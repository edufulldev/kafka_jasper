package edu.dev.icompras.pedidos.controller;

import edu.dev.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import edu.dev.icompras.pedidos.controller.dto.NovoPedidoDTO;
import edu.dev.icompras.pedidos.controller.mappers.PedidoMapper;
import edu.dev.icompras.pedidos.model.ErroResposta;
import edu.dev.icompras.pedidos.model.exception.ItemNaoencontradoException;
import edu.dev.icompras.pedidos.model.exception.ValidationException;
import edu.dev.icompras.pedidos.publisher.DetalhesPedidoMapper;
import edu.dev.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import edu.dev.icompras.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhesPedidoMapper detalhesPedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro da validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody AdicaoNovoPagamentoDTO dto) {
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoencontradoException e) {
            var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }
    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo) {

        return service.carregarDadosCompletoPedido(codigo).map(detalhesPedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
