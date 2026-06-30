package com.restaurante.api.dto;

public record ItemPedidoResponseDTO(
        Long id,
        Long produtoId,
        String nomeProduto,
        Integer quantidade,
        Double precoUnitario,
        Double subtotal
) {}
