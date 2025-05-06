# Entrega - Milestone 2

## Relat�rio

> [!NOTE]
> O relat�rio formatado nas normas ABNT pode ser acessado [neste link](https://docs.google.com/document/d/1jIHBDsVWXQEOZ4SOZoOyVwkcakcXL9rmGmixtXmOC0w/edit?usp=sharing).

## Introdu��o

---

Neste segundo milestone, o sistema do Jackut foi expandido para incluir as funcionalidades das User Stories 5 a 9, mantendo o design baseado no padr�o Facade e incorporando novos padr�es de projeto para lidar com a crescente complexidade do sistema.

## Desenvolvimento

---

A implementa��o das User Stories 5 a 9 exigiu a introdu��o de novas classes e servi�os especializados, al�m da adapta��o da arquitetura existente para acomodar as mudan�as sem comprometer funcionalidades anteriores.  

A abordagem orientada a objetos foi mantida como base, com uso estrat�gico de padr�es como Facade, Singleton e Strategy, permitindo que a l�gica do sistema se mantivesse modular e extens�vel.  

A seguir, ser�o detalhadas as decis�es de arquitetura adotadas, os padr�es utilizados, as novas classes implementadas e as principais funcionalidades entregues neste milestone.

### Padr�es de Design Utilizados

#### Facade
O padr�o Facade continuou sendo utilizado como principal interface entre os testes de aceita��o e a l�gica de neg�cio.  
Para isso, a classe Facade foi expandida a fim de incluir os novos m�todos que implementam as funcionalidades das User Stories 5-9:

- Cria��o e gerenciamento de comunidades
- Adi��o de usu�rios a comunidades
- Envio de mensagens para comunidades
- Cria��o de novos tipos de relacionamentos (f�-�dolo, paqueras, inimizades)
- Remo��o de contas de usu�rio

#### Singleton
O padr�o Singleton foi implementado para garantir que certas classes tenham apenas uma inst�ncia durante toda a execu��o do programa. Por exemplo:

- `UsuarioService`: Singleton que centraliza todas as opera��es relacionadas aos usu�rios
- `ComunidadeService`: Singleton que gerencia todas as opera��es relacionadas �s comunidades

Este padr�o ajudou a evitar inconsist�ncias nos dados e garantir que os servi�os compartilhassem o mesmo estado durante toda a execu��o do programa.

#### Strategy
Para lidar com os diferentes tipos de relacionamentos (amizade, f�-�dolo, paqueras, inimizades), implementamos o padr�o **Strategy**:

- `RelacionamentoStrategy`: Interface que define o comportamento comum para todos os tipos de relacionamentos
- `AmizadeStrategy`: Implementa��o espec�fica para relacionamentos de amizade
- `FaIdoloStrategy`: Implementa��o espec�fica para relacionamentos f�-�dolo
- `PaqueraStrategy`: Implementa��o espec�fica para paqueras
- `InimizadeStrategy`: Implementa��o espec�fica para inimizades

A classe abstrata `RelacionamentoStrategyBase` fornece implementa��es comuns, enquanto as classes concretas implementam comportamentos espec�ficos para cada tipo de relacionamento.

### Novas Classes

#### Comunidades
- `Comunidade`: Representa uma comunidade no sistema, armazenando nome, descri��o, dono e membros
- `ComunidadeService`: Gerencia todas as opera��es relacionadas as comunidades

#### Relacionamentos
- `RelacionamentoStrategy`: Interface para estrat�gias de relacionamento
- `RelacionamentoStrategyBase`: Implementa��o base para todas as estrat�gias
- Implementa��es espec�ficas: `AmizadeStrategy`, `FaIdoloStrategy`, `PaqueraStrategy`, `InimizadeStrategy`
- `RelacionamentoService`: Servi�o que coordena todas as estrat�gias de relacionamento

#### Mensagens
- `MensagemService`: Gerencia o envio de mensagens para comunidades
- `RecadoService`: Servi�o aprimorado para gerenciar recados entre usu�rios

### Principais Funcionalidades Implementadas

#### User Story 5: Cria��o de comunidades
Nesse user story foi implementada a funcionalidade que permite aos usu�rios criar comunidades com nome e descri��o. O criador se torna automaticamente o dono da comunidade.  
Quando o usu�rio exclui sua conta, a comunidade � apagada juntamente com a conta e todos os membros s�o removidos automaticamente.

#### User Story 6: Adi��o de comunidades
Outra demanda atendida foi a adi��o da capacidade de os usu�rios se adicionarem a comunidades existentes.

#### User Story 7: Envio de mensagens a comunidades
Tamb�m foi implementado o envio de mensagens para comunidades, onde todos os membros recebem a mesma mensagem.

#### User Story 8: Cria��o de novos relacionamentos
Para a entrega desse user story, foram adicionados tr�s novos tipos de relacionamentos al�m da amizade:

- F�-�dolo: Um usu�rio pode adicionar outro como �dolo e se tornar seu f�, uma informa��o p�blica
- Paqueras: Usu�rios podem se adicionar como paqueras, recebendo uma notifica��o do Jackut caso a adi��o seja m�tua
- Inimizades: Usu�rios podem adicionar outros como inimigos, bloqueando intera��es

#### User Story 9: Remo��o de conta
Por fim, foi implementada a funcionalidade que permite aos usu�rios remover suas contas, eliminando todas as suas informa��es do sistema.

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

## Conclus�o

---

Em resumo, para a entrega desse milestone, a implementa��o das User Stories 5-9 seguiu os princ�pios de design orientado a objetos, utilizando padr�es de projeto como Facade, Singleton e Strategy para criar um sistema modular, extens�vel e de f�cil manuten��o.  

A introdu��o de novos tipos de relacionamentos e as diversas novas funcionalidades, como as comunidades, enriqueceram significativamente o sistema Jackut, proporcionando uma experi�ncia mais completa aos usu�rios.  

Com a atualiza��o, a persist�ncia de dados foi mantida atrav�s do uso do sistema de serializa��o, garantindo que todas as informa��es sejam preservadas entre sess�es. Isso inclui as comunidades, os novos tipos de relacionamentos e as mensagens enviadas para comunidades.