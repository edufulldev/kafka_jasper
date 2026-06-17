package edu.dev.icompras.clientes.service;

import edu.dev.icompras.clientes.model.Cliente;
import edu.dev.icompras.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {

        cliente.setAtivo(true);
       return repository.save(cliente);
    }

    public Optional<Cliente> obterPorCodigo(Long codigo) {
       return repository.findById(codigo);
    }

    public void deletar(Cliente cliente) {
        cliente.setAtivo(false);
        repository.save(cliente);
    }
}
