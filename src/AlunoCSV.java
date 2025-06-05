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

    public static void salvarAlunos(List<Aluno> alunos, String caminhoArquivo) throws IOException {
        Path pathArquivo = Paths.get(caminhoArquivo);
        Path diretorioPai = pathArquivo.getParent();

        if (diretorioPai != null && !Files.exists(diretorioPai)) {
            Files.createDirectories(diretorioPai);
            System.out.println("LOG: Diretório criado: " + diretorioPai.toAbsolutePath());
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminhoArquivo, false), StandardCharsets.UTF_8))) {
            writer.write(cabecalho);
            writer.newLine();

            if (alunos != null) {
                for (Aluno aluno : alunos) {
                    if (aluno != null) {
                        writer.write(aluno.toCsvString());
                        writer.newLine();
                    }
                }
            }
        }
    }

    public static void adicionarAluno(Aluno aluno, String caminhoArquivo) throws IOException {
        if (aluno == null) {
            System.err.println("LOG: Tentativa de adicionar aluno nulo ao CSV. Operação ignorada.");
            return;
        }

        Path pathArquivo = Paths.get(caminhoArquivo);
        Path diretorioPai = pathArquivo.getParent();
        File arquivo = new File(caminhoArquivo);

        if (diretorioPai != null && !Files.exists(diretorioPai)) {
            Files.createDirectories(diretorioPai);
            System.out.println("LOG: Diretório criado: " + diretorioPai.toAbsolutePath());
        }

        boolean escreverCabecalho = !arquivo.exists() || arquivo.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo, true), StandardCharsets.UTF_8))) {
            if (escreverCabecalho) {
                writer.write(cabecalho);
                writer.newLine();
            }
            writer.write(aluno.toCsvString());
            writer.newLine();
        }
    }

    public static List<Aluno> lerAlunos(String caminhoArquivo) throws IOException, Validacao {
        List<Aluno> alunos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            System.out.println("LOG: Arquivo '" + caminhoArquivo + "' não encontrado. Retornando lista vazia.");
            return alunos;
        }
        if (arquivo.length() == 0) {
            System.out.println("LOG: Arquivo '" + caminhoArquivo + "' está vazio. Retornando lista vazia.");
            return alunos;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            String linha = reader.readLine();

            if (linha == null) {
                System.out.println("LOG: Arquivo CSV '" + caminhoArquivo + "' está vazio após leitura inicial. Retornando lista vazia.");
                return alunos;
            }

            if (!linha.trim().equalsIgnoreCase(cabecalho.trim())) {
                System.err.println("AVISO: O cabeçalho do arquivo CSV ('" + linha + "') não corresponde ao esperado ('" + cabecalho + "').");
                if (!linha.trim().isEmpty()) {
                    System.err.println("Tentando processar a primeira linha como dados do aluno...");
                    try {
                        alunos.add(Aluno.fromCsvString(linha));
                    } catch (Validacao e) {
                        System.err.println("Erro ao processar a primeira linha (que não era o cabeçalho esperado) como dados: '" + linha + "'. Erro: " + e.getMessage() + ". Linha ignorada.");
                    }
                }
            }

            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    try {
                        alunos.add(Aluno.fromCsvString(linha));
                    } catch (Validacao e) {
                        System.err.println("Erro ao processar linha do CSV: '" + linha + "'. Erro: " + e.getMessage() + ". Linha ignorada.");
                    }
                }
            }
        }
        return alunos;
    }
}