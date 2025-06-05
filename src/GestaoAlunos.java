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
                    scanner.next();
                    opcao = -1;
                    continue;
                }
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarAluno();
                        break;
                    case 2:
                        listarAlunos();
                        break;
                    case 3:
                        buscarAluno();
                        break;
                    case 4:
                        editarAluno();
                        break;
                    case 5:
                        excluirAluno();
                        break;
                    case 6:
                        salvarManual();
                        break;
                    case 7:
                        carregarManual();
                        break;
                    case 8:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida para opção. Por favor, insira um número.");
                scanner.nextLine();
                opcao = -1;
            } catch (IOException e) {
                System.err.println("Erro de I/O ao manipular o arquivo de dados: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }

            if (opcao != 8 && opcao != -1) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
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
        System.out.println("6. Salvar Dados (Manual)");
        System.out.println("7. Carregar Dados (Manual)");
        System.out.println("8. Sair");
        System.out.println("===============================");
    }

    private void cadastrarAluno() throws IOException {
        System.out.println("\n=== Cadastrar Aluno ===");

        Aluno novoAluno = new Aluno();

        boolean matriculaValida = false;
        while (!matriculaValida) {
            System.out.print("Matrícula: ");
            String matriculaInput = scanner.nextLine();
            if (buscarAlunoMatricula(matriculaInput) != null) {
                System.out.println("Erro: Matrícula já cadastrada. Tente outra.");
                continue;
            }
            try {
                novoAluno.setMatricula(matriculaInput);
                matriculaValida = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        boolean nomeValido = false;
        while (!nomeValido) {
            System.out.print("Nome: ");
            String nomeInput = scanner.nextLine();
            try {
                novoAluno.setNome(nomeInput);
                nomeValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        boolean cpfValido = false;
        while (!cpfValido) {
            System.out.print("CPF (XXX.XXX.XXX-XX ou XXXXXXXXXXX): ");
            String cpfInput = scanner.nextLine();
            try {
                novoAluno.setCpf(cpfInput);
                cpfValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        boolean dataNascimentoValida = false;
        while (!dataNascimentoValida) {
            System.out.print("Data de Nascimento (DD/MM/AAAA): ");
            String dataNascimentoInput = scanner.nextLine();
            try {
                novoAluno.setDataNascimento(dataNascimentoInput);
                dataNascimentoValida = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        boolean cursoValido = false;
        while (!cursoValido) {
            System.out.print("Curso: ");
            String cursoInput = scanner.nextLine();
            try {
                novoAluno.setCurso(cursoInput);
                cursoValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        boolean periodoValidoLoop = false;
        while (!periodoValidoLoop) {
            System.out.print("Período (ex: 1): ");
            if (scanner.hasNextInt()) {
                int periodoInput = scanner.nextInt();
                scanner.nextLine();
                try {
                    novoAluno.setPeriodo(periodoInput);
                    periodoValidoLoop = true;
                } catch (Validacao e) {
                    System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
                }
            } else {
                System.out.println("Entrada inválida para período. Por favor, insira um número.");
                scanner.nextLine();
            }
        }

        boolean emailValido = false;
        while (!emailValido) {
            System.out.print("Email: ");
            String emailInput = scanner.nextLine();
            try {
                novoAluno.setEmail(emailInput);
                emailValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente.");
            }
        }

        alunos.add(novoAluno);
        salvarDados();
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private String formatarCampo(String str, int width) {
        if (str == null) {
            str = "";
        }
        if (str.length() > width) {
            return str.substring(0, width - 3) + "...";
        }
        return String.format("%-" + width + "s", str);
    }

    private String formatarCampo(int valor, int width) {
        return formatarCampo(String.valueOf(valor), width);
    }

    private void listarAlunos() {
        System.out.println("\n=== Lista de Alunos Cadastrados ===");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado no sistema.");
            return;
        }

        int larguraNome = 25;
        int larguraMatricula = 15;
        int larguraCurso = 20;
        int larguraCpf = 15;
        int larguraEmail = 25;
        int larguraDataNasc = 15;
        int larguraPeriodo = 8;

        String formatoLinha = "| %-" + larguraNome + "s | %-" + larguraMatricula + "s | %-" + larguraCurso + "s | %-" + larguraCpf + "s | %-" + larguraEmail + "s | %-" + larguraDataNasc + "s | %" + larguraPeriodo + "s |";

        String cabecalho = String.format(formatoLinha,
                "Nome", "Matrícula", "Curso", "CPF", "Email", "Dt.Nasc.", "Período");

        StringBuilder linhaSeparadoraBuilder = new StringBuilder("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraNome + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraMatricula + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraCurso + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraCpf + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraEmail + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraDataNasc + 2)).append("+");
        linhaSeparadoraBuilder.append("-".repeat(larguraPeriodo + 2)).append("+");
        String linhaSeparadora = linhaSeparadoraBuilder.toString();

        System.out.println(linhaSeparadora);
        System.out.println(cabecalho);
        System.out.println(linhaSeparadora);

        for (Aluno aluno : alunos) {
            String linhaAluno = String.format(formatoLinha,
                    formatarCampo(aluno.getNome(), larguraNome),
                    formatarCampo(aluno.getMatricula(), larguraMatricula),
                    formatarCampo(aluno.getCurso(), larguraCurso),
                    formatarCampo(aluno.getCpf(), larguraCpf),
                    formatarCampo(aluno.getEmail(), larguraEmail),
                    formatarCampo(aluno.getDataNascimento(), larguraDataNasc),
                    formatarCampo(aluno.getPeriodo(), larguraPeriodo)
            );
            System.out.println(linhaAluno);
        }
        System.out.println(linhaSeparadora);
    }

    private Aluno buscarAlunoMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }
        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(matricula.trim())) {
                return aluno;
            }
        }
        return null;
    }

    private void buscarAluno() {
        System.out.println("\n=== Buscar Aluno por Matrícula ===");
        System.out.print("Digite a matrícula do aluno: ");
        String matricula = scanner.nextLine();
        Aluno alunoEncontrado = buscarAlunoMatricula(matricula);

        if (alunoEncontrado != null) {
            System.out.println("Aluno encontrado:");
            System.out.println(alunoEncontrado.toString());
        } else {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matricula);
        }
    }

    private void editarAluno() throws IOException {
        System.out.println("\n=== Editar Aluno ===");
        System.out.print("Digite a matrícula do aluno a ser editado: ");
        String matriculaOriginal = scanner.nextLine();

        Aluno alunoParaEditar = null;
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equalsIgnoreCase(matriculaOriginal)) {
                alunoParaEditar = alunos.get(i);
                break;
            }
        }

        if (alunoParaEditar == null) {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matriculaOriginal);
            return;
        }

        System.out.println("Dados atuais do Aluno:");
        System.out.println(alunoParaEditar.toString());
        System.out.println("Deixe o campo em branco e pressione Enter para não alterar a informação.");

        boolean nomeEditValido = false;
        while(!nomeEditValido) {
            System.out.print("Novo Nome (" + alunoParaEditar.getNome() + "): ");
            String inputNome = scanner.nextLine();
            if (inputNome.trim().isEmpty()) {
                nomeEditValido = true; break;
            }
            try {
                alunoParaEditar.setNome(inputNome);
                nomeEditValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean matriculaEditValida = false;
        while(!matriculaEditValida) {
            System.out.print("Nova Matrícula (" + alunoParaEditar.getMatricula() + "): ");
            String inputMatricula = scanner.nextLine();
            if (inputMatricula.trim().isEmpty()) {
                matriculaEditValida = true;
                break;
            }
            boolean matriculaExistenteEmOutroAluno = false;
            if (!inputMatricula.equalsIgnoreCase(alunoParaEditar.getMatricula())) {
                Aluno alunoComNovaMatricula = buscarAlunoMatricula(inputMatricula);
                if (alunoComNovaMatricula != null && alunoComNovaMatricula != alunoParaEditar) {
                    matriculaExistenteEmOutroAluno = true;
                }
            }

            if (matriculaExistenteEmOutroAluno) {
                System.out.println("Erro: Nova matrícula '" + inputMatricula + "' já existe para outro aluno. Tente outra ou deixe em branco.");
                continue;
            }
            try {
                alunoParaEditar.setMatricula(inputMatricula);
                matriculaEditValida = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean cursoEditValido = false;
        while(!cursoEditValido) {
            System.out.print("Novo Curso (" + alunoParaEditar.getCurso() + "): ");
            String inputCurso = scanner.nextLine();
            if (inputCurso.trim().isEmpty()) {
                cursoEditValido = true; break;
            }
            try {
                alunoParaEditar.setCurso(inputCurso);
                cursoEditValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean cpfEditValido = false;
        while(!cpfEditValido) {
            System.out.print("Novo CPF (" + alunoParaEditar.getCpf() + "): ");
            String inputCpf = scanner.nextLine();
            if (inputCpf.trim().isEmpty()) {
                cpfEditValido = true; break;
            }
            try {
                alunoParaEditar.setCpf(inputCpf);
                cpfEditValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean emailEditValido = false;
        while(!emailEditValido){
            System.out.print("Novo Email (" + alunoParaEditar.getEmail() + "): ");
            String inputEmail = scanner.nextLine();
            if (inputEmail.trim().isEmpty()){
                emailEditValido = true; break;
            }
            try {
                alunoParaEditar.setEmail(inputEmail);
                emailEditValido = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean dataNascEditValida = false;
        while(!dataNascEditValida) {
            System.out.print("Nova Data de Nascimento (" + alunoParaEditar.getDataNascimento() + "): ");
            String inputDataNasc = scanner.nextLine();
            if (inputDataNasc.trim().isEmpty()) {
                dataNascEditValida = true; break;
            }
            try {
                alunoParaEditar.setDataNascimento(inputDataNasc);
                dataNascEditValida = true;
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        boolean periodoEditValido = false;
        while(!periodoEditValido){
            System.out.print("Novo Período (" + alunoParaEditar.getPeriodo() + "): ");
            String periodoStr = scanner.nextLine();
            if (periodoStr.trim().isEmpty()){
                periodoEditValido = true; break;
            }
            try {
                int periodoNovo = Integer.parseInt(periodoStr);
                alunoParaEditar.setPeriodo(periodoNovo);
                periodoEditValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida para período. Por favor, insira um número ou deixe em branco.");
            } catch (Validacao e) {
                System.err.println("Erro: " + e.getMessage() + " Tente novamente ou deixe em branco.");
            }
        }

        salvarDados();
        System.out.println("Aluno editado com sucesso!");
    }

    private void excluirAluno() throws IOException {
        System.out.println("\n=== Excluir Aluno ===");
        System.out.print("Digite a matrícula do aluno a ser excluído: ");
        String matricula = scanner.nextLine();

        Aluno alunoParaExcluir = null;
        int indiceAluno = -1;
        for (int i = 0; i < alunos.size(); i++) {
            if (alunos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                alunoParaExcluir = alunos.get(i);
                indiceAluno = i;
                break;
            }
        }

        if (alunoParaExcluir == null) {
            System.out.println("Nenhum aluno encontrado com a matrícula: " + matricula);
            return;
        }

        System.out.println("Aluno encontrado:");
        System.out.println(alunoParaExcluir.toString());
        System.out.print("Tem certeza que deseja excluir este aluno? (s/n): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("s")) {
            alunos.remove(indiceAluno);
            salvarDados();
            System.out.println("Aluno excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

    private void salvarDados() throws IOException {
        try {
            AlunoCSV.salvarAlunos(this.alunos, arquivoAlunos);
            System.out.println("LOG: Dados salvos com sucesso em " + arquivoAlunos);
        } catch (IOException e) {
            System.err.println("ERRO AO SALVAR: Falha ao salvar dados no arquivo: " + e.getMessage());
            throw e;
        }
    }

    private void salvarManual() {
        try {
            salvarDados();
        } catch (IOException e) {
        }
    }

    private void carregarAlunos() {
        try {
            this.alunos = AlunoCSV.lerAlunos(arquivoAlunos);
            System.out.println("LOG: " + this.alunos.size() + " aluno(s) carregado(s) de " + arquivoAlunos);
        } catch (IOException e) {
            System.err.println("ERRO AO CARREGAR: Falha ao carregar dados do arquivo: " + e.getMessage() + ". Iniciando com lista vazia.");
            this.alunos = new ArrayList<>();
        } catch (Validacao e) {
            System.err.println("ERRO DE VALIDAÇÃO AO CARREGAR: " + e.getMessage() + ". Alguns dados podem não ter sido carregados ou o arquivo pode estar corrompido.");
            this.alunos = new ArrayList<>();
        }
    }

    private void carregarManual() {
        System.out.println("Tentando recarregar alunos do arquivo manualmente...");
        carregarAlunos();
    }
}