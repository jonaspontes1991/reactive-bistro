# 🍽️ Reactive Bistro

> Sistema de gerenciamento de restaurantes de alta performance, focado em **programação reativa**, **arquitetura orientada a eventos** e **alta resiliência**.

---

## 🚀 Sobre o Projeto

O **Reactive Bistro** é um projeto de portfólio criado para demonstrar domínio sobre arquiteturas de software modernas e engenharia de backend escalável.

O sistema gerencia o fluxo completo de um restaurante — **mesas, cardápio, pedidos dos garçons, cozinha e pagamentos** — utilizando um ecossistema **100% não-bloqueante (non-blocking)**, do controller até o banco de dados.

---

## 🛠️ Tecnologias e Stack

| Categoria | Tecnologia |
|---|---|
| **Linguagem** | Kotlin (JVM 25) + Kotlin Coroutines |
| **Framework** | Spring Boot 4 com **Spring WebFlux** (Project Reactor) |
| **Arquitetura** | Arquitetura Hexagonal (Ports and Adapters) / Clean Architecture |
| **Banco de Dados** | PostgreSQL via **Spring Data R2DBC** (não-bloqueante) |
| **Cache** | **Spring Data Reactive Redis** (estratégia *Cache-Aside* para o cardápio) |
| **Mensageria** | **Apache Kafka** (desacoplamento de pedidos para cozinha e estoque) |
| **Resiliência** | **Resilience4j** (Circuit Breaker nas integrações externas de pagamento) |
| **Segurança** | Spring Security (reativo) |
| **Observabilidade** | Spring Boot Actuator & Prometheus *(planejado)* |
| **Testes** | JUnit 5, **Reactor Test (`StepVerifier`)**, Testcontainers e Spring REST Docs |

---

## 🏛️ Arquitetura do Sistema

O projeto segue os princípios da **Arquitetura Hexagonal**, isolando o domínio de negócio de qualquer framework ou tecnologia externa:

```text
com.portifolio.reactivebistro
 ├── domain               <-- Regras de negócio puras, modelos e portas (interfaces)
 ├── application          <-- Casos de uso / orquestração de fluxos
 └── infrastructure
      └── adapter
           ├── in         <-- Adaptadores de entrada (controllers WebFlux, consumers Kafka)
           └── out        <-- Adaptadores de saída (R2DBC, Redis, producers Kafka, gateways)
```

**Regra de dependência:** `infrastructure` → `application` → `domain`. O domínio não conhece Spring, banco ou mensageria.

### 🔄 Fluxo Orientado a Eventos (Kafka)

```text
Garçom ──► API REST Reativa ──► R2DBC (PostgreSQL) ──► Kafka [pedido.criado]
                                                          │
                                       ┌──────────────────┴──────────────────┐
                                       ▼                                     ▼
                                    Cozinha                               Estoque
```

1. O garçom lança um pedido via **API REST reativa**.
2. O sistema persiste o pedido no banco relacional (**R2DBC**) de forma não-bloqueante.
3. Um evento `pedido.criado` é publicado no **Apache Kafka**.
4. Os consumidores da **cozinha** e do **estoque** capturam o evento em tempo real.

---

## ⚙️ Pré-requisitos

- **JDK 25** (o Gradle toolchain está configurado para Java 25)
- **Docker** e **Docker Compose** (para subir PostgreSQL, Redis e Kafka)
- **Gradle** — ou utilize o wrapper incluso (`./gradlew` / `gradlew.bat`)

---

## ▶️ Como Executar

**1. Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/reactive-bistro.git
cd reactive-bistro
```

**2. Suba a infraestrutura (PostgreSQL, Redis e Kafka):**

```bash
docker compose up -d
```

**3. Execute a aplicação:**

```bash
./gradlew bootRun
```

> No Windows, use `gradlew.bat bootRun`.

**Alternativa para desenvolvimento:** rode a classe `TestReactiveBistroApplication` (em `src/test`), que sobe automaticamente os containers necessários via **Testcontainers** — sem precisar de Docker Compose.

---

## 🧪 Testes

```bash
./gradlew test
```

Os testes de integração utilizam **Testcontainers** (Docker precisa estar rodando). A documentação da API é gerada a partir dos testes com **Spring REST Docs**:

```bash
./gradlew asciidoctor
```

---

## 📈 Roadmap

- [ ] Implementar autenticação reativa com Spring Security e JWT
- [ ] Ampliar a cobertura de testes de integração com Testcontainers
- [ ] Adicionar Actuator + Prometheus e criar dashboards no Grafana
- [ ] Criar o `docker-compose.yml` com PostgreSQL, Redis e Kafka

---

## 👨‍💻 Autor

Desenvolvido por **Jonas**.

Se gostou do projeto, considere deixar uma ⭐ no repositório!
