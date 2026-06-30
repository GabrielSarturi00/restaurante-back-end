package com.restaurante.api.service;

import com.restaurante.api.dto.ProdutoRequestDTO;
import com.restaurante.api.dto.ProdutoResponseDTO;
import com.restaurante.api.entity.Produto;
import com.restaurante.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProdutoResponseDTO> listarAtivos() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
        return toResponse(produto);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setCategoria(dto.categoria());
        produto.setPreco(dto.preco());
        produto.setImagem(dto.imagem());
        produto.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        return toResponse(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setCategoria(dto.categoria());
        produto.setPreco(dto.preco());
        produto.setImagem(dto.imagem());
        if (dto.ativo() != null) produto.setAtivo(dto.ativo());
        return toResponse(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO alternarStatus(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));
        produto.setAtivo(!produto.getAtivo());
        return toResponse(produtoRepository.save(produto));
    }

    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }

    private ProdutoResponseDTO toResponse(Produto p) {
        return new ProdutoResponseDTO(
                p.getId(),
                p.getNome(),
                p.getDescricao(),
                p.getCategoria(),
                p.getPreco(),
                p.getImagem(),
                p.getAtivo()
        );
    }
}
