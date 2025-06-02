public class Aluno {
    private String nome, matricula, curso, cpf, email, dataNascimento;
    private int periodo;

    public Aluno(String nome, String matricula, String curso, String cpf, String email, String dataNascimento, int periodo) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.periodo = periodo;
    }

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getMatricula() {return matricula;}
    public void setMatricula(String matricula) {this.matricula = matricula;}

    public String getCurso() {return curso;}
    public void setCurso(String curso) {this.curso = curso;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getDataNascimento() {return dataNascimento;}
    public void setDataNascimento(String data) {this.dataNascimento = data;}

    public int getPeriodo() {return periodo;}
    public void setPeriodo(int periodo) {this.periodo = periodo;}

}
