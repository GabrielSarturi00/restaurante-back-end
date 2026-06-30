# Restaurante Digital — Back-End

API REST desenvolvida em **Java Spring Boot** para o sistema de Menu de Restaurante (Trabalho Final — Desenvolvimento de Aplicação Web).

> Front-end deste projeto: [restaurante-front-end](https://github.com/SEU_USUARIO/restaurante-front-end)

---

## Tecnologias

- Java 21
- Spring Boot 3.3.0
- Spring Web (API REST)
- Spring Data JPA / Hibernate
- MySQL 8
- Maven

---

## Pré-requisitos

- Java 21+ instalado
- Maven 3.8+ (ou usar o `mvnw` incluso, se houver)
- MySQL 8+ rodando localmente

---

## Como Executar

### 1. Criar o banco de dados

Execute o script `database.sql` no seu MySQL:

```bash
mysql -u root -p < database.sql
```

Ou abra o arquivo no MySQL Workbench e execute todo o script. Ele cria o banco `restaurante_db`, as tabelas e insere 10 produtos de exemplo.

> Obs: mesmo sem rodar o script manualmente, o Hibernate cria as tabelas automaticamente na primeira execução (`ddl-auto=update`). Rodar o script é recomendado apenas para já ter dados de exemplo.

### 2. Configurar a conexão com o banco

Edite `src/main/resources/application.properties` com seu usuário e senha do MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
```

### 3. Rodar a aplicação

Pela linha de comando:

```bash
mvn spring-boot:run
```

Ou pela IDE (IntelliJ/Eclipse): rode a classe `RestauranteApplication.java`.

A API sobe em **http://localhost:8080**

### 4. Testar se está no ar

Acesse no navegador:

```
http://localhost:8080/produtos
```

Deve retornar um JSON com a lista de produtos (ou `[]` se o banco estiver vazio).

---

## Estrutura do Projeto

```
src/main/java/com/restaurante/api/
├── entity/         → Classes mapeadas para as tabelas do banco (JPA)
│   ├── Produto.java
│   ├── Pedido.java
│   └── ItemPedido.java
├── repository/     → Interfaces de acesso ao banco (Spring Data JPA)
│   ├── ProdutoRepository.java
│   └── PedidoRepository.java
├── service/        → Regras de negócio da aplicação
│   ├── ProdutoService.java
│   └── PedidoService.java
├── controller/      → Endpoints REST (camada de entrada da API)
│   ├── ProdutoController.java
│   └── PedidoController.java
├── dto/            → Objetos de transferência de dados (Request/Response)
└── config/
    └── CorsConfig.java  → Libera o front-end a consumir a API
```

A aplicação segue arquitetura em camadas:
**Controller → Service → Repository → Entity**, com DTOs isolando o que é exposto pela API daquilo que é persistido no banco.

---

## Endpoints da API

### Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Lista todos os produtos (uso administrativo) |
| GET | `/produtos/ativos` | Lista apenas produtos ativos (uso do cliente) |
| GET | `/produtos/{id}` | Busca um produto por ID |
| POST | `/produtos` | Cadastra um novo produto |
| PUT | `/produtos/{id}` | Atualiza um produto existente |
| PATCH | `/produtos/{id}/status` | Ativa ou desativa um produto |
| DELETE | `/produtos/{id}` | Exclui um produto |

**Exemplo de corpo (POST/PUT) `/produtos`:**
```json
{
  "nome": "X-Burguer Clássico",
  "descricao": "Hambúrguer artesanal 180g com queijo e salada",
  "categoria": "Lanches",
  "preco": 28.90,
  "imagem": "https://exemplo.com/imagem.jpg",
  "ativo": true
}
```

### Pedidos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/pedidos` | Cria um novo pedido |
| GET | `/pedidos` | Lista todos os pedidos |
| GET | `/pedidos/{id}` | Busca um pedido por ID |
| PATCH | `/pedidos/{id}/status` | Atualiza o status do pedido |

**Exemplo de corpo (POST) `/pedidos`:**
```json
{
  "nomeCliente": "João Silva",
  "telefoneCliente": "(51) 99999-9999",
  "observacao": "Sem cebola",
  "itens": [
    { "produtoId": 1, "quantidade": 2 },
    { "produtoId": 3, "quantidade": 1 }
  ]
}
```

**Exemplo de corpo (PATCH) `/pedidos/{id}/status`:**
```json
{ "status": "PREPARANDO" }
```

Status possíveis: `PENDENTE`, `PREPARANDO`, `PRONTO`, `ENTREGUE`, `CANCELADO`

---

## Regras de Negócio

- Um produto **inativo** não pode ser adicionado a um pedido — a validação ocorre no `PedidoService`, no back-end, e não depende apenas do front-end esconder o botão.
- O preço de cada item do pedido é gravado no momento da compra (`precoUnitario` em `itens_pedido`), preservando o histórico mesmo que o preço do produto mude depois.
- Excluir um produto que já foi vendido é bloqueado pela constraint de chave estrangeira (`ON DELETE RESTRICT`), preservando a integridade dos pedidos antigos.

---

## Banco de Dados

Três tabelas principais:

- **produtos** — nome, descrição, categoria, preço, imagem, status (ativo/inativo)
- **pedidos** — dados do cliente, valor total, status, data
- **itens_pedido** — tabela associativa entre pedido e produto, com quantidade e preço unitário

O script completo de criação está em [`database.sql`](./database.sql).
