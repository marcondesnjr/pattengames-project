package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Interface que define objetos que podem objervar outros objetos do tipo Observable.
 * Este objeto deve ser adicionado na lista de observação do objeto que se deseja observar e 
 * será notificado pelo método doAction().
 * @author José Marcondes do Nascimento Junior
 */
public interface Observer {
    public void doAction(Jogo obs);
}
