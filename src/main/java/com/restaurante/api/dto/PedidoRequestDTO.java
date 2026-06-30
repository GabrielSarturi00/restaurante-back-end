package com.restaurante.api.dto;

import java.util.List;

public record PedidoRequestDTO(
        String nomeCliente,
        String telefoneCliente,
        String observacao,
        List<ItemPedidoRequestDTO> itens
) {}
