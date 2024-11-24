# Livraria Online

Imagine que estamos desenvolvendo um sistema de livraria online que permite aos usuários consultar informações sobre autores e livros. O sistema deve permitir a pesquisa eficiente a fim de apresentar informações detalhadas sobre cada entidade. Para facilitar a comunicação entre o frontend e o backend, utilizamos DTOs, que ajudam a transferir apenas os dados necessários, otimizando a performance e a segurança do sistema.


## Configure o projeto

- Crie um projeto Spring Boot com as seguintes dependências:
  - Spring Web
  - Spring Data JPA
  - H2 Database
  - Lombok (opcional)

Configure o arquivo `application.properties` com as seguintes propriedades:

```properties
spring.application.name=atividade-relacionamento

# exibe a instrução SQL no console
spring.jpa.show-sql=true
# formata a instrução SQL
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.url=jdbc:h2:mem:livraria


# habilita a inclusão a mensagem e os erros de validação
server.error.include-message=always
server.error.include-binding-errors=always

# haiblita a inicialização do datasource
spring.jpa.defer-datasource-initialization=true
```

- Crie um esquema de pacotes conforme as instruções abaixo.
  - **entity**: classes de entidade/dominio.
  - **repository**: classes de repositório.
  - **service**: classes de serviço.
  - **controller**: classes de controladores.


- Crie um arquivo `data.sql` na pasta `resources` para popular o banco de dados:
```sql
INSERT INTO Autor (nome, nacionalidade) VALUES
('J.K. Rowling', 'Britânica'),
('George R.R. Martin', 'Americana'),
('J.R.R. Tolkien', 'Britânica'),
('Gabriel García Márquez', 'Colombiana'),
('Haruki Murakami', 'Japonesa');

INSERT INTO Livro (titulo, descricao, data_publicacao, preco, autor_id) VALUES
('Harry Potter e a Pedra Filosofal',
 'Primeiro livro da série Harry Potter, onde o jovem Harry descobre ser um bruxo e vai para Hogwarts.',
 '1997-06-26',
 40.0,
 1),

('Harry Potter e a Câmara Secreta',
 'Segundo livro da série Harry Potter, onde Harry enfrenta novos desafios em Hogwarts, incluindo a abertura da Câmara Secreta.',
 '1998-07-02',
 45.0,
 1),

('A Game of Thrones',
 'Primeiro livro da série As Crônicas de Gelo e Fogo, onde as casas nobres dos Sete Reinos lutam pelo controle do Trono de Ferro.',
 '1996-08-06',
 50.0,
 2),

('A Clash of Kings',
 'Segundo livro da série As Crônicas de Gelo e Fogo, onde a guerra pelo Trono de Ferro se intensifica.',
 '1998-11-16',
 55.0,
 2),

('O Senhor dos Anéis: A Sociedade do Anel',
 'Primeiro livro da trilogia O Senhor dos Anéis, onde Frodo Bolseiro inicia sua jornada para destruir o Um Anel.',
 '1954-07-29',
 60.0,
 3),

('Cem Anos de Solidão',
 'Obra-prima de Gabriel García Márquez que narra a história da família Buendía na fictícia cidade de Macondo.',
 '1967-05-30',
 35.0,
 4),

('Kafka à Beira-Mar',
 'Um dos romances mais conhecidos de Haruki Murakami, que segue a história de um jovem fugitivo e um velho caçador de gatos.',
 '2002-09-12',
 30.0,
 5);
```

# Entidades

- **Autor**
  - id: Integer
  - nome: String
  - nacionalidade: String

- **Livro**
  - id: Integer
  - titulo: String
  - descricao: String
  - dataPublicacao: LocalDate
  - preco: Double
  - autor: Autor

<br/>
<hr style="background-color:gray; height:6px">

### Crie uma camada de serviço para manipular as entidades Autor e Livro. Utilize a anotação @Service para indicar que a classe é um bean de serviço.

- **AutorService**
  - **Métodos**
    - List<Autor> listar()
    - Autor buscarPorId(Integer id)
    - Autor salvar(Autor autor)
- **LivroService**
  - **Métodos**
    - List<Livro> listar()
    - Livro buscarPorId(Integer id)
    - Livro salvar(Livro livro)
    - Livro atualizar(Integer id, Livro livro)
    - List<Livro> buscarPorTitulo(String titulo)
    - List<Livro> buscarPorDataPublicacao(LocalDate data)
    - List<Livro> buscarPorTituloEData(String titulo, LocalDate dataInicio, LocalDate dataFim)
    - Double somaPrecos()

# Endpoints

## 📄 Criacao de autores

- **Endpoint**: `POST /autores`
- **Descrição**: Cria um novo autor.
- **Retorno**: Autor.
- **Objeto Request**: 
```json
{
  "nome": "Gabriel García Márquez",
  "nacionalidade": "Colombiana"
}
```

## 📄 Listagem de autores

- **Endpoint**: `GET /autores`
- **Descrição**: Retorna todos os autores cadastrados com os livros vinculados.
- **Retorno**: Lista de Autor.


## 📄 Busca de autor por ID

- **Endpoint**: `GET /autores/{id}`
- **Descrição**: Retorna um autor específico.
- **Retorno**: Autor.


## 📄 Listagem de Livros

- **Endpoint**: `GET /livros`
- **Descrição**: Retorna todos os livros cadastrados.
- **Retorno**: Lista de Livros.
- **Observação**: Caso não haja livros cadastrados, retorne um status 204.


## 📄 Listagem de Livros por ID
- **Endpoint**: `GET /livros/{id}`
- **Descrição**: Retorna um livro específico.
- **Retorno**: Livro.
- **Observação**: Caso o livro não seja encontrado, retorne um status 404.


## 📄 Cadastro de Livros
- **Endpoint**: `POST /livros`
- **Request Body**: Livro.
- **Retorno**: Retorno do Livro salvo no banco.
- **Descrição**: Cria um novo livro.
- **Observação**: O livro deve ser retornado após a criação.
- **Observação**: Caso o autor não exista, retorne um status 404.
- **Objeto Request**: 
```json
{
  "titulo": "Harry Potter e o Prisioneiro de Azkaban",
  "descricao": "Terceiro livro da série Harry Potter, onde Harry descobre que Sirius Black é seu padrinho e foge de Azkaban.",
  "dataPublicacao": "1999-07-08",
  "preco": 50.0,
  "autor": {
    "id": 1
  }
}
```

## 📄 Atualização de Livros
- **Endpoint**: `PUT /livros/{id}`
- **Request Body**: Livro.
- **Retorno**: Retorno do Livro salvo no banco.
- **Descrição**: Atualiza um livro existente.
- **Observação**: Considere o objeto que chegou na requisição. O objeto que chegou, após recebido, deve ter o id atribuído que chegou no PathVariable.
- **Observação**: Caso o livro não seja encontrado, retorne um status 404.
- **Objeto Request**: 
```json
{
  "titulo": "Harry Potter e o Prisioneiro de Azkaban",
  "descricao": "Terceiro livro da série Harry Potter, onde Harry descobre que Sirius Black é seu padrinho e foge de Azkaban.",
  "dataPublicacao": "1999-07-08",
  "preco": 30.0,
  "autor": {
    "id": 1
  }
}
```


## 📄 Listagem de Livros por Título
- **Endpoint**: `GET /livros/titulo`
- **Descrição**: Retorna todos os livros que possuem o título informado via RequestParam.
- **Tipo de Busca**: Dynamic.
- **Retorno**: Lista de Livros.
- **Observação**: Caso não haja livros com o título informado, retorne um status 204.


## 📄 Listagem de Livros por uma Data de Publicação posterior a uma data informada
- **Endpoint**: `GET /livros/data`
- **Descrição**: Retorna todos os livros que possuem a data de publicação POSTERIOR a data informada via RequestParam.
- **Tipo de Busca**: Dynamic Finder.
- **Retorno**: Lista de Livros.
- **Observação**: Caso não haja livros com a data de publicação informada, retorne um status 204.
- **Objeto retorno**:

## 📄 Listagem de Livros entre duas datas e com parte de um titulo ignorando case
- **Endpoint**: `GET /livros/data/titulo`
- **Descrição**: Retorna todos os livros que possuem a data de publicação entre as datas informadas e que possuem parte do título informado ignorando case, os dois via RequestParam.
- **Tipo de Busca**: Dynamic Finder.
- **Retorno**: Lista de Livros.
- **Observação**: Caso não haja livros, retorne um status 204.
- **Observação**: A busca por parte do título deve ser ignorando case.
- **Observação**: A busca por data deve ser inclusiva.

## 📄 Busca a soma dos preços dos livros
- **Endpoint**: `GET /livros/soma`
- **Descrição**: Retorna a soma dos preços dos livros.
- **Tipo de Busca**: JPQL.
- **Retorno**: Double.
- **Observação**: Caso a soma vier null, retorne 0.0.