/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitbucket.marcondesads.patterngames.api.modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class ClienteTest {
    
    public ClienteTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doAction method, of class Cliente.
     */
    @Test
    public void testDoAction() {
        System.out.println("doAction");
        Jogo obs = new Jogo("Devil may Cry");
        Cliente instance = new Cliente("login", "senha", "patterngames", "123.123.123-12","patterngames3@gmail.com");
        instance.doAction(obs);
    }
    
}
