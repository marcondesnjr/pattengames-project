package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.Observer;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface DAOObservado {
    public void guardar(String login, int idJogo) throws SQLException;
    public void excluir(String login, int idJogo) throws SQLException;
    public List<Observer> listarObs(int idJogo) throws SQLException;
    public void excluirObsJogo(int id) throws SQLException;
}
