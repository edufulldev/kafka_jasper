package edu.dev.icompras.pedidos.client.representation;

public record ClientRepresentation(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone,
        Boolean ativo
) {
}
