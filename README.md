# TradeMatch 📈
**Algorithmic Order Matching Microservice**

TradeMatch is a backend financial technology engine designed to process high-volume limit orders. It simulates enterprise-grade trading environments by enforcing strict Price-Time priority matching using optimized data structures.

### 🚀 Technical Stack
* **Framework:** Java, Spring Boot 3
* **Database:** MySQL, Spring Data JPA / Hibernate
* **Architecture:** REST API, Asynchronous Event-Driven Simulation

### 🧠 Core Algorithmic Engine
At the heart of TradeMatch is an O(log N) algorithmic matching engine. Instead of iterating through database rows to match trades (which causes severe bottlenecking), the engine utilizes **Max-Heaps** and **Min-Heaps** (Priority Queues in Java) in local memory.
* **Buy Orders** are routed to a Max-Heap (highest price executes first).
* **Sell Orders** are routed to a Min-Heap (lowest price executes first).

### ⚙️ Enterprise Features
* **Message Broker Simulation:** Utilizes Spring `@Async` Event Listeners to decouple trade execution from transaction logging.
* **Security Interceptors:** Implements a custom HTTP routing filter to validate mock Bearer Tokens before granting access to trading endpoints.

### 🛠️ API Endpoints
| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/orders/buy` | Submits a limit buy order |
| `POST` | `/api/orders/sell` | Submits a limit sell order |
