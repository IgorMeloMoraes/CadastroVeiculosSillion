# [NOME DO SEU APP] - Gerenciador de Veículos

Este é um aplicativo Android nativo desenvolvido como parte de um processo de avaliação. O objetivo principal é permitir o cadastro e listagem de veículos, com foco em código limpo, arquitetura moderna e integração com Firebase.

## Funcionalidades (Features)

* **Listagem de Veículos:** Exibe todos os veículos cadastrados em uma lista.
* **Sincronização com Firebase:** A lista é sincronizada automaticamente com o Firebase Firestore no início.
* **Adicionar Veículo (Create):** Permite o cadastro de novos veículos através de um formulário completo.
* **Editar Veículo (Update):** Permite editar os dados de um veículo existente (clicando no item da lista).
* **Excluir Veículo (Delete):** Permite excluir um veículo (deslizando o item da lista).
* **Persistência Offline (Offline-first):** O app funciona offline, exibindo dados locais (do Room) e sincronizando com a nuvem quando há conexão.

---

## Como Executar o App

Para compilar e executar este projeto, são necessários alguns passos de configuração:

### 1. Pré-requisitos
* Android Studio (Narwhal | 2025.1.4 ou mais recente)
* JDK 17 ou superior
* Conta do Google para acesso ao Firebase

### 2. Configuração do Firebase
Este projeto utiliza o Firebase Firestore como banco de dados remoto. Para que o app possa se conectar à sua instância, você deve:

1.  Criar um novo projeto no [Firebase Console](https://console.firebase.google.com/).
2.  Adicionar um aplicativo Android a este projeto. O **Package Name** (Application ID) deve ser exatamente `com.kingcreationslabs.sillioncadastroveiculosmanager` (ou o que você definiu no seu `build.gradle.kts`).
3.  No console do Firebase, criar um banco de dados **Cloud Firestore** (em modo de teste).
4.  Baixar o arquivo `google-services.json` fornecido pelo Firebase.
5.  Colocar este arquivo `google-services.json` dentro da pasta `app/` do projeto (no mesmo nível do `build.gradle.kts` do módulo).

### 3. Compilar e Executar
Após adicionar o `google-services.json`, o projeto está pronto. Basta clicar em "Run 'app'" no Android Studio.

---

## Arquitetura e Tecnologias ("O Porquê")

A arquitetura do projeto foi desenhada para ser moderna, escalável, testável e robusta, seguindo as melhores práticas recomendadas pelo Google.

### 1. Arquitetura Principal: MVVM + Repository
O projeto segue o padrão **MVVM (Model-View-ViewModel)**.

* **View (Jetpack Compose):** A UI (Composables como `VehicleListScreen`) é declarativa e "burra". Ela apenas *observa* mudanças de estado.
* **ViewModel (Jetpack ViewModel):** O "cérebro" da UI (`VehicleListViewModel`). Ele expõe o estado da UI (via `StateFlow`) e delega a lógica de negócios ao...
* **Repository:** A camada de dados (`VehicleRepository`). Este é o "cérebro" da lógica de dados. Ele é o **único** que sabe *de onde* os dados vêm (Room ou Firebase).
* **Model:** As classes de dados (`Vehicle.kt`) e as fontes de dados (Room DAO, Firebase).

### 2. Fonte Única da Verdade (Single Source of Truth - SSOT)
Este projeto implementa uma arquitetura **Offline-first**.

* **A Fonte da Verdade para a UI é o Room:** A UI (via ViewModel) *sempre* lê e observa os dados do banco de dados local (Room). Isso torna o app instantâneo e funcional mesmo sem internet.
* **O Firebase é a Fonte da Verdade Remota:** O `Repository` é responsável por "sincronizar" o Room.
    * **Leitura (Read):** Ao iniciar, o app exibe os dados do Room e, em paralelo, busca atualizações no Firebase, salvando-as no Room (o que atualiza a UI automaticamente).
    * **Escrita (C/U/D):** Toda operação de escrita (Create, Update, Delete) é executada **primeiro no Firebase**. Se for bem-sucedida, é executada no Room. Isso garante consistência entre a nuvem e o cache local.

### 3. Stack de Tecnologias

| Tecnologia | O que é? | Porquê foi utilizada? |
| --- | --- | --- |
| **Kotlin** | Linguagem de programação | A linguagem oficial para Android. Usada por sua concisão, segurança (null-safety) e Coroutines. |
| **Jetpack Compose** | UI Toolkit | O framework moderno e declarativo para UIs. Resulta em menos código, é mais rápido de iterar e facilita a criação de estados de UI complexos. |
| **Kotlin Coroutines & Flow** | Programação Assíncrona | Usado para todas as operações de I/O (rede e banco). O `Flow` foi um requisito (`LiveData/Flow`) e é a solução nativa do Kotlin para fluxos de dados reativos, integrando-se perfeitamente com Room e Compose. |
| **Hilt** | Injeção de Dependência | Simplifica a Injeção de Dependência no Android. Usado para "injetar" o Repository no ViewModel e as Fontes de Dados (Room, Firebase) no Repository, criando um código desacoplado e fácil de testar. |
| **Jetpack Navigation** | Navegação | Requisito do projeto. Gerencia a navegação entre as telas (Composables) de forma centralizada (`AppNavigation.kt`) e robusta, incluindo a passagem de argumentos (como a placa do veículo). |
| **Room** | Banco de Dados Local (SQL) | Requisito do projeto (`Room/SP`). Escolhido por ser um ORM robusto, ideal para armazenar e consultar dados estruturados (veículos) e servir como cache offline (SSOT). |
| **Firebase Firestore** | Banco de Dados Remoto (NoSQL) | Requisito do projeto (`Firebase`). Usado para a persistência em nuvem e sincronização de dados em tempo real. |