import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AlunoCSV {
    //Caminho do arquivo CSV
    private static String nomeArquivo = "./BancoDeDados/alunosCSV.csv";

    public static void adicionarAluno(Aluno aluno) {
        //Lógica para adicionar um aluno ao arquivo CSV
        try {
            boolean arquivoExiste = new File(nomeArquivo).exists();

            OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream(nomeArquivo, true), StandardCharsets.ISO_8859_1);
            if (!arquivoExiste) {
                //Se o arquivo não existir, escrever o cabeçalho
                escritor.write("Nome,Matricula,Curso\n");
            }
            //Escrever os dados do aluno no arquivo CSV
            escritor.write(aluno.getNome() + ";" + aluno.getMatricula() + ";" + aluno.getCurso() + ";" + aluno.getCpf() + ";" + aluno.getEmail() + ";" + aluno.getDataNascimento() + ";" + aluno.getPeriodo() + "\n");
            // Garantir que os dados sejam gravados no arquivo
            escritor.flush();
            // Fechar o escritor
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            //Verificar se o arquivo existe
            boolean arquivoExiste = new File(nomeArquivo).exists();

            //Iniciar escritor para editar o arquivo CSV
            FileWriter escritor = new FileWriter(nomeArquivo, StandardCharsets.ISO_8859_1, true);
            if (!arquivoExiste) {
                //Se o arquivo não existir, escrever o cabeçalho
                escritor.write("Nome,Matricula,Curso\n");
            }

            //Escrever os dados do aluno no arquivo CSV
            escritor.write(aluno.getNome() + "," + aluno.getMatricula() + "," + aluno.getCurso() + "\n");
            // Garantir que os dados sejam gravados no arquivo
            escritor.flush();
            // Fechar o escritor
            escritor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    // Metodo para ler os alunos do arquivo CSV
    public static ArrayList<Aluno> lerAlunos() {
        ArrayList<Aluno> alunos = new ArrayList<>();

        try {

            //Iniciar leitor para ler o arquivo CSV
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
            String linha;
            boolean primeiraLinha = true;

            while ((linha = leitor.readLine()) != null) {
                if (primeiraLinha) {
                    // Ignorar a primeira linha (cabeçalho)
                    primeiraLinha = false;
                    continue;
                }
                //Dividir a linha em partes usando vírgula como delimitador
                String[] partes = linha.split(";");

                String nome = partes[0];
                String matricula = partes[1];
                String curso = partes[2];
                String cpf = partes[3];
                String email = partes[4];
                String dataNascimento = partes[5];
                int periodo = Integer.parseInt(partes[6]);

                //Criar um novo objeto Aluno e adicioná-lo à lista
                Aluno aluno = new Aluno(nome, matricula, curso, cpf, email, dataNascimento, periodo);
                alunos.add(aluno);
            }
            //Fechar o leitor
            leitor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return alunos;
    }
}