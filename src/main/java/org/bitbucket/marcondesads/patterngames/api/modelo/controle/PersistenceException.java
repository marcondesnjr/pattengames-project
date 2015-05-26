package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class PersistenceException extends Exception {

    public PersistenceException() {
    }

    public PersistenceException(String message) {
        super(message);
    }

     public static PersistenceException defautMessage(){
         return new PersistenceException("Erro no acesso aos dados");
     }   
}
