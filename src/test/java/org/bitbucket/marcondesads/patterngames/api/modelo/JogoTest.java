/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitbucket.marcondesads.patterngames.api.modelo;

import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class JogoTest {
    
    public JogoTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of addObserver method, of class Jogo.
     */
    @Test
    public void testAddObserver() {
        System.out.println("addObserver");
        Observer obs = new Observer() {

            @Override
            public void doAction(Jogo obs) {
                
            }
        };
        Jogo instance = new Jogo("Devil May Cry");
        instance.addObserver(obs);
        assertTrue(instance.getObservers().contains(obs));
    }

    /**
     * Test of remObserver method, of class Jogo.
     */
    @Test
    public void testRemObserver() {
        System.out.println("remObserver");
        Observer obs = new Observer() {

            @Override
            public void doAction(Jogo obs) {
                
            }
        };
        Jogo instance = new Jogo("Devil May Cry");
        instance.remObserver(obs);
        assertFalse(instance.getObservers().contains(obs));
    }

    /**
     * Test of notifyObs method, of class Jogo.
     */
    @Test
    public void testNotifyObs() {
        System.out.println("notifyObs");
        Observer obs = new Observer() {

            @Override
            public void doAction(Jogo obs) {
                throw new TestExcetion();
            }
        };
        Jogo instance = new Jogo("Devil May Cry");
        instance.addObserver(obs);
        try{
            instance.notifyObs();
        }catch(TestExcetion e){
            return;
        }
        fail();
    }

    /**
     * Test of alocar method, of class Jogo.
     */
    @Test
    public void testAlocar() throws Exception {
        System.out.println("alocar");
        Jogo instance = new Jogo("Devil May Cry");
        instance.alocar();
        assertEquals(EstadoJogoEnum.ALOCADO, instance.getEstado());
        try{
            instance.alocar();
        }catch(AlocacaoException e){
            return;
        }
        fail("The test case is a prototype.");
    }

    /**
     * Test of desalocar method, of class Jogo.
     */
    @Test
    public void testDesalocar() throws Exception {
        System.out.println("desalocar");
        Jogo instance = new Jogo("Devil May Cry");
        instance.alocar();
        instance.desalocar();
        assertEquals(EstadoJogoEnum.DISPONIVEL, instance.getEstado());
        try{
            instance.desalocar();
        }catch(AlocacaoException e){
            return;
        }
        
        fail("The test case is a prototype.");
    }
    
private class TestExcetion extends RuntimeException{
    
} 

}

