public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private String cpf;
    private String email;
    private String dataNascimento;
    private int periodo;

    public Aluno() {
    }

    public Aluno(String nome, String matricula, String curso, String cpf, String email, String dataNascimento, int periodo) throws Validacao {
        this.setNome(nome);
        this.setMatricula(matricula);
        this.setCurso(curso);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setDataNascimento(dataNascimento);
        this.setPeriodo(periodo);
    }

    public String getNome() { return nome; }
    public String getMatricula() { return matricula; }
    public String getCurso() { return curso; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getDataNascimento() { return dataNascimento; }
    public int getPeriodo() { return periodo; }

    public void setNome(String nome) throws Validacao {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Validacao("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setMatricula(String matricula) throws Validacao {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new Validacao("Matrícula não pode ser vazia.");
        }
        this.matricula = matricula.trim();
    }

    public void setCurso(String curso) throws Validacao {
        if (curso == null || curso.trim().isEmpty()) {
            throw new Validacao("Curso não pode ser vazio.");
        }
        this.curso = curso.trim();
    }

    public void setCpf(String cpf) throws Validacao {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new Validacao("CPF não pode ser vazio.");
        }
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() == 11) {
            this.cpf = cpf.trim();
        } else if (cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            this.cpf = cpf.trim();
        } else {
            throw new Validacao("CPF inválido. Deve estar no formato XXX.XXX.XXX-XX ou XXXXXXXXXXX (11 dígitos).");
        }
    }

    public void setEmail(String email) throws Validacao {
        if (email == null || email.trim().isEmpty()) {
            throw new Validacao("Email não pode ser vazio.");
        }
        if (!email.contains("@")) {
            throw new Validacao("Email inválido (falta o caractere '@').");
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            throw new Validacao("Formato de email inválido.");
        }
        this.email = email.trim();
    }

    public void setDataNascimento(String dataNascimento) throws Validacao {
        if (dataNascimento == null || dataNascimento.trim().isEmpty()) {
            throw new Validacao("Data de nascimento não pode ser vazia.");
        }
        String dataRegex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/((19|20)\\d\\d)$";
        if (!dataNascimento.matches(dataRegex)) {
            throw new Validacao("Data de nascimento inválida. Deve estar no formato DD/MM/AAAA e ser uma data plausível (ex: 01/01/2000).");
        }
        this.dataNascimento = dataNascimento.trim();
    }

    public void setPeriodo(int periodo) throws Validacao {
        if (periodo < 1) {
            throw new Validacao("Período inválido. Deve ser um número positivo (mínimo 1).");
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
        return String.join(separador,
                nome != null ? nome : "",
                matricula != null ? matricula : "",
                curso != null ? curso : "",
                cpf != null ? cpf : "",
                email != null ? email : "",
                dataNascimento != null ? dataNascimento : "",
                String.valueOf(periodo)
        );
    }

    public static Aluno fromCsvString(String csvLine) throws Validacao {
        String separador = ";";
        String[] campos = csvLine.split(separador, -1);
        if (campos.length < 7) {
            throw new Validacao ("Dados insuficientes para criar um Aluno a partir da linha CSV: '" + csvLine + "'. Esperado 7 campos, mas encontrado " + campos.length + ".");
        }
        String[] nomesCamposParaErro = {"Nome", "Matrícula", "Curso", "CPF", "Email", "Data de Nascimento", "Período"};
        for (int i = 0; i < nomesCamposParaErro.length; i++) {
            if (campos[i] == null || campos[i].trim().isEmpty()) {
                throw new Validacao (nomesCamposParaErro[i] + " não pode ser vazio na linha CSV: '" + csvLine + "'.");
            }
        }
        String nome = campos[0].trim();
        String matricula = campos[1].trim();
        String curso = campos[2].trim();
        String cpf = campos[3].trim();
        String email = campos[4].trim();
        String dataNascimento = campos[5].trim();
        int periodo;
        try {
            periodo = Integer.parseInt(campos[6].trim());
        } catch (NumberFormatException e) {
            throw new Validacao("Período inválido ('"+campos[6]+"') na linha CSV: '" + csvLine + "'. Deve ser um número.");
        }
        return new Aluno(nome, matricula, curso, cpf, email, dataNascimento, periodo);
    }
}