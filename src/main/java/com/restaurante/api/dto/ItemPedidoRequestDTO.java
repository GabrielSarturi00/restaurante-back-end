package com.restaurante.api.dto;

public record ItemPedidoRequestDTO(
        Long produtoId,
        Integer quantidade
) {}
