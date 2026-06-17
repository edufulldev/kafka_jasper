package edu.dev.icompras.pedidos.controller.dto;

import java.util.List;

public record NovoPedidoDTO(
        Long codigoCliente,
        DadosPagamentoDto dadosPagamento,
        List<ItemPedidoDTO> itens
) {
}
