public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private String cpf;
    private String email;
    private String dataNascimento;
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

    @Override
    public String toString() {
        return "Aluno{" +
                "nome='" + nome + '\'' +
                ", matricula='" + matricula + '\'' +
                ", curso='" + curso + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", periodo=" + periodo +
                '}';
    }

    public String toCsvString() {
        String separador = ";";
        return nome + separador +
                matricula + separador +
                curso + separador +
                cpf + separador +
                email + separador +
                dataNascimento + separador +
                periodo;
    }

    public static Aluno fromCsvString(String csvLine) throws Validacao {
        String separador = ";";
        String[] campos = csvLine.split(separador, -1);

        if (campos.length < 7) {
            throw new Validacao ("Dados insuficientes para criar um Aluno: '" + csvLine + "'. Esperado 7 campos, mas encontrado " + campos.length + ".");
        }

        for (int i = 0; i < campos.length; i++) {
            if (i != 5 && (campos[1] == null || campos[i].trim().isEmpty())) {
                String nomeCampo = "";
                switch(i) {
                    case 0: nomeCampo = "Nome"; break;
                    case 1: nomeCampo = "Matrícula"; break;
                    case 2: nomeCampo = "Curso"; break;
                    case 3: nomeCampo = "CPF"; break;
                    case 4: nomeCampo = "Email"; break;
                    case 5: nomeCampo = "Data de Nascimento"; break;
                    case 6: nomeCampo = "Período"; break;
            }
            throw new Validacao (nomeCampo + " não pode ser vazio na linha CSV: '" + csvLine + "'.");
        }
    }
    String nome = campos[0];
    String matricula = campos[1];
    String curso = campos[2];
    String cpf = campos[3];
    String email = campos[4].trim();
    String dataNascimento = campos[5];
    int periodo;

    try {
        if (campos[6].trim().isEmpty()) {
            throw new Validacao("Período não pode ser vazio na linha CSV: '" + csvLine + "'.");
        }
        periodo = Integer.parseInt(campos[6].trim());
    } catch (NumberFormatException e) {
        throw new Validacao("Período inválido na linha CSV: '" + csvLine + "'.");
    }
    return new Aluno(nome, matricula, curso, cpf, email, dataNascimento, periodo);
    }
}
