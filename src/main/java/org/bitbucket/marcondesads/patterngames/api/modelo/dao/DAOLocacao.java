package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface DAOLocacao {
    public void guardar(Locacao jogo) throws SQLException;
    public void excluir(int id) throws SQLException;
    public void atualizar(Locacao jogo) throws SQLException;
    public List<Locacao> listar() throws SQLException;
    public Locacao localizar(int id) throws SQLException;
    public void excluirTodos() throws SQLException;
    public int nextLocId() throws SQLException;
}
