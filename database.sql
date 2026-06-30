-- Script de criação do banco de dados
-- Restaurante Menu Digital

CREATE DATABASE IF NOT EXISTS restaurante_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE restaurante_db;

-- Tabela de produtos
CREATE TABLE IF NOT EXISTS produtos (
    id_produto   BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(100)  NOT NULL,
    descricao    VARCHAR(500)  NOT NULL,
    categoria    VARCHAR(50)   NOT NULL,
    preco        DOUBLE        NOT NULL,
    imagem       VARCHAR(500),
    ativo        BOOLEAN       NOT NULL DEFAULT TRUE
);

-- Tabela de pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_cliente     VARCHAR(100) NOT NULL,
    telefone_cliente VARCHAR(150) NOT NULL,
    observacao       VARCHAR(300),
    valor_total      DOUBLE       NOT NULL,
    status           VARCHAR(30)  NOT NULL DEFAULT 'PENDENTE',
    data_pedido      DATETIME     NOT NULL
);

-- Tabela de itens do pedido
CREATE TABLE IF NOT EXISTS itens_pedido (
    id_item        BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id      BIGINT  NOT NULL,
    produto_id     BIGINT  NOT NULL,
    quantidade     INT     NOT NULL,
    preco_unitario DOUBLE  NOT NULL,
    subtotal       DOUBLE  NOT NULL,
    CONSTRAINT fk_itens_pedido  FOREIGN KEY (pedido_id)  REFERENCES pedidos(id_pedido)  ON DELETE CASCADE,
    CONSTRAINT fk_itens_produto FOREIGN KEY (produto_id) REFERENCES produtos(id_produto) ON DELETE RESTRICT
);

-- Dados iniciais de exemplo
INSERT INTO produtos (nome, descricao, categoria, preco, imagem, ativo) VALUES
('X-Burguer Clássico',  'Hambúrguer artesanal 180g, queijo, alface, tomate e molho especial',       'Lanches',   28.90, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400', TRUE),
('X-Bacon Duplo',       'Dois hambúrgueres 180g, bacon crocante, queijo cheddar e cebola caramelizada', 'Lanches', 39.90, 'https://images.unsplash.com/photo-1553979459-d2229ba7433a?w=400', TRUE),
('X-Frango Crispy',     'Frango empanado crocante, queijo, alface e maionese temperada',             'Lanches',   32.90, 'https://images.unsplash.com/photo-1606755962773-d324e0a13086?w=400', TRUE),
('Batata Frita P',      'Porção pequena de batatas fritas crocantes com sal e temperos',             'Porções',   14.90, 'https://images.unsplash.com/photo-1576107232684-1279f390859f?w=400', TRUE),
('Batata Frita G',      'Porção grande de batatas fritas crocantes com sal e temperos',              'Porções',   24.90, 'https://images.unsplash.com/photo-1576107232684-1279f390859f?w=400', TRUE),
('Onion Rings',         'Anéis de cebola empanados e fritos, acompanha molho ranch',                 'Porções',   19.90, 'https://images.unsplash.com/photo-1639024471283-03518883512d?w=400', TRUE),
('Refrigerante Lata',   'Coca-Cola, Guaraná Antarctica ou Sprite 350ml',                             'Bebidas',    7.90, 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=400', TRUE),
('Milk Shake',          'Milk shake 400ml — Chocolate, Morango ou Baunilha',                         'Bebidas',   22.90, 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400', TRUE),
('Suco Natural',        'Suco natural 300ml — Laranja, Limão ou Maracujá',                           'Bebidas',   12.90, 'https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=400', TRUE),
('Brownie',             'Brownie de chocolate com sorvete de baunilha e calda quente',               'Sobremesas', 18.90, 'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400', TRUE);
