import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AlunoCSV {

    private static final String separador = ";";
    private static final String cabecalho = "Nome" + separador +
            "Matrícula" + separador +
            "Curso" + separador +
            "CPF" + separador +
            "Email" + separador +
            "Data de Nascimento" + separador +
            "Período";

    public static void salvarAlunos(List<Aluno> aluno, String caminhoArquivo) throws IOException {
        Path pathArquivo = Paths.get(caminhoArquivo);
        Path diretorioPai = pathArquivo.getParent();

        if(diretorioPai != null && !Files.exists(diretorioPai)) {
            Files.createDirectories(diretorioPai);
            System.out.println("Diretório criado: " + diretorioPai.toAbsolutePath());
        }

        try (BufferedWriter writer = new BufferedWriter (new OutputStreamWriter (new FileOutputStream(caminhoArquivo), StandardCharsets.UTF_8))) {
            writer.write (cabecalho);
            writer.newLine ();

            if (aluno != null) {
                for (Aluno aluno1 : aluno) {
                    writer.write(aluno1.toCsvString());
                    writer.newLine();
                }
            }
        }
    }

    public static void adicionarAluno(Aluno aluno, String caminhoArquivo) throws IOException {
        if (aluno == null) {
            System.err.println("Aluno não pode ser nulo.");
            return;
        }

        Path pathArquivo = Paths.get(caminhoArquivo);
        Path diretorioPai = pathArquivo.getParent();
        File arquivo = new File(caminhoArquivo);

        if (diretorioPai != null && !Files.exists(diretorioPai)) {
            Files.createDirectories(diretorioPai);
            System.out.println("Diretório criado: " + diretorioPai.toAbsolutePath());
        }

        boolean escreverCabecalho = !arquivo.exists() || arquivo.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(arquivo, true), StandardCharsets.UTF_8))) {
            if (escreverCabecalho) {
                writer.write(cabecalho);
                writer.newLine ();
            }
            writer.write(aluno.toString());
            writer.newLine();
        }
    }

    public static List<Aluno> lerAlunos(String caminhoArquivo) throws IOException, Validacao {
        List<Aluno> alunos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            System.out.println("Arquivo '" + caminhoArquivo + "' não encontrado. Retornando lista vazia.");
            return alunos;
        }
        if (arquivo.length() == 0) {
            System.out.println("Arquivo '" + caminhoArquivo + "' está vazio. Retornando lista vazia.");
            return alunos;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha = reader.readLine();

            if (linha == null) {
                System.out.println("Arquivo CSV '" + caminhoArquivo + "' está vazio ou não contém dados válidos. Retornando lista vazia.");
                return alunos;
            }
            if (!linha.trim().equalsIgnoreCase(cabecalho.trim())) {
                System.err.println("AVISO: O arquivo CSV '" + caminhoArquivo + "' não contém o cabeçalho esperado. ('" + cabecalho + "'))");
                if (!linha.trim().isEmpty()) {
                    System.err.println("Tentando ler os dados do arquivo sem o cabeçalho...");
                    try {
                        alunos.add(Aluno.fromCsvString(linha));
                    } catch (Validacao e) {
                        System.err.println("Erro ao processar a primeira linha (sem cabeçalho ou cabeçalho inválido) como dados: '" + linha + "'. Erro: " + e.getMessage() + ". Linha ignorada.");

                    }
                }
            }

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    try {
                        alunos.add(Aluno.fromCsvString(linha));
                    } catch (Validacao e) {
                        System.err.println("Erro ao processar a linha vazia: '" + linha + "'. Erro: " + e.getMessage() + ". Linha ignorada.");
                    }
                }
            }
        }
        return alunos;
    }
}