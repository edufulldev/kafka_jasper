package edu.dev.faturamento.publisher.representation;

public record AtualizacaoStatusPedido(
        Long codigo,
        StatusPedido status,
        String urlNotaFiscal
) {
}
