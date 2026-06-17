package edu.dev.icompras.pedidos.validator;

import edu.dev.icompras.pedidos.client.ClientesClient;
import edu.dev.icompras.pedidos.client.ProdutosClient;
import edu.dev.icompras.pedidos.client.representation.ClientRepresentation;
import edu.dev.icompras.pedidos.client.representation.ProdutoRepresentation;
import edu.dev.icompras.pedidos.model.ItemPedido;
import edu.dev.icompras.pedidos.model.Pedido;
import edu.dev.icompras.pedidos.model.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        Long codigoCliente = pedido.getCodigo();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            var response = clientesClient.obterDados(codigoCliente);
            ClientRepresentation cliente = response.getBody();
            log.info("cliente de código{} encontrado: {}", cliente.codigo(), cliente.nome());

            if(!cliente.ativo()) {
                throw new ValidationException("codigoCliente", "cliente inativo");
            }

        } catch (FeignException.NotFound e) {
            var message = String.format("cliente de código %d não encontrado", codigoCliente);
            throw new ValidationException("codigoCliente", message);
        }
    }

    private void validarItem(ItemPedido item) {
        try {
            var response = produtosClient.obterDados(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("produto de  código {} encontrado: {}", produto.codigo(), produto.nome());

            if(!produto.ativo()) {
                throw new ValidationException("codigoProduto", "produto inativo");
            }

        } catch (FeignException.NotFound e) {
            var message = String.format("produto de código %d não encontrado", item.getCodigoProduto());
            throw new ValidationException("codigoProdtuo", message);
        }
    }
}
