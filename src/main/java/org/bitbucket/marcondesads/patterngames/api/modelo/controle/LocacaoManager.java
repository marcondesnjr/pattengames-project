package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.AccountException;
import org.bitbucket.marcondesads.patterngames.api.modelo.AlocacaoException;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOCliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOClienteBD;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOJogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOJogoBD;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOLocacao;
import org.bitbucket.marcondesads.patterngames.api.modelo.dao.DAOLocacaoBD;

/**
 * Classe de principal do sistema, contem todos os cliente, jogos e alocações realizadas
 * e é responsavel pela manutenção destes.
 * @author José Marcondes do Nascimento Junior
 */
public class LocacaoManager {
    
        private static Collection<Cliente> clientes;
        private static Collection<Jogo> jogos;
        private static Collection<Locacao> locacoes;
        
        static{
            try {
                clientes = new DAOClienteBD().listar();
                jogos = new DAOJogoBD().listar();
                locacoes = new DAOLocacaoBD().listar();
            } catch (SQLException ex) {
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        public static void cadastrarCliente(Cliente cli) throws ClienteInvalido, PersistenceException{
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
                throw new ClienteInvalido("O cliente deve possuir um cpf válido");
            }
            
            try{
                DAOCliente dao = new DAOClienteBD();
                dao.guardar(cli);
                clientes.add(cli);
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        }
        
        public static void excluirCliente(final String login) throws PersistenceException{
           
            try{
                DAOCliente dao = new DAOClienteBD();
                dao.excluir(login);
            
                clientes.removeIf(new Predicate<Cliente>() {

                    @Override
                    public boolean test(Cliente t) {
                        return t.getLogin().equals(login);
                    }
                });
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        }
        
        public static void cadastrarJogo(Jogo jogo) throws PersistenceException{
            try{
                DAOJogo dao = new DAOJogoBD();
                dao.guardar(jogo);
                jogos.add(jogo);
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        }
        
        public static void excluirJogo(final int id) throws PersistenceException{
            try{
                DAOJogo dao = new DAOJogoBD();
                dao.excluir(id);
                jogos.removeIf(new Predicate<Jogo>() {

                    @Override
                    public boolean test(Jogo t) {
                        return t.getId() == id;
                    }
                });
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        }
        
        public static Locacao realizarLocacao(String login, String senha , Jogo jogo) throws AlocacaoException, PersistenceException, AccountException{
            Cliente find = localizaCliente(login, senha);
            if(find == null)
                throw new AccountException("login ou senhas incorretos");
            Jogo jogoFind = null;
            for(Jogo jg: jogos){
                if(jg.getId() == jogo.getId()){
                    jogoFind = jg;
                    break;
                }                    
            }
            if(jogoFind == null)
                throw new AlocacaoException("Jogo inválido");
            
            Locacao loc = LocacaoFactory.getNewLocacao(find,jogoFind);
            try{ 
                DAOLocacao dao = new DAOLocacaoBD();
                dao.guardar(loc);
                locacoes.add(loc);
                jogo.alocar();
                new DAOJogoBD().atualizar(jogo);
                return loc;
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        
        }
        
        public static double realizarDesalocacao(int id) throws AlocacaoException, PersistenceException{
            Locacao loc = localizaLocacao(id);
            
            try{
                DAOLocacao dao = new DAOLocacaoBD();
                dao.excluir(id);            
                locacoes.remove(loc);
                localizaJogo(loc.getJogo().getId()).desalocar();
                DAOJogo daoJG = new DAOJogoBD();
                daoJG.atualizar(loc.getJogo());
                return loc.calcularMulta();
            }catch(SQLException e){
                Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
                throw PersistenceException.defautMessage();
            }
        }
        
    public static Locacao localizaLocacao(int id){
        Locacao localizada = null;
        for(Locacao loc: locacoes){
            if(loc.getId() == id){
                localizada = loc;
                break;
            }
        }
        return localizada;
    }
        
    public static void adicionarObservador(String login, String senha, Jogo jogo) throws PersistenceException, AccountException{
        Cliente find = localizaCliente(login, senha);
        if(find == null)
            throw new AccountException("login ou senhas incorretos");
        try{
            jogo.addObserver(find);
            DAOJogo dao = new DAOJogoBD();
            dao.atualizar(jogo);
        }catch(SQLException e){
            Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
            throw PersistenceException.defautMessage();
        }
    }
    
    public static void removerObservador(String login, String senha, Jogo jogo) throws PersistenceException, AccountException{
        Cliente find = localizaCliente(login, senha);
        if(find == null)
            throw new AccountException("login ou senhas incorretos");
        try{
            jogo.remObserver(find);
            DAOJogo dao = new DAOJogoBD();
            dao.atualizar(jogo);
        }catch(SQLException e){
            Logger.getLogger(LocacaoManager.class.getName()).log(Level.SEVERE, null,e);
            throw PersistenceException.defautMessage();
        }
    }
    
    public static Cliente localizaCliente(String login, String senha){
        for(Cliente cli: clientes){
                if(cli.getLogin().equals(login) && cli.getSenha().equals(senha)){
                    return cli;
                }
            }
        return null;
    }

    public static Collection<Cliente> getClientes() {
        return Collections.unmodifiableCollection(clientes);
    }

    public static void setClientes(Collection<Cliente> cli) {
        clientes = cli;
    }

    public static Collection<Jogo> getJogos() {
        return Collections.unmodifiableCollection(jogos);
    }

    public static void setJogos(Collection<Jogo> jog) {
        jogos = jog;
    }

    public static Collection<Locacao> getLocacoes() {
        return Collections.unmodifiableCollection(locacoes);
    }

    public static void setLocacoes(Collection<Locacao> loc) {
        locacoes = loc;
    }

    public static Jogo localizaJogo(int id) {
        for(Jogo jg: jogos){
                if(jg.getId() == id){
                    return jg;
                }
            }
        return null;
    }
        
        
}
