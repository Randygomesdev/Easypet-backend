# 🐾 EasyPet Backend

> API REST para gerenciamento de pets, veterinários e agendamentos de serviços.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-7-red?style=flat-square&logo=redis)
![Docker](https://img.shields.io/badge/Docker-compose-blue?style=flat-square&logo=docker)
![Railway](https://img.shields.io/badge/Railway-deployed-success?style=flat-square&logo=railway)

---

## 📋 Sobre o Projeto

O **EasyPet** é uma API REST corporativa desenvolvida com Spring Boot para gerenciamento de clínicas veterinárias. Permite que tutores cadastrem seus pets, agendem consultas e tenham acesso à carteira de vacinação digital. Oferece ao corpo clínico um sistema completo de prontuários eletrônicos, prescrições médicas e notificações assíncronas automáticas (via E-mail) para lembretes de vacinas.

---

## 🚀 Tecnologias

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5.11 | Framework principal |
| Spring Security | 6 | Autenticação e autorização |
| Spring Data JPA | 3 | Acesso ao banco de dados |
| PostgreSQL | 16 | Banco de dados relacional |
| Redis | 7 | Cache e blacklist de tokens |
| Flyway | 10 | Versionamento do banco |
| JWT (jjwt) | 0.12.6 | Autenticação stateless |
| Lombok | - | Redução de boilerplate |
| Swagger/OpenAPI | 3 | Documentação da API |
| Thymeleaf & JavaMail | 3 | Templates HTML e envio de E-mails |
| Docker | - | Containerização |

---

## 🏗️ Arquitetura

```
src/main/java/br/com/easypet/
├── controller/     ← Camada de apresentação (endpoints REST)
├── service/        ← Camada de negócio (regras e lógica)
├── repository/     ← Camada de dados (acesso ao banco)
├── domain/
│   ├── entity/     ← Entidades JPA
│   └── enums/      ← Enumerações
├── dto/
│   ├── request/    ← Objetos de entrada
│   └── response/   ← Objetos de saída
├── exception/      ← Tratamento de erros centralizado
├── security/       ← Filtros e serviços JWT
└── config/         ← Configurações (Security, Redis, Swagger)
```

---

## 👥 Perfis de Acesso

| Role | Descrição | Permissões |
|------|-----------|------------|
| `USER` | Tutor de pet | Gerencia seus pets e agendamentos |
| `VET` | Veterinário | Visualiza seus agendamentos |
| `ADMIN` | Administrador | Acesso total ao sistema |

---

## 📦 Pré-requisitos

- [Java 21](https://adoptium.net/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- [Git](https://git-scm.com/)

---

## ⚙️ Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/easypet-backend.git
cd easypet-backend
```

### 2. Suba os containers

```bash
docker-compose up -d
```

### 3. Execute a aplicação

```bash
./gradlew bootRun
```

### 4. Acesse a documentação

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🐳 Docker

O projeto possui um `docker-compose.yml` com os seguintes serviços:

| Serviço | Imagem | Porta |
|---------|--------|-------|
| PostgreSQL | postgres:16-alpine | 5433 |
| Redis | redis:7-alpine | 6379 |

```bash
# Subir os containers
docker-compose up -d

# Parar os containers
docker-compose down

# Remover volumes (limpa o banco)
docker-compose down -v
```

---

## 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação stateless.

### Registro de usuário
```http
POST /api/auth/register
Content-Type: application/json

{
    "name": "Randy Silva",
    "email": "randy@easypet.com",
    "password": "123456"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "randy@easypet.com",
    "password": "123456"
}
```

### Usando o token
```http
GET /api/pets
Authorization: Bearer {seu-token-aqui}
```

### Logout
```http
POST /api/users/logout
Authorization: Bearer {seu-token-aqui}
```

---

## 📡 Endpoints

### 🔑 Autenticação
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/auth/register` | Registrar usuário | Não |
| POST | `/api/auth/register/vet` | Registrar veterinário | Não |
| POST | `/api/auth/login` | Login | Não |

### 👤 Usuários
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| GET | `/api/users/me` | Buscar perfil | USER, VET, ADMIN |
| PUT | `/api/users/me` | Atualizar perfil | USER, VET, ADMIN |
| PATCH | `/api/users/me/password` | Trocar senha | USER, VET, ADMIN |
| POST | `/api/users/logout` | Logout | USER, VET, ADMIN |

### 🐾 Pets
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| POST | `/api/pets` | Cadastrar pet | USER, ADMIN |
| GET | `/api/pets` | Listar pets | USER, ADMIN |
| GET | `/api/pets/{id}` | Buscar pet | USER, ADMIN |
| PUT | `/api/pets/{id}` | Atualizar pet | USER, ADMIN |
| DELETE | `/api/pets/{id}` | Deletar pet | USER, ADMIN |

### 🏥 Veterinários
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| GET | `/api/vets` | Listar veterinários | USER, VET, ADMIN |
| GET | `/api/vets/{id}` | Buscar veterinário | USER, VET, ADMIN |
| PUT | `/api/vets/{id}` | Atualizar veterinário | ADMIN |
| DELETE | `/api/vets/{id}` | Desativar veterinário | ADMIN |

### 📅 Agendamentos
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| POST | `/api/appointments` | Criar agendamento | USER, ADMIN |
| GET | `/api/appointments` | Listar agendamentos | USER, ADMIN |
| GET | `/api/appointments/{id}` | Buscar agendamento | USER, ADMIN |
| PATCH | `/api/appointments/{id}/cancel` | Cancelar agendamento | USER, ADMIN |
| GET | `/api/appointments/vet` | Agendamentos do vet | VET |

### 📋 Prontuários (Medical Records)
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| POST | `/api/vets/appointments/{id}/records` | Criar prontuário, exames e receitas | VET |

### 💉 Vacinas
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| POST | `/api/vaccines` | Registrar vacina do pet | USER, ADMIN |
| GET | `/api/vaccines?petId={id}` | Listar vacinas do pet | USER, ADMIN |
| POST | `/api/vets/vaccinations` | Aplicação de vacina pelo Vet | VET, ADMIN |

### 🔔 Notificações & Cron Jobs
| Método | Endpoint | Descrição | Role |
|--------|----------|-----------|------|
| Sistema | `@Scheduled(cron = "0 0 8 * * *")` | Disparo automático diário de lembretes | SYSTEM |
| POST | `/api/notifications/vaccines/check` | Disparo manual programado | ADMIN |

---

## 🗄️ Banco de Dados

### Migrations Flyway

| Versão | Descrição |
|--------|-----------|
| V1 | Criação da tabela `users` |
| V2 | Criação da tabela `pets` |
| V3 | Criação da tabela `vets` |
| V4 | Criação da tabela `appointments` |

### Diagrama de Entidades

```
users
  ├── id, name, email, password, role
  ├── 1:N → pets (owner_id)
  └── 1:1 → vets (user_id)

pets
  ├── id, name, species, breed, birth_date, gender, microchip_number
  ├── N:1 → users (owner_id)
  └── 1:N → vaccines (pet_id)

vets
  ├── id, name, crmv, specialty, phone, active
  └── 1:1 → users (user_id)

appointments
  ├── id, type, status, scheduled_at, notes
  ├── N:1 → pets (pet_id)
  ├── N:1 → vets (vet_id)
  └── 1:1 → medical_records (appointment_id)

vaccines
  ├── id, name, laboratory, lote, dose_date, next_dose_date
  └── N:1 → pets (pet_id)
```

---

## ⚡ Redis

O Redis é utilizado para:

| Funcionalidade | Descrição | TTL |
|---------------|-----------|-----|
| Cache de veterinários | Evita queries repetidas ao banco | 10 minutos |
| Blacklist de tokens | Garante logout seguro | Até expiração do token |

---

## 🌱 Variáveis de Ambiente

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `DB_HOST` | localhost | Host do PostgreSQL |
| `DB_PORT` | 5433 | Porta do PostgreSQL |
| `DB_NAME` | easypet_db | Nome do banco |
| `DB_USER` | easypet_user | Usuário do banco |
| `DB_PASS` | easypet_pass | Senha do banco |
| `REDIS_HOST` | localhost | Host do Redis |
| `REDIS_PORT` | 6379 | Porta do Redis |
| `JWT_SECRET` | - | Chave secreta JWT (obrigatória em produção) |

---

## 🗂️ Postman Collection

Para facilitar os testes, o repositório conta com o arquivo `EasyPet-Postman-Collection.json` na raiz do projeto. Ele contém **todos os endpoints documentados**, com pastas organizadas e injeção automática de **Bearer Token** ao efetuar login. Basta importar no Postman e começar a testar.

---

## 📖 Documentação

| Ambiente | URL |
|----------|-----|
| Local | http://localhost:8080/swagger-ui/index.html |
| Produção | https://easypet-backend-production.up.railway.app/swagger-ui/index.html |

---

## 🚀 Deploy

A API está disponível em produção no Railway:

**Base URL:** `https://easypet-backend-production.up.railway.app`

---

## 🔄 Git Flow

```
main        → código em produção
develop     → integração das features
feat/xxx    → desenvolvimento de features
refactor/xx → refatorações
docs/xx     → documentação
```

---



<div align="center">
  Desenvolvido por <a href="https://github.com/LetsGoRandy">Randy Gomes</a>
</div>