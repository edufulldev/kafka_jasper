package edu.dev.logistica.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dev.logistica.service.EnvioPedidoService;
import edu.dev.logistica.subscriber.representation.AtualizacaoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FaturamentoSubscriber {

    private final ObjectMapper objectMapper;
    private final EnvioPedidoService service;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${icompras.config.kafka.topics.pedidos-faturados}")
    public void listen(String json) {

        log.info("Recebendo pedido para envio: {} ", json);

        try {

            var representation = objectMapper.readValue(json, AtualizacaoFaturamentoRepresentation.class);

            service.enviar(representation.codigo(), representation.urlNotaFiscal());

            log.info("Pedido foi processado código: {}", representation.codigo());

        } catch (Exception e ) {
            log.error("Erro ao preparar pedido para envio", e);
        }
    }
}
