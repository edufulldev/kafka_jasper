package edu.dev.icompras.pedidos.controller.mappers;

import edu.dev.icompras.pedidos.controller.dto.ItemPedidoDTO;
import edu.dev.icompras.pedidos.model.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
