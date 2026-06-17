package edu.dev.faturamento.mapper;

import edu.dev.faturamento.model.Cliente;
import edu.dev.faturamento.model.ItemPedido;
import edu.dev.faturamento.model.Pedido;
import edu.dev.faturamento.subscriber.representation.DetalheItemPedidoRepresentation;
import edu.dev.faturamento.subscriber.representation.DetalhePedidoRepresentation;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PedidoMapper {

    public Pedido map(DetalhePedidoRepresentation representation) {
        Cliente cliente = new Cliente(
                representation.nome(),
                representation.cpf(),
                representation.logradouro(),
                representation.numero(),
                representation.bairro(),
                representation.email(),
                representation.telefone()
        );


        List<ItemPedido> itens = representation.itens()
                .stream()
                .map(this::mapItem)
                .toList();

        return new Pedido(
                representation.codigo(),
                cliente,
                representation.dataPedido(),
                representation.total(),
                itens
        );
    }

    private ItemPedido mapItem(DetalheItemPedidoRepresentation representation) {
        return new ItemPedido(
                representation.codigoProduto(),
                representation.nome(),
                representation.valorUnitario(),
                representation.quantidade(),
                representation.total()
        );
    }
}