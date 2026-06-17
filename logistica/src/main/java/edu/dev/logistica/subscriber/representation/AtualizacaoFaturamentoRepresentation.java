package edu.dev.logistica.subscriber.representation;

import edu.dev.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
