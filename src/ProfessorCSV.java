import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProfessorCSV {
    //Caminho do arquivo CSV
    private static String nomeArquivo2 = "./BancoDeDados/professoresCSV.csv";

    public static void adicionarProfessor(Professor professor) {
        //Lógica para adicionar um aluno ao arquivo CSV
        try {
            boolean arquivoExiste = new File(nomeArquivo2).exists();

            OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream(nomeArquivo2, true), StandardCharsets.ISO_8859_1);
            if (!arquivoExiste) {
                //Se o arquivo não existir, escrever o cabeçalho
                escritor.write("Nome,Matricula,Curso\n");
            }
            //Escrever os dados do aluno no arquivo CSV
            escritor.write(professor.getNome() + ";" + professor.getMatricula() + ";" + professor.getCurso() + "\n");
            // Garantir que os dados sejam gravados no arquivo
            escritor.flush();
            // Fechar o escritor
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo para ler os professores do arquivo CSV
    public static ArrayList<Professor> lerProfessores() {
        ArrayList<Professor> professores = new ArrayList<>();

        try {

            //Iniciar leitor para ler o arquivo CSV
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo2));
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

                //Criar um novo objeto Aluno e adicioná-lo à lista
                Professor professor = new Professor(nome, matricula, curso);
                professores.add(professor);
            }
            //Fechar o leitor
            leitor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return professores;
    }
}