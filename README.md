# Sinta-se Bem App

🚀 Aplicação desenvolvida em **Spring Boot** seguindo os princípios de **Arquitetura Limpa**, garantindo separação de responsabilidades, escalabilidade e testabilidade.

## 📌 Visão Geral

O **Sinta-se Bem App** é um sistema projetado para promover serviços de bem-estar e beleza. A aplicação permite que usuários encontrem profissionais e agendem serviços de forma rápida e prática.

## 🏗 Arquitetura

A aplicação segue o conceito de **Clean Architecture**, organizando o código em camadas bem definidas:

- **Domain:** Contém as entidades
- **Application:** Implementa os casos de uso da aplicação.
- **Infra:** Integração com banco de dados, APIs externas e configurações gerais.
- **Gateway:** Configurações de segurança.
- **Main:** Classe principal da aplicação.

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **PostgreSQL**
- **Liquibase** (para versionamento do banco de dados)
- **Docker** (para containerização)
- **JUnit 5 & Mockito** (para testes unitários)
- **Lombok** (para redução de boilerplate)
- **Swagger/OpenAPI** (documentação da API)

## 📂 Estrutura do Projeto

```shell
sinta-se-bem-app/
│── src/
│   ├── main/
│   │   ├── java/com/sintasebem/
│   │   │   ├── domain/           # Entidades e regras de negócio
│   │   │   ├── application/      # Casos de uso e serviços
│   │   │   ├── infra/            # Repositórios, clients e integrações
│   │   │   ├── gateway/          # Configurações e segurança
│   │   │   ├── main/             # Classe principal da aplicação
│   ├── test/                     # Testes unitários e de integração
│── docker-compose.yml            # Configuração para execução em contêineres
│── README.md                     # Documentação do projeto
│── pom.xml                       # Dependências do Maven
```

## 🏃‍♂️ Como Rodar o Projeto

### Pré-requisitos

Certifique-se de ter os seguintes requisitos instalados:

- **JDK 21**
- **Maven 3.8+**
- **Docker** (caso queira rodar o banco de dados em container)

### Passos para Execução

1. Clone o repositório:
   ```sh
   git clone https://github.com/rafaelteixeirarnnt/sinta-se-bem-app.git
   cd sinta-se-bem-app
   ```

2. Configure as variáveis de ambiente no arquivo `application.yaml`.
   - 2.1. 📌 Configuração das Variáveis de Ambiente.  
   Antes de rodar a aplicação, defina as seguintes variáveis de ambiente:

```plaintext
DATABASE_USER=postgres -- Usuário do banco
DATABASE_PASSWORD=postgres -- Senha do banco
DATABASE=app -- Database (Precisa ser criado antes de iniciar o projeto)
DATABASE_URL=jdbc:postgresql://localhost:5432/app -- URL do banco (Troca {app} pelo nome escolhido para o database)
```

3. Suba os containers necessários com **Docker Compose**:
   ```sh
   docker-compose up -d
   ```

4. Compile e rode a aplicação:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

5. A API estará disponível em:
   ```
   http://localhost:8080
   ```

## 📜 Documentação da API

A documentação Swagger pode ser acessada após rodar o projeto:

```
http://localhost:8080/swagger-ui.html
```

## ✅ Testes

Para rodar os testes automatizados:

```sh
mvn test
```

## 🚀 Deploy e Ambiente

O projeto pode ser deployado utilizando **Docker** ou diretamente em servidores com **Spring Boot**.

### 🔹 Deploy com Docker

```sh
docker build -t sintasebem-app .
docker run -p 8080:8080 sintasebem-app
```

## 📄 Licença

Este projeto está sob a licença **MIT**. Veja o arquivo `LICENSE` para mais detalhes.

---