package edu.dev.icompras.pedidos.controller.dto;

import edu.dev.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(
        Long codigoPedido,
        String dados,
        TipoPagamento tipoPagamento
) {
}
