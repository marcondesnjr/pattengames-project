package org.bitbucket.marcondesads.patterngames.api.modelo;

import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * Enum com os tipos diferentes de abordagem que podem ter os metodos de locação.
 * @author José Marcondes do Nascimento Junior
 */
public enum LocStrategyEnum implements LocStrategy{
    LOCACAO_ESPECIAL{

        private final double VALOR = 5.00;
        private final int DURACAO = 2;
        private final double MULTA = 3.00;
        private final double MULTA_DIA = 3.00;
        
        @Override
        public double calcularMulta(Locacao aloc, LocalDate atual) {
            LocalDate dataCorreta = aloc.getData().plus(Period.ofDays(this.DURACAO));
            if(atual.compareTo(dataCorreta) <= 0)
                return 0.00;
            else
                return MULTA + MULTA_DIA * dataCorreta.compareTo(atual);
        }

        @Override
        public int calcularDias() {
            return DURACAO;
        }

        @Override
        public double calcularValor() {
            return VALOR;
        }
        
    },
    LOCACAO_NORMAL{
        private final double VALOR = 3.00;
        private final int DURACAO = 1;
        private final double MULTA = 1.00;
        private final double MULTA_DIA = 3.00;
        
        @Override
        public double calcularMulta(Locacao aloc, LocalDate atual) {
            LocalDate dataCorreta = aloc.getData().plus(Period.ofDays(this.DURACAO));
            if(atual.compareTo(dataCorreta) <= 0)
                return 0.00;
            else
                return MULTA + MULTA_DIA * dataCorreta.compareTo(atual);
        }

        @Override
        public int calcularDias() {
            return DURACAO;
        }

        @Override
        public double calcularValor() {
            return VALOR;
        }
    }
}
