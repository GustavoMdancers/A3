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
    public void setNome(String nome) throws Validacao {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Validacao("Nome não pode ser vazio.");
        } else {
            this.nome = nome;
        }
    }

    public String getMatricula() {return matricula;}
    public void setMatricula(String matricula) throws Validacao {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new Validacao("Matrícula não pode ser vazia.");
        } else {
            this.matricula = matricula;
        }
    }

    public String getCurso() {return curso;}
    public void setCurso(String curso) throws Validacao {
        if (curso == null || curso.trim().isEmpty()) {
            throw new Validacao("Curso não pode ser vazio.");
        } else {
            this.curso = curso;
        }
    }

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) throws Validacao {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Validacao ("CPF não pode ser vazio.");
        }
        if (!cpf.matches("\\d{3}")) {
            if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                throw new Validacao("CPF inválido. Deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX.");
            }
        }
        this.cpf = cpf;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) throws Validacao {
        if (email == null || email.trim().isEmpty()) {
            throw new Validacao ("Email não pode ser vazio.");
        }
        if (!email.contains("@")) {
            throw new Validacao("Email inválido.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new Validacao("Email inválido.");
        }
    }

    public String getDataNascimento() {return dataNascimento;}
    public void setDataNascimento(String data) throws Validacao {
        if (data == null || data.trim().isEmpty()) {
            throw new Validacao("Data de nascimento não pode ser vazia.");
        }
        String dataRegex = "\\d{2}/\\d{2}/\\d{4}";
        if (!data.matches(dataRegex)) {
            throw new Validacao("Data de nascimento inválida. Deve estar no formato DD/MM/AAAA.");
        }
        this.dataNascimento = data;
    }

    public int getPeriodo() {return periodo;}
    public void setPeriodo(int periodo) throws Validacao {
        if (periodo < 1 || periodo > 10) {
            throw new Validacao("Período inválido. Deve ser um número entre 1 e 10.");
        }
        this.periodo = periodo;
    }

}
