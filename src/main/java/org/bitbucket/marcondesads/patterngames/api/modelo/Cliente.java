package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Classe que representa um cliente, este cliente pode observar jogos específicos e será notificado
 * caso algum deles se torne disponivel para alocação.
 * @author José Marcondes do Nascimento Junior
 */
public class Cliente implements Observer{
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String senha;

    public Cliente(String login, String senha, String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    
    
    @Override
    public void doAction(Jogo obs) {
       String msg = new StringBuilder("Caro ").append(this.nome).append(" o jogo ").append(obs.getNome())
               .append(" está disponnível para locação! Corra agora para a Pattern Games para garantir sua jogatina !").toString();
       String titulo = new StringBuilder("O jogo ").append(obs.getNome()).append(" está disponivel!!!").toString();
       EmailSender.sender(this, msg, titulo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
}
