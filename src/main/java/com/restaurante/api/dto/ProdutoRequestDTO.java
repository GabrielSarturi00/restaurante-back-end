package com.restaurante.api.dto;

public record ProdutoRequestDTO(
        String nome,
        String descricao,
        String categoria,
        Double preco,
        String imagem,
        Boolean ativo
) {}
