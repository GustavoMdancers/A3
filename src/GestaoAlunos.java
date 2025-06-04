import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GestaoAlunos {

    private List<Aluno> alunos;
    private Scanner scanner;
    private static final String arquivoAlunos = "./BancoDeDados/alunos.csv";

    public GestaoAlunos() {
        this.alunos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        GestaoAlunos gestaoAlunos = new GestaoAlunos();
        gestaoAlunos.carregarAlunos();
        gestaoAlunos.executar();
    }

    public void executar() {
        int opcao = -1;
        while (opcao != 8) {
            exibirMenu();
            try {
                System.out.print("Escolha uma opção: ");
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                } else {
                    System.out.println("Opção inválida. Por favor, insira um número.");
                    scanner.next(); // Limpa o buffer
                    opcao = -1;
                    System.out.println("\n Pressione Enter para continuar...");
                    scanner.nextLine(); // Limpa o buffer
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();

                switch (opcao) {
                    case 1 :
                        cadastrarAluno();
                        break;
                    case 2 :
                        listarAlunos();
                        break;
                    case 3 :
                        buscarAluno();
                        break;
                    case 4 :
                        editarAluno();
                        break;
                    case 5 :
                        excluirAluno();
                        break;
                    case 6 :
                        salvarManual();
                        break;
                    case 7 :
                        carregarManual();
                        break;
                    case 8 :
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número para opção.");
                opcao = -1;
            } catch (Validacao e) {
                System.err.println("Erro de validação: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Erro de IO ao manipular o arquivo: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
            }
            if (opcao != 8) {
                System.out.println("\n Pressione Enter para continuar...");
                scanner.nextLine(); // Limpa o buffer
            }
        }
        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n=== Menu de Gestão de Alunos ===");
        System.out.println("1. Cadastrar Aluno");
        System.out.println("2. Listar Alunos");
        System.out.println("3. Buscar Aluno");
        System.out.println("4. Editar Aluno");
        System.out.println("5. Excluir Aluno");
        System.out.println("6. Salvar Manualmente");
        System.out.println("7. Carregar Manualmente");
        System.out.println("8. Sair");
        System.out.println("===============================");
    }

    private void cadastrarAluno() throws Validacao, IOException {
        System.out.println("\n=== Cadastrar Aluno ===");
        System.out.print("Matricula: ");
        String matricula = scanner.nextLine();

        if (buscarAlunoMatricula(matricula) != null) {
            System.out.println("Aluno com matrícula " + matricula + " já cadastrado.");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (somente números ou XXX.XXX.XXX-XX): ");
        String cpf = scanner.nextLine();
        System.out.print("Data de Nascimento (DD/MM/AAAA): ");
        String dataNascimento = scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();

        int periodo = 0;
        boolean periodoValido = false;
        while (!periodoValido) {
            try {
                System.out.print("Periodo (ex: 1, 2): ");
                if (scanner.hasNextInt()) {
                    periodo = scanner.nextInt();
                    if (periodo > 0) {
                        periodoValido = true;
                    } else {
                        System.out.println("Período deve ser um número positivo.");
                    }
                } else {
                    System.out.println("Período inválido. Por favor, insira um número.");
                    scanner.next(); // Limpa o buffer
                }
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número para o período.");
                scanner.next(); // Limpa o buffer
            }
        }
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Aluno novoAluno = new Aluno(nome, matricula, curso, cpf, email, dataNascimento, periodo);

        alunos.add(novoAluno);
        salvarDados();
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private void listarAlunos() {
        System.out.println("\n=== Listar Alunos ===");
        if (alunos.isEmpty()) {
            System.out.println ("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            System.out.println(aluno.toString());
        }
    }

    private Aluno buscarAlunoMatricula(String matricula) {
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(matricula)) {
                return aluno;
            }
        }
        return null;
    }

    private void buscarAluno() {
        System.out.println("\n=== Buscar Aluno Matricula ===");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = scanner.nextLine();

        Aluno alunoEncontrado = buscarAlunoMatricula(matricula);

        if (alunoEncontrado != null) {
            System.out.println("Aluno encontrado: " + alunoEncontrado.toString());
        } else {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matricula);
        }
    }

    private void editarAluno() throws Validacao, IOException {
        System.out.println("\n=== Editar Aluno ===");
        System.out.print("Digite a matrícula do aluno a ser editado: ");
        String matriculaOriginal = scanner.nextLine();

        Aluno alunoEditado = null;
        int indice = -1;
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equalsIgnoreCase(matriculaOriginal)) {
                alunoEditado = alunos.get(i);
                indice = i;
                break;
            }
        }
        if (alunoEditado == null) {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matriculaOriginal);
            return;
        }

        System.out.println("Dados do Aluno a ser editado: ");
        System.out.println(alunoEditado.toString());
        System.out.print("Deixe o campo vazio para não alterar.\n");

        Aluno dadosAtualizados = new Aluno(
                alunoEditado.getNome(), alunoEditado.getMatricula(), alunoEditado.getCurso(),
                alunoEditado.getCpf(), alunoEditado.getEmail(), alunoEditado.getDataNascimento(),
                alunoEditado.getPeriodo()
        );

        System.out.print("Nova Matricula (" + alunoEditado.getMatricula() + "): ");
        String novaMatricula = scanner.nextLine();
        if (!novaMatricula.trim().isEmpty()) {
            if (!novaMatricula.equalsIgnoreCase(matriculaOriginal) && buscarAlunoMatricula(novaMatricula) != null) {
                System.out.println("Nova matrícula já existe '" + novaMatricula + "'. Mantendo a original.");
            } else {
                dadosAtualizados.setMatricula(novaMatricula);
            }
        }

        System.out.println("Novo nome (" + alunoEditado.getNome() + "): ");
        String nome = scanner.nextLine();
        if (!nome.trim().isEmpty()) dadosAtualizados.setNome(nome);

        System.out.print("Novo CPF (" + alunoEditado.getCpf() + "): ");
        String cpf = scanner.nextLine();
        if (!cpf.trim().isEmpty()) dadosAtualizados.setCpf(cpf);

        System.out.print("Nova Data de Nascimento (" + alunoEditado.getDataNascimento() + "): ");
        String dataNascimento = scanner.nextLine();
        if (!dataNascimento.trim().isEmpty()) dadosAtualizados.setDataNascimento(dataNascimento);

        System.out.print("Novo Curso (" + alunoEditado.getCurso() + "): ");
        String curso = scanner.nextLine();
        if (!curso.trim().isEmpty()) dadosAtualizados.setCurso(curso);

        System.out.print("Novo Periodo (" + alunoEditado.getPeriodo() + "): ");
        String periodoStr = scanner.nextLine();
        if (!periodoStr.trim().isEmpty()) {
            try {
                int periodo = Integer.parseInt(periodoStr);
                dadosAtualizados.setPeriodo(periodo);
            } catch (NumberFormatException e) {
                System.out.println("Período inválido. Mantendo o original.");
            } catch (Validacao e) {
                System.err.println("Erro ao definir período: " + e.getMessage() + ". Mantendo o original (" + dadosAtualizados.getPeriodo() + ").");
            }
        }

        System.out.print("Novo Email (" + alunoEditado.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.trim().isEmpty()) dadosAtualizados.setEmail(email);

        alunos.set(indice, dadosAtualizados);
        salvarDados();
        System.out.println("Aluno editado com sucesso!");
    }

    private void excluirAluno() throws IOException {
        System.out.println("\n=== Excluir Aluno ===");
        System.out.print("Digite a matrícula do aluno a ser excluído: ");
        String matricula = scanner.nextLine();

        Aluno alunoExcluido = null;
        int indice = -1;
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                alunoExcluido = alunos.get(i);
                indice = i;
                break;
            }
        }
        if (alunoExcluido == null) {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matricula);
            return;
        }

        System.out.println("Aluno encontrado: " + alunoExcluido.toString());
        System.out.print("Tem certeza que deseja excluir este aluno? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            alunos.remove(indice);
            salvarDados();
            System.out.println("Aluno excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private void salvarDados() throws IOException {
        try {
            AlunoCSV.salvarAlunos(this.alunos, arquivoAlunos);
            System.out.println("Dados salvos com sucesso em " + arquivoAlunos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
            throw e; // Re-throw the exception to handle it in the main loop
        }
    }

    private void salvarManual() {
        try {
            salvarDados();
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private void carregarAlunos() {
        try {
            this.alunos = AlunoCSV.lerAlunos(arquivoAlunos);
            System.out.println("Alunos carregados com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar os alunos: " + e.getMessage());
        } catch (Validacao e) {
            System.err.println("Erro de validação ao carregar os alunos: " + e.getMessage());
        }
    }

    private void carregarManual() {
        System.out.println("Tentando carregar alunos do arquivo manualmente...");
        carregarAlunos();
    }

}