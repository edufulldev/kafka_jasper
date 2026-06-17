package edu.dev.icompras.pedidos.service;

import edu.dev.icompras.pedidos.model.enums.StatusPedido;
import edu.dev.icompras.pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizacaoPedidoService {

    private final PedidoRepository repository;

    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido status, String urlNotaFiscal, String rastreio) {
        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(status);

            if(urlNotaFiscal != null) {
                pedido.setUrlNotaFiscal(urlNotaFiscal);
            }
            if(rastreio != null) {
                pedido.setCodigoRastreio(rastreio);
            }

        });
    }
}
