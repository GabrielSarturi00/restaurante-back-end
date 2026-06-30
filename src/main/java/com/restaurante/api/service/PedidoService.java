package com.restaurante.api.service;

import com.restaurante.api.dto.*;
import com.restaurante.api.entity.ItemPedido;
import com.restaurante.api.entity.Pedido;
import com.restaurante.api.entity.Produto;
import com.restaurante.api.repository.PedidoRepository;
import com.restaurante.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setNomeCliente(dto.nomeCliente());
        pedido.setTelefoneCliente(dto.telefoneCliente());
        pedido.setObservacao(dto.observacao());
        pedido.setStatus("PENDENTE");
        pedido.setDataPedido(LocalDateTime.now());

        List<ItemPedido> itens = new ArrayList<>();
        double total = 0.0;

        for (ItemPedidoRequestDTO itemDto : dto.itens()) {
            Produto produto = produtoRepository.findById(itemDto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.produtoId()));

            if (!produto.getAtivo()) {
                throw new RuntimeException("Produto indisponível: " + produto.getNome());
            }

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(produto.getPreco());
            double subtotal = produto.getPreco() * itemDto.quantidade();
            item.setSubtotal(subtotal);
            total += subtotal;
            itens.add(item);
        }

        pedido.setItens(itens);
        pedido.setValorTotal(total);

        return toResponse(pedidoRepository.save(pedido));
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAllByOrderByDataPedidoDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PedidoResponseDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        return toResponse(pedido);
    }

    public PedidoResponseDTO atualizarStatus(Long id, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + id));
        pedido.setStatus(novoStatus);
        return toResponse(pedidoRepository.save(pedido));
    }

    private PedidoResponseDTO toResponse(Pedido pedido) {
        List<ItemPedidoResponseDTO> itensDto = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponseDTO(
                        item.getId(),
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getNomeCliente(),
                pedido.getTelefoneCliente(),
                pedido.getObservacao(),
                itensDto,
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getDataPedido()
        );
    }
}
