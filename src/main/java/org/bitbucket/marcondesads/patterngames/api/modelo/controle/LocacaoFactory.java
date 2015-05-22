package org.bitbucket.marcondesads.patterngames.api.modelo.controle;

import java.time.DayOfWeek;
import java.time.LocalDate;
import org.bitbucket.marcondesads.patterngames.api.modelo.AlocacaoException;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.LocStrategyEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;

/**
 * Classe para criação de locações no sistema, o metodo getNewLocacao() retorna uma locação
 * especial ou normal dependendo da hr do sistema.
 * @author José Marcondes do Nascimento Junior
 */
public abstract class LocacaoFactory {
    public static Locacao getNewLocacao(Cliente cli, Jogo jg) throws AlocacaoException{
        jg.alocar();
        if(LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY)
            return new Locacao(cli,jg,LocStrategyEnum.LOCACAO_ESPECIAL);
        else
            return new Locacao(cli, jg, LocStrategyEnum.LOCACAO_NORMAL);
    }
}
