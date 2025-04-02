# Entrega - Milestone 1

## Relat�rio

Como pedido nas especifica��es do projeto, o sistema foi desenvolvido utilizando o padr�o de projeto **Facade**, o qual � utilizado para fornecer uma interface simplificada para um subsistema complexo,

### Classes

A implementa��o do sistema segue os princ�pios de encapsulamento e abstra��o, garantindo que os detalhes internos sejam ocultos dos usu�rios e que a interface fornecida seja clara e f�cil de usar.  

Al�m disso, o sistema foi projetado para ser extens�vel, permitindo a adi��o de novas funcionalidades no futuro sem comprometer a estrutura existente.

Assim sendo, abaixo segue um resumo das principais classes do sistema:
- **Facade**: Classe principal que gerencia a l�gica de neg�cio do sistema,  interagindo com a classe **Database** para persist�ncia de dados e com a classe **Usuario** para manipula��o das informa��es dos usu�rios.
- **Database**: Classe respons�vel por salvar e carregar os dados dos usu�rios em um arquivo, garantindo que as informa��es sejam mantidas mesmo ap�s o encerramento do sistema. Ela utiliza a serializa��o para armazenar informa��es em um arquivo.
- **Usuario**: Classe que representa cada usu�rio do sistema, armazenando suas informa��es pessoais, amigos, convites pendentes, recados, etc. Ela possui diversos m�todos para manipular esses dados.

> [!NOTE]  
> O relat�rio em formato `.doc` pode ser encontrado [neste link](https://docs.google.com/document/d/1MKG_iAf1WyufKZPILbJ4sDtHYWviTYY2e4wumGcxqOc/edit?usp=sharing)

## Diagrama de Classes

```mermaid
classDiagram
    class Facade {
        -static Database db
        -Map~String, Usuario~ usuarios
        -Map~String, String~ sessoes
        -int contadorSessao
        +Facade()
        +zerarSistema() void
        +criarUsuario(login: String, senha: String, nome: String) void
        -validarLogin(login: String) void
        -validarSenha(senha: String) void
        +abrirSessao(login: String, senha: String) String
        +getAtributoUsuario(login: String, atributo: String) String
        +editarPerfil(id: String, atributo: String, valor: String) void
        +adicionarAmigo(id: String, amigoLogin: String) void
        +ehAmigo(login: String, amigoLogin: String) boolean
        +getAmigos(login: String) String
        +enviarRecado(id: String, destinatario: String, mensagem: String) void
        +lerRecado(id: String) String
        +encerrarSistema() void
    }
    
    class Database {
        -static final String DATA_FILE
        +salvarDados(usuarios: Map~String, Usuario~) void
        +carregarDados() Map~String, Usuario~
    }
    
    class Usuario {
        -static final long serialVersionUID
        -String login
        -String senha
        -String nome
        -Map~String, String~ atributos
        -List~String~ amigos
        -List~String~ convitesEnviados
        -Queue~String~ recados
        +Usuario(login: String, senha: String, nome: String)
        +getLogin() String
        +getSenha() String
        +getAtributo(atributo: String) String
        +setAtributo(atributo: String, valor: String) void
        +getAmigos() List~String~
        +enviarConviteAmizade(loginAmigo: String) void
        +aceitarAmizade(loginAmigo: String) void
        +verificarConvitePendente(loginAmigo: String) boolean
        +ehAmigo(loginAmigo: String) boolean
        +adicionarRecado(recado: String) void
        +lerRecado() String
    }
    
    Facade --> Database : usa
    Facade --> Usuario : gerencia
```