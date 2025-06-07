Trabalho A3 para a matéria de Programação de soluções computacionais

Gustavo Morais - 1242022304


A classe GestaoAlunos oferece uma interface de linha de comando (CLI) para realizar operações CRUD (Criar, Ler, Editar, Excluir) em registros de alunos. A classe Aluno encapsula os dados de cada estudante (nome, matrícula, curso, etc.) e inclui métodos para validar essas informações, lançando uma exceção Validacao em caso de dados inválidos. A persistência dos dados é gerenciada pela classe AlunoCSV, que lê e escreve os registros dos alunos em um arquivo CSV, permitindo que as informações sejam salvas e carregadas entre as sessões do programa.

Classes Principais

Aluno

* Representa um aluno universitário.
* Contém atributos como nome, matrícula, curso, CPF, e-mail, data de nascimento e período.
* Possui métodos getters e setters que lançam exceções de validação (Validacao) quando dados inválidos são inseridos.
* Converte dados para e de strings CSV.

Validacao

* Exceção personalizada para erros de validação dos dados dos alunos.

AlunoCSV

* Classe responsável pela leitura e escrita dos dados de alunos em arquivos CSV.
* Possui métodos estáticos para salvar uma lista de alunos, adicionar um aluno e ler alunos de um arquivo.
* Pode lançar exceções de I/O e de validação.

GestaoAlunos

* Classe principal que gerencia o ciclo de vida dos alunos e a interação com o usuário via CLI.
* Mantém uma lista de objetos Aluno.
* Utiliza a classe AlunoCSV para persistência.
* Usa Scanner para leitura de dados do usuário.
* Trata exceções de validação e de I/O.
* Implementa operações CRUD (Criar, Ler, Atualizar, Excluir).
