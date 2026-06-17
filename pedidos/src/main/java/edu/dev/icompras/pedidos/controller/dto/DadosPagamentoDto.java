package edu.dev.icompras.pedidos.controller.dto;

import edu.dev.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDto(
        String dados,
        TipoPagamento tipoPagamento
) {
}
