package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import org.bitbucket.marcondesads.patterngames.api.modelo.AlocacaoException;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;

/**
 * Classe de principal do sistema, contem todos os cliente, jogos e alocações realizadas
 * e é responsavel pela manutenção destes.
 * @author José Marcondes do Nascimento Junior
 */
public class LocacaoManager {
    
        private Collection<Cliente> clientes;
        private Collection<Jogo> jogos;
        private Collection<Locacao> locacoes;
        
        public LocacaoManager(){
            this.clientes = new ArrayList<>();
            this.jogos = new ArrayList<>();
            this.locacoes = new ArrayList<>();
        }
        
        public void cadastrarCliente(Cliente cli) throws ClienteInvalido{
            if(cli == null)
                throw new ClienteInvalido("O cliente não pode ser nulo");
            else if(cli.getLogin().equals("") || cli.getLogin() == null)
                throw new ClienteInvalido("O cliente deve possuir login");
            else if(cli.getSenha().equals("") || cli.getSenha()== null)
                throw new ClienteInvalido("O cliente deve possuir uma senha");
            else if(cli.getNome().equals("") || cli.getNome()== null)
                throw new ClienteInvalido("O cliente deve possuir um nome");
            else if(cli.getCpf().equals("") || cli.getCpf()== null)
                throw new ClienteInvalido("O cliente deve possuir um cpf válido");
            else if(cli.getEmail().equals("") || cli.getEmail()== null)
                throw new ClienteInvalido("O cliente deve possuir um cpf válido");
            try{
                new CPFValidator().assertValid(cli.getCpf());
            }catch(InvalidStateException e){
                throw new ClienteInvalido("O cpf do usuário indicado não é válido");
            }
            this.clientes.add(cli);
        }
        
        public void excluirCliente(final String cpf){
            this.clientes.removeIf(new Predicate<Cliente>() {

                @Override
                public boolean test(Cliente t) {
                    return t.getCpf().equals(cpf);
                }
            });
        }
        
        public void cadastrarJogo(Jogo jogo){
            this.jogos.add(jogo);
        }
        
        public void excluirJogo(final String nome){
            this.jogos.removeIf(new Predicate<Jogo>() {

                @Override
                public boolean test(Jogo t) {
                    return t.getNome().equals(nome);
                }
            });
        }
        
        public Locacao realizarLocacao(String login, String senha , Jogo jogo) throws AlocacaoException{
            Cliente find = null;
            for(Cliente cli: clientes){
                if(cli.getLogin().equals(login) && cli.getSenha().equals(senha)){
                    find = cli;
                    break;
                }
            }
            Jogo jogoFind = null;
            for(Jogo jg: jogos){
                if(jg.getNome().equals(jogo.getNome())){
                    jogoFind = jg;
                    break;
                }                    
            }
            if(find == null)
                throw new AlocacaoException("Login ou senha inválidos");
            if(jogoFind == null)
                throw new AlocacaoException("Login ou senha inválidos");
            
            Locacao loc = LocacaoFactory.getNewLocacao(find,jogoFind);
            this.locacoes.add(loc);
            return loc;
        }
        
        public double realizarDesalocacao(int id) throws AlocacaoException{
            Locacao loc = this.localizaLocacao(id);
            loc.getJogo().desalocar();
            locacoes.remove(loc);
            return loc.calcularMulta();
        }
        
        public Locacao localizaLocacao(int id){
            Locacao localizada = null;
            for(Locacao loc: locacoes){
                if(loc.getId() == id){
                    localizada = loc;
                    break;
                }
            }
            return localizada;
        }
        
        public void adicionarObservador(Cliente cli, Jogo jogo){
            jogo.addObserver(cli);
        }

    public Collection<Cliente> getClientes() {
        return Collections.unmodifiableCollection(clientes);
    }

    public void setClientes(Collection<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Collection<Jogo> getJogos() {
        return Collections.unmodifiableCollection(jogos);
    }

    public void setJogos(Collection<Jogo> jogos) {
        this.jogos = jogos;
    }

    public Collection<Locacao> getLocacoes() {
        return Collections.unmodifiableCollection(locacoes);
    }

    public void setLocacoes(Collection<Locacao> locacoes) {
        this.locacoes = locacoes;
    }
        
        
}
