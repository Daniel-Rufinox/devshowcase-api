# DevShowcase API

API REST para cadastro de desenvolvedores, seus projetos, tecnologias e comentários.
Trabalho prático — modelagem de domínio, persistência e endpoints REST.

## Tecnologias
- Java 17
- Spring Boot 3.3.4 (Web, Data JPA, Validation)
- Banco H2 (em memória)

## Como rodar
./mvnw spring-boot:run
A API sobe em http://localhost:8080

## Modelo de domínio
- Developer 1:N Project
- Project N:N Technology
- Project 1:N Comment

## Endpoints
| Método | Rota | Descrição |
|---|---|---|
| POST | /api/developers | Cadastra desenvolvedor |
| GET | /api/developers | Lista desenvolvedores |
| GET | /api/developers/{id} | Busca por id |
| DELETE | /api/developers/{id} | Remove desenvolvedor |
| POST | /api/developers/{id}/projects | Cria projeto do desenvolvedor |
| GET | /api/projects | Lista projetos |
| GET | /api/projects/{id} | Busca projeto |
| PUT | /api/projects/{id} | Atualiza projeto |
| DELETE | /api/projects/{id} | Remove projeto |
| POST | /api/projects/{id}/technologies | Adiciona tecnologia |
| GET | /api/technologies | Lista tecnologias |
| POST | /api/projects/{id}/comments | Adiciona comentário |
| GET | /api/projects/{id}/comments | Lista comentários |
