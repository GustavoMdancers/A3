public class Main {
    public static void main(String[] args) {
        // Criar um novo aluno
        Aluno aluno1 = new Aluno("João Silva", "123456", "Engenharia");
        // Adicionar o aluno ao arquivo CSV
        AlunoCSV.adicionarAluno(aluno1);

    }
}