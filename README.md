# Projeto: Rede de Relacionamentos Jackut

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="./.github/cover.png">
  <source media="(prefers-color-scheme: light)" srcset="./.github/cover_light.png">
  <img alt="Projeto: Rede de Relacionamentos Jackut" src="/.github/cover_light.png">
</picture>

<br />
<br />

> [!NOTE]
> As entregas das milestones estão disponíveis no [diretório `docs`](./docs) do repositório.

## Descrição
O Jackut é um sistema que mantém uma rede de relacionamentos, nos moldes de uma série de outras que há na internet hoje em dia (Orkut, Friendster, etc.). Ele é particularmente inspirado no Orkut (www.orkut.com).  
A funcionalidade desejada do Jackut está descrita em User Stories (Use Cases informais). Cada Story descreve uma interação que o sistema deve suportar.  
As User Stories receberam prioridades (pelo cliente interessado) e devem ser implementadas de acordo com tais prioridades.  

Em geral, o Jackut deve ser capaz de:
* Manter um cadastro de informações dos usuários (perfis, álbuns, etc.)  
* Manter uma série de informações de relacionamentos entre os usuários (agrupamentos em comunidades, redes de amizade, de fãs, listas de paqueras, etc.)  
* Manter o fluxo de mensagens entre os usuários do sistema.

## Instruções

O desenvolvimento do sistema será incremental, seguindo marcos (milestones). Em cada um deles, novas funcionalidades serão adicionadas ao sistema.  
Os testes de aceitação avaliarão exclusivamente a lógica de negócio, sem considerar a interface com o usuário.
Essas funcionalidades estão descritas em **User Stories**, que possuem duas partes:
1. Uma descrição informal da funcionalidade desejada e das interações esperadas.
2. Um conjunto formal de testes de aceitação que comprovam a correta implementação da funcionalidade.  
   Antes de entregar cada milestone, é essencial revisar atentamente as recomendações e a lista de requisitos de entrega.

## As Users Stories

As User Stories¹ levantadas inicialmente para o sistema estão mostradas abaixo, priorizadas pelo cliente conforme a descrição dos milestones.  

> ¹**User Stories:** _são uma forma de expressar requisitos funcionais desejados para o sistema (o que o sistema deve fazer)._


| US | Título                           | Breve Descrição                                                                                                                                                                                                                                               |
|:--:|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1  | Criação de conta                 | Permita a um usuário criar uma conta no Jackut. Deve ser fornecido um login, uma senha e um nome com o qual o usuário será conhecido na rede.                                                                                                                 |
| 2  | Criação/Edição de perfil         | Permita a um usuário cadastrado do Jackut criar/editar atributos de seu perfil. Ele deve poder modificar qualquer atributo do perfil ou preencher um atributo inexistente.                                                                                    |
| 3  | Adição de amigos                 | Permita a um usuário cadastrado do Jackut adicionar outro usuário como amigo, o que faz o sistema enviar-lhe um convite. O relacionamento só é efetivado quando o outro usuário o adicionar de volta.                                                         |
| 4  | Envio de recados                 | Permita a um usuário cadastrado do Jackut enviar um recado a qualquer outro usuário cadastrado.                                                                                                                                                               |
| 5  | Criação de comunidades           | Permita a um usuário cadastrado do Jackut criar uma comunidade. Deve ser fornecido um nome e uma descrição. O usuário passa a ser o dono da comunidade e é o responsável por gerenciar os membros.                                                            |
| 6  | Adição de comunidades            | Permita a um usuário cadastrado do Jackut se adicionar a uma comunidade.                                                                                                                                                                                      |
| 7  | Envio de mensagens a comunidades | Permita a um usuário cadastrado do Jackut enviar uma mensagem a uma comunidade. Todos os usuários da comunidade a recebem.                                                                                                                                    |
| 8  | Criação de novos relacionamentos | Permita a um usuário cadastrado do Jackut estabelecer outros tipos de relacionamentos dentro da rede, além de amizade; novos tipos incluem relação de fã-ídolo, paqueras e inimizades. Cada uma tem regras específicas, explicitadas nos testes de aceitação. |
| 9  | Remoção de conta                 | Permita a um usuário encerrar sua conta no Jackut. Todas as suas informações devem sumir do sistema: relacionamentos, mensagens enviadas, perfil.                                                                                                             |


## Testes de Aceitação

