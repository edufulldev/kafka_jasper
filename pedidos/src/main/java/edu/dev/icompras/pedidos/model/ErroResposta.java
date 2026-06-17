package edu.dev.icompras.pedidos.model;

public record ErroResposta(
        String mensagem,
        String campo,
        String erro
) {
}
