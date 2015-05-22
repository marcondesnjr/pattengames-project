package org.bitbucket.marcondesads.patterngames.api.modelo;

import java.time.LocalDate;

/**
 * Interface de estratega para calculo de diferentes tipos de locações.
 * @author José Marcondes do Nascimento Junior
 */
public interface LocStrategy {
    
    public double calcularMulta(Locacao aloc,LocalDate atual);
    public int calcularDias();
    public double calcularValor();
    
}
