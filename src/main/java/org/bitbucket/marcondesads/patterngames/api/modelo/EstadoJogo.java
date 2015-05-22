package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Interface de estado de jogos, esta interface define como cada método será executado
 * dependentemente do estado do jogo no sistema.
 * @author José Marcondes do Nascimento Junior
 */
public interface EstadoJogo {
    public void alocar(Jogo jogo) throws AlocacaoException;
    public void desalocar(Jogo jogo) throws AlocacaoException;
}
