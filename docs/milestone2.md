# Entrega - Milestone 2

## Relatório

> [!NOTE]
> O relatório formatado nas normas ABNT pode ser acessado [neste link](https://docs.google.com/document/d/1jIHBDsVWXQEOZ4SOZoOyVwkcakcXL9rmGmixtXmOC0w/edit?usp=sharing).

## Introdução

---

Neste segundo milestone, o sistema do Jackut foi expandido para incluir as funcionalidades das User Stories 5 a 9, mantendo o design baseado no padrão Facade e incorporando novos padrões de projeto para lidar com a crescente complexidade do sistema.

## Desenvolvimento

---

A implementação das User Stories 5 a 9 exigiu a introdução de novas classes e serviços especializados, além da adaptação da arquitetura existente para acomodar as mudanças sem comprometer funcionalidades anteriores.  

A abordagem orientada a objetos foi mantida como base, com uso estratégico de padrões como Facade, Singleton e Strategy, permitindo que a lógica do sistema se mantivesse modular e extensível.  

A seguir, serão detalhadas as decisões de arquitetura adotadas, os padrões utilizados, as novas classes implementadas e as principais funcionalidades entregues neste milestone.

### Padrões de Design Utilizados

#### Facade
O padrão Facade continuou sendo utilizado como principal interface entre os testes de aceitação e a lógica de negócio.  
Para isso, a classe Facade foi expandida a fim de incluir os novos métodos que implementam as funcionalidades das User Stories 5-9:

- Criação e gerenciamento de comunidades
- Adição de usuários a comunidades
- Envio de mensagens para comunidades
- Criação de novos tipos de relacionamentos (fã-ídolo, paqueras, inimizades)
- Remoção de contas de usuário

#### Singleton
O padrão Singleton foi implementado para garantir que certas classes tenham apenas uma instância durante toda a execução do programa. Por exemplo:

- `UsuarioService`: Singleton que centraliza todas as operações relacionadas aos usuários
- `ComunidadeService`: Singleton que gerencia todas as operações relacionadas ãs comunidades

Este padrão ajudou a evitar inconsistências nos dados e garantir que os serviços compartilhassem o mesmo estado durante toda a execução do programa.

#### Strategy
Para lidar com os diferentes tipos de relacionamentos (amizade, fã-ídolo, paqueras, inimizades), implementamos o padrão **Strategy**:

- `RelacionamentoStrategy`: Interface que define o comportamento comum para todos os tipos de relacionamentos
- `AmizadeStrategy`: Implementação específica para relacionamentos de amizade
- `FaIdoloStrategy`: Implementação específica para relacionamentos fã-ídolo
- `PaqueraStrategy`: Implementação específica para paqueras
- `InimizadeStrategy`: Implementação específica para inimizades

A classe abstrata `RelacionamentoStrategyBase` fornece implementações comuns, enquanto as classes concretas implementam comportamentos específicos para cada tipo de relacionamento.

### Novas Classes

#### Comunidades
- `Comunidade`: Representa uma comunidade no sistema, armazenando nome, descrição, dono e membros
- `ComunidadeService`: Gerencia todas as operações relacionadas as comunidades

#### Relacionamentos
- `RelacionamentoStrategy`: Interface para estratégias de relacionamento
- `RelacionamentoStrategyBase`: Implementação base para todas as estratégias
- Implementações específicas: `AmizadeStrategy`, `FaIdoloStrategy`, `PaqueraStrategy`, `InimizadeStrategy`
- `RelacionamentoService`: Serviço que coordena todas as estratégias de relacionamento

#### Mensagens
- `MensagemService`: Gerencia o envio de mensagens para comunidades
- `RecadoService`: Serviço aprimorado para gerenciar recados entre usuários

### Principais Funcionalidades Implementadas

#### User Story 5: Criação de comunidades
Nesse user story foi implementada a funcionalidade que permite aos usuários criar comunidades com nome e descrição. O criador se torna automaticamente o dono da comunidade.  
Quando o usuário exclui sua conta, a comunidade é apagada juntamente com a conta e todos os membros são removidos automaticamente.

#### User Story 6: Adição de comunidades
Outra demanda atendida foi a adição da capacidade de os usuários se adicionarem a comunidades existentes.

#### User Story 7: Envio de mensagens a comunidades
Também foi implementado o envio de mensagens para comunidades, onde todos os membros recebem a mesma mensagem.

#### User Story 8: Criação de novos relacionamentos
Para a entrega desse user story, foram adicionados três novos tipos de relacionamentos além da amizade:

- Fã-ídolo: Um usuário pode adicionar outro como ídolo e se tornar seu fã, uma informação pública
- Paqueras: Usuários podem se adicionar como paqueras, recebendo uma notificação do Jackut caso a adição seja mútua
- Inimizades: Usuários podem adicionar outros como inimigos, bloqueando interações

#### User Story 9: Remoção de conta
Por fim, foi implementada a funcionalidade que permite aos usuários remover suas contas, eliminando todas as suas informações do sistema.

## Diagrama de Classes

```mermaid
classDiagram
    class Facade {
        +criarComunidade(id: String, nome: String, descricao: String) void
        +getDescricaoComunidade(nome: String) String
        +getDonoComunidade(nome: String) String
        +getMembrosComunidade(nome: String) String
        +adicionarComunidade(id: String, nome: String) void
        +getComunidades(login: String) String
        +enviarMensagem(id: String, comunidade: String, mensagem: String) void
        +lerMensagem(id: String) String
        +adicionarIdolo(id: String, idolo: String) void
        +ehFa(login: String, idolo: String) boolean
        +getFas(login: String) String
        +adicionarPaquera(id: String, paquera: String) void
        +ehPaquera(id: String, paquera: String) boolean
        +getPaqueras(id: String) String
        +adicionarInimigo(id: String, inimigo: String) void
        +removerUsuario(id: String) void
    }
    
    class UsuarioService {
        -static UsuarioService instance
        -Map~String, Usuario~ usuarios
        +getInstance() UsuarioService
        +getUsuario(login: String) Usuario
        +criarUsuario(login: String, senha: String, nome: String) void
        +salvarDados() void
        +carregarDados() void
        +removerUsuario(login: String) void
    }
    
    class Usuario {
        -Map~String, String~ atributos
        -List~String~ amigos
        -List~String~ idolos
        -List~String~ fas
        -List~String~ paqueras
        -List~String~ inimigos
        -Queue~String~ recados
        -Queue~String~ mensagens
        +Usuario(login: String, senha: String, nome: String)
        +adicionarIdolo(idolo: String) void
        +ehIdolo(idolo: String) boolean
        +adicionarFa(fa: String) void
        +ehFa(fa: String) boolean
        +adicionarPaquera(paquera: String) void
        +ehPaquera(paquera: String) boolean
        +adicionarInimigo(inimigo: String) void
        +ehInimigo(inimigo: String) boolean
        +adicionarMensagem(mensagem: String) void
        +lerMensagem() String
    }
    
    class ComunidadeService {
        -static ComunidadeService instance
        -Map~String, Comunidade~ comunidades
        +getInstance() ComunidadeService
        +criarComunidade(login: String, nome: String, descricao: String) void
        +adicionarUsuario(nome: String, login: String) void
        +getComunidade(nome: String) Comunidade
        +enviarMensagemParaComunidade(comunidade: String, mensagem: String) void
    }
    
    class Comunidade {
        -String nome
        -String descricao
        -String dono
        -Set~String~ membros
        +Comunidade(nome: String, descricao: String, dono: String)
        +adicionarMembro(login: String) void
        +getMembros() Set~String~
        +getDono() String
        +getDescricao() String
    }
    
    class RelacionamentoStrategy {
        <<interface>>
        +adicionarRelacionamento(usuarioLogin: String, alvoLogin: String) void
        +verificarRelacionamento(usuarioLogin: String, alvoLogin: String) boolean
        +listarRelacionamentos(usuarioLogin: String) String
    }
    
    class RelacionamentoStrategyBase {
        <<abstract>>
        #UsuarioService usuarioService
        #formatarLista(lista: Collection~String~) String
        #validarAutoRelacionamento(usuarioLogin: String, alvoLogin: String) void
        #validarInimizade(usuario: Usuario, alvo: Usuario) void
        #salvarAlteracoes() void
    }
    
    class AmizadeStrategy {
        +adicionarRelacionamento(usuarioLogin: String, amigoLogin: String) void
        +verificarRelacionamento(usuarioLogin: String, amigoLogin: String) boolean
        +listarRelacionamentos(usuarioLogin: String) String
    }
    
    class FaIdoloStrategy {
        +adicionarRelacionamento(faLogin: String, idoloLogin: String) void
        +verificarRelacionamento(faLogin: String, idoloLogin: String) boolean
        +listarRelacionamentos(usuarioLogin: String) String
    }
    
    class PaqueraStrategy {
        +adicionarRelacionamento(usuarioLogin: String, paqueraLogin: String) void
        +verificarRelacionamento(usuarioLogin: String, paqueraLogin: String) boolean
        +listarRelacionamentos(usuarioLogin: String) String
    }
    
    class InimizadeStrategy {
        +adicionarRelacionamento(usuarioLogin: String, inimigoLogin: String) void
        +verificarRelacionamento(usuarioLogin: String, inimigoLogin: String) boolean
        +listarRelacionamentos(usuarioLogin: String) String
    }
    
    class Database {
        +salvarDados(usuarios: Map~String, Usuario~, comunidades: Map~String, Comunidade~) void
        +carregarDados() Object[]
    }
    
    Facade --> UsuarioService : usa
    Facade --> ComunidadeService : usa
    UsuarioService --> Usuario : gerencia
    ComunidadeService --> Comunidade : gerencia
    RelacionamentoStrategyBase --|> RelacionamentoStrategy
    AmizadeStrategy --|> RelacionamentoStrategyBase
    FaIdoloStrategy --|> RelacionamentoStrategyBase
    PaqueraStrategy --|> RelacionamentoStrategyBase
    InimizadeStrategy --|> RelacionamentoStrategyBase
    UsuarioService --> Database : usa
    ComunidadeService --> Database : usa
```

## Conclusão

---

Em resumo, para a entrega desse milestone, a implementação das User Stories 5-9 seguiu os princípios de design orientado a objetos, utilizando padrões de projeto como Facade, Singleton e Strategy para criar um sistema modular, extensível e de fácil manutenção.  

A introdução de novos tipos de relacionamentos e as diversas novas funcionalidades, como as comunidades, enriqueceram significativamente o sistema Jackut, proporcionando uma experiência mais completa aos usuários.  

Com a atualização, a persistência de dados foi mantida através do uso do sistema de serialização, garantindo que todas as informações sejam preservadas entre sessões. Isso inclui as comunidades, os novos tipos de relacionamentos e as mensagens enviadas para comunidades.