package org.bitbucket.marcondesads.patterngames.api.modelo;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe que representa uma alocação realizada no sistema.
 * @author José Marcondes do Nascimento Junior
 */
public class Locacao {
    private int id;
    private Cliente cliente;
    private Jogo jogo;
    private LocalDate dataRealizacao;
    private LocStrategy tipo;    

    public Locacao(Cliente cliente, Jogo jogo, LocStrategy tipo) {
        this.cliente = cliente;
        this.jogo = jogo;
        this.tipo = tipo;
        this.dataRealizacao = LocalDate.now();
    }
    
    public Locacao(Cliente cliente, Jogo jogo,LocalDate dataRealizacao, LocStrategy tipo) {
        this.cliente = cliente;
        this.jogo = jogo;
        this.tipo = tipo;
        this.dataRealizacao = dataRealizacao;
    }
    
    
    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public LocalDate getData() {
        return dataRealizacao;
    }

    public void setData(LocalDate data) {
        this.dataRealizacao = data;
    }

    public LocStrategy getTipo() {
        return tipo;
    }

    public void setTipo(LocStrategy tipo) {
        this.tipo = tipo;
    }
    
    public double calcularMulta(){
        return tipo.calcularMulta(this, LocalDate.now());
    }
    
    public double calcularValor(){
        return tipo.calcularValor();
    }
    
    public LocalDate calcularEntrega(){
        return dataRealizacao.plus(Period.ofDays(tipo.calcularDias()));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
