package edu.dev.icompras.pedidos.service;

import edu.dev.icompras.pedidos.client.ClientesClient;
import edu.dev.icompras.pedidos.client.ProdutosClient;
import edu.dev.icompras.pedidos.client.ServicoBancarioClient;
import edu.dev.icompras.pedidos.model.DadosPagamento;
import edu.dev.icompras.pedidos.model.ItemPedido;
import edu.dev.icompras.pedidos.model.Pedido;
import edu.dev.icompras.pedidos.model.enums.StatusPedido;
import edu.dev.icompras.pedidos.model.enums.TipoPagamento;
import edu.dev.icompras.pedidos.model.exception.ItemNaoencontradoException;
import edu.dev.icompras.pedidos.publisher.PagamentoPublisher;
import edu.dev.icompras.pedidos.repository.ItemPedidoRepository;
import edu.dev.icompras.pedidos.repository.PedidoRepository;
import edu.dev.icompras.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient sevicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);
        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = sevicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public void atualizarStatusPagamento(
            Long codigoPedido,
            String chavePagamento,
            boolean sucesso,
            String observacoes
    ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()) {
            var  msg = String.format("pedido não encontrado para o código %d e chave pagamento %s", codigoPedido, chavePagamento);
            log.error(msg);
            return;
        }
        Pedido pedido = pedidoEncontrado.get();

        if(sucesso) {
            prepararEpublicarPedidoPago(pedido);
        } else  {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
    }

    private void prepararEpublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);
        carregarDadosCliente(pedido);
        carregaritensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipo) {
        var pedidoEncontrado = repository.findById(codigoPedido);

        if(pedidoEncontrado.isEmpty()) {
            throw new ItemNaoencontradoException("Pedido não encontrado para o código informado");
        }

        var pedido = pedidoEncontrado.get();

        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipo);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o processamento.");

        String novaChavePagamento = sevicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        repository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletoPedido(Long codigo) {
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregaritensPedido);
        return  pedido;
    }

    private void carregarDadosCliente(Pedido pedido) {
         Long codigoCliente = pedido.getCodigoCliente();
         var response = apiClientes.obterDados(codigoCliente);
         pedido.setDadosCliente(response.getBody());
    }

    private void carregaritensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido item) {
        Long codigoProduto = item.getCodigoProduto();
        var response = apiProdutos.obterDados(codigoProduto);
        item.setNome(response.getBody().nome());
    }
}
