package edu.dev.logistica.model;

public record AtualizacaoEnvioPedido(
        Long codigo,
        StatusPedido status,
        String codigoRastreio
) {
}
