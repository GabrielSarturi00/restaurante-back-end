package com.restaurante.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String nomeCliente,
        String telefoneCliente,
        String observacao,
        List<ItemPedidoResponseDTO> itens,
        Double valorTotal,
        String status,
        LocalDateTime dataPedido
) {}
