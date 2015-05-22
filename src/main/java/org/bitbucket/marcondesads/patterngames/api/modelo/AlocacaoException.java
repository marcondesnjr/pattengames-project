package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Exceção de alocações e desalocações malsucedidas
 * @author José Marcondes do Nascimento Junior
 */
public class AlocacaoException extends Exception {

    public AlocacaoException() {
    }

    public AlocacaoException(String message) {
        super(message);
    }
    
}
