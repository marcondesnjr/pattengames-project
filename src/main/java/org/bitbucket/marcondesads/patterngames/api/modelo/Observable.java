package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Interface de elementos que podem ser objervados por elementos Observer's.
 * Estes elementos devem ser adicionados na lista de Observer's do objeto para que
 * este seja notificado atravéz do seu método doAction caso ocorra eventos específicos.
 * @author José Marcondes do Nascimento Junior
 */
public interface Observable {
    public void addObserver(Observer obs);
    public void remObserver(Observer obs);
    public void notifyObs();    
}