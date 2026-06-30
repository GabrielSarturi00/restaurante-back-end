package com.restaurante.api.controller;

import com.restaurante.api.dto.ProdutoRequestDTO;
import com.restaurante.api.dto.ProdutoResponseDTO;
import com.restaurante.api.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Área Administrativa: lista todos os produtos
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    // Área do Cliente: lista apenas produtos ativos
    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
                                                         @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }

    // Ativar/Desativar produto (toggle)
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProdutoResponseDTO> alternarStatus(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.alternarStatus(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
