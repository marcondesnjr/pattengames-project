package org.bitbucket.marcondesads.patterngames.api.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Classe que representa um jogo cadastrado no sistema, este jogo pode ser observado e cada
 * vez que este jogo for  desalocado todos os seus observadores serão informados.
 * @author José Marcondes do Nascimento Junior
 */
public class Jogo implements Observable{
    private int id;
    private String nome;
    private EstadoJogo estado;
    private Collection<Observer> observers;

    /**
     * Cria um novo jogo com o nome especificado, um jogo recentimente criado é tratado com o estado DISPONIVEL.
     * @param nome 
     */
    public Jogo(String nome){
        this.nome = nome;
        this.observers = new HashSet<>();
        this.estado = EstadoJogoEnum.DISPONIVEL;
    }
    
    public Jogo(String nome, EstadoJogoEnum est){
        this.nome = nome;
        this.observers = new HashSet<>();
        this.estado = est;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoJogo getEstado() {
        return estado;
    }

    public Collection<Observer> getObservers() {
        return Collections.unmodifiableCollection(observers);
    }

    public void setObservers(Collection<Observer> observers) {
        this.observers = observers;
    }
    
    

    public void setEstado(EstadoJogo estado) {
        this.estado = estado;
    }

    @Override
    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    @Override
    public void remObserver(Observer obs) {
        this.observers.remove(obs);
    }

    @Override
    public void notifyObs() {
        for(Observer obs: this.observers){
            obs.doAction(this);
        }
    }
    
    public void alocar() throws AlocacaoException{
        estado.alocar(this);
    }
    
    public void desalocar() throws AlocacaoException{
        estado.desalocar(this);
        for(Observer obs: observers){
            obs.doAction(this);
        }
    }
    
    
}
