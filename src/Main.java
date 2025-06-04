import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private List<Aluno> alunos;
    private Scanner scanner;
    private static final String arquivoAlunos = "alunosCSV.csv";

    public GestaoAlunos() {
        this.alunos = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main gestaoAlunos = new Main();
        Main.carregarAlunos();

    }

}