package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import org.bitbucket.marcondesads.patterngames.api.modelo.AlocacaoException;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.EstadoJogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.EstadoJogoEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.LocStrategyEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author José Marcondes do Nascimento Junior
 */
public class LocacaoManagerTest {

    private Cliente cli;
    private Jogo jg;
    private LocStrategyEnum tipo;
    
    public LocacaoManagerTest() {
        cli = new Cliente("login", "senha", "pattensgames","117.376.474-78", "pattengames3@gmail.com");
        jg = new Jogo("Devil May Cry");
        if(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY)
            tipo = LocStrategyEnum.LOCACAO_ESPECIAL;
        else
            tipo = LocStrategyEnum.LOCACAO_NORMAL;
    }
    
    @Before
    public void setUp() {
        cli = new Cliente("login", "senha", "pattensgames","117.376.474-78", "pattengames3@gmail.com");
        jg = new Jogo("Devil May Cry");
        if(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY)
            tipo = LocStrategyEnum.LOCACAO_ESPECIAL;
        else
            tipo = LocStrategyEnum.LOCACAO_NORMAL;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of cadastrarCliente method, of class LocacaoManager.
     */
    @Test
    public void testCadastrarCliente() throws ClienteInvalido {
        System.out.println("cadastrarCliente");
        Cliente cli = this.cli;
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarCliente(cli);
        assertTrue(instance.getClientes().contains(cli));
        try{
            instance.cadastrarCliente(new Cliente("", "senha", "pattensgames","117.376.474-78", "pattengames3@gmail.com"));
        }catch(ClienteInvalido e){
            try{
                instance.cadastrarCliente(new Cliente("login", "", "pattensgames","117.376.474-78", "pattengames3@gmail.com"));
            }catch(ClienteInvalido e2){
                try{
                    instance.cadastrarCliente(new Cliente("login", "senha", "","117.376.474-78", "pattengames3@gmail.com"));
                }catch(ClienteInvalido e3){
                    try{
                        instance.cadastrarCliente(new Cliente("login", "senha", "pattensgames","", "pattengames3@gmail.com"));
                    }catch(ClienteInvalido e4){
                        try{
                            instance.cadastrarCliente(new Cliente("login", "senha", "pattensgames","123.123.123-12", "pattengames3@gmail.com"));
                        }catch(ClienteInvalido e5){
                            try{
                                instance.cadastrarCliente(new Cliente("login", "senha", "pattensgames","117.376.474-78", ""));
                            }catch(ClienteInvalido ex6){
                                return;
                            }
                        }
                    }
                }
            }
        }
        fail();
    }

    /**
     * Test of excluirCliente method, of class LocacaoManager.
     */
    @Test
    public void testExcluirCliente() throws ClienteInvalido {
        System.out.println("excluirCliente");
        String cpf = "117.376.474-78";
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarCliente(cli);
        instance.excluirCliente(cpf);
        assertFalse(instance.getClientes().contains(cli));
    }

    /**
     * Test of cadastrarJogo method, of class LocacaoManager.
     */
    @Test
    public void testCadastrarJogo() {
        System.out.println("cadastrarJogo");
        Jogo jogo = this.jg;
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarJogo(jogo);
        assertTrue(instance.getJogos().contains(jg));
    }

    /**
     * Test of excluirJogo method, of class LocacaoManager.
     */
    @Test
    public void testExcluirJogo() {
        System.out.println("excluirJogo");
        String nome = "Devil May Cry";
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarJogo(jg);
        instance.excluirJogo(nome);
        assertFalse(instance.getJogos().contains(jg));
    }

    /**
     * Test of realizarLocacao method, of class LocacaoManager.
     */
    @Test
    public void testRealizarLocacao() throws Exception {
        System.out.println("realizarLocacao");
        Cliente cli = this.cli;
        Jogo jogo = this.jg;
        LocacaoManager instance = new LocacaoManager();
        
        try{
            instance.realizarLocacao("login","senha", jogo);
        }catch(AlocacaoException e){
            instance.cadastrarCliente(cli);
            instance.cadastrarJogo(jogo);
            Locacao loc = instance.realizarLocacao("login", "senha", jogo);
            assertTrue(instance.getLocacoes().contains(loc));
            assertEquals(jogo, loc.getJogo());
            assertEquals(cli,loc.getCliente());
            assertEquals(tipo, loc.getTipo());
            assertEquals(EstadoJogoEnum.ALOCADO, jogo.getEstado());
            return;
        }
        fail();
    }

    /**
     * Test of realizarDesalocacao method, of class LocacaoManager.
     */
    @Test
    public void testRealizarDesalocacao() throws Exception {
        System.out.println("realizarDesalocacao");
        int id;
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarCliente(cli);
        instance.cadastrarJogo(jg);
        Locacao loc = instance.realizarLocacao(cli.getLogin(), cli.getSenha(), jg);
        instance.realizarDesalocacao(loc.getId());
        assertFalse(instance.getLocacoes().contains(loc));
    }
    
    @Test
    public void testDesalocacaoComObservador() throws AlocacaoException, ClienteInvalido{
        System.out.println("realizarDesalocacaoComObservador");
        LocacaoManager instance = new LocacaoManager();
        instance.cadastrarCliente(cli);
        instance.cadastrarJogo(jg);
        Locacao loc = instance.realizarLocacao(cli.getLogin(), cli.getSenha(), jg);
        instance.adicionarObservador(cli, jg);
        instance.realizarDesalocacao(loc.getId());
    }
}
