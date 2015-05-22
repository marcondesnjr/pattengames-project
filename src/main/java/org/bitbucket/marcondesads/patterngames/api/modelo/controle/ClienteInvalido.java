/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
class ClienteInvalido extends Exception {

    public ClienteInvalido() {
    }

    public ClienteInvalido(String message) {
        super(message);
    }
    
}
