package com.restaurante.api.dto;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        String categoria,
        Double preco,
        String imagem,
        Boolean ativo
) {}