A validação das **User Stories** implementadas pelos alunos será feita por meio de um conjunto de **testes de aceitação** pré-definidos. Esses testes serão disponibilizados pelo professor à medida que os **milestones** avançarem.
Os testes serão escritos em uma linguagem de script interpretada pela biblioteca **Easy Accept**, que será fornecida pelo professor.  
Para executar os testes, será necessário criar uma **Façade** que acessará a lógica de negócio do sistema conforme a linguagem de script específica utilizada nos testes.

---

## Linguagem de Script do Easy Accept

Para permitir a execução dos testes de aceitação, será necessário implementar uma **linguagem de script** com os seguintes comandos, que deverão estar mapeados para métodos correspondentes na **Façade**:

- **`zerarSistema`**
  - Apaga todos os dados mantidos no sistema.

- **`criarUsuario login=<String> senha=<String> nome=<String>`**
  - Cria um usuário com os dados fornecidos.

- **`abrirSessao login=<String> senha=<String>`**
  - Abre uma sessão para um usuário e retorna um identificador (`id`) para essa sessão.

- **`getAtributoUsuario login=<String> atributo=<String>`**
  - Retorna o valor de um atributo do perfil do usuário especificado.

- **`editarPerfil id=<String> atributo=<String> valor=<String>`**
  - Modifica o valor de um atributo do perfil de um usuário. É necessário que a sessão esteja aberta (`id` válido).

- **`adicionarAmigo id=<String> amigo=<String>`**
  - Adiciona um amigo ao usuário autenticado na sessão (`id`).

- **`ehAmigo login=<String> amigo=<String>`**
  - Retorna `true` se os dois usuários forem amigos.

- **`getAmigos login=<String>`**
  - Retorna a lista de amigos do usuário especificado (codificada como uma `String`).

- **`enviarRecado id=<String> destinatario=<String> mensagem=<String>`**
  - Envia um recado ao destinatário. O usuário deve estar autenticado (`id` válido).

- **`lerRecado id=<String>`**
  - Retorna o primeiro recado da fila de recados do usuário autenticado (`id`).

- **`encerrarSistema`**
  - Salva os dados em arquivo e encerra o programa. O final de um script equivale à execução deste comando.

---

## **Primeiro Milestone** (User Stories de 1 a 4)

### **Itens a serem entregues:**
- Código-fonte documentado.
- Relatório descrevendo o design do sistema, incluindo um **diagrama de classes**.

### **Instruções de entrega:**
- O código deve ser enviado para um repositório específico no **GitHub** do aluno.
- O repositório será baixado e testado.
- Utilize **apenas caminhos relativos** para arquivos, evitando caminhos absolutos específicos da sua máquina.
- Teste o código em uma máquina diferente da que foi usada no desenvolvimento para garantir compatibilidade.
- **Se o projeto não compilar**, haverá uma penalidade equivalente a 4 dias de atraso (20%).
- O sistema será testado com **JDK 1.5.0 ou superior**.
- **Não** serão feitas alterações em `build.xml` ou arquivos `.bat` para ajustes de classpath ou outras configurações.

### **Critérios de avaliação:**
A nota do projeto será composta pelos seguintes critérios:

| Critério                             | Peso (%)                                        |
|--------------------------------------|-------------------------------------------------|
| **Compilação** *(obrigatório)*       | 0% (se não compilar, o projeto não será aceito) |
| **Execução dos testes de aceitação** | 50%                                             |
| **Qualidade da documentação**        | 20%                                             |
| **Qualidade do design**              | 20%                                             |
| **Qualidade do relatório**           | 10%                                             |

### **Método de correção:**

1. **Compilação**
   - O programa deve compilar. Caso contrário, os alunos serão notificados imediatamente e o projeto será penalizado.
   
2. **Testes de Aceitação**
   - Os testes de aceitação serão executados. 
   - Para cada teste que falhar, será descontado **1 ponto** sobre **10**. 
   - No **milestone 1**, serão testadas as User Stories **1, 2 e 3**. 
   - A partir do **milestone 2**, os testes das User Stories anteriores serão reavaliados.

3. **Relatório**
   - O relatório será avaliado de **0 a 10** conforme a qualidade da explicação e do design apresentado.

4. **Documentação**
   - A qualidade do **Javadoc** será avaliada.
   - Devem estar documentados: **pacotes, classes, métodos, parâmetros, valores de retorno e exceções**.
   - Uma análise geral será feita, atribuindo nota de **0 a 10**.

5. **Design**
   - Será avaliada a qualidade do design do sistema com base no código e/ou no relatório.
   - Os critérios incluem: organização das entidades, responsabilidade dos métodos e atributos, coerência dos relacionamentos entre classes.
   - A nota será de **0 a 10**