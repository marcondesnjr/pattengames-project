package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface DAOJogo {
    public void guardar(Jogo jg) throws SQLException;
    public void excluir(int id) throws SQLException;
    public void atualizar(Jogo obj) throws SQLException;
    public List<Jogo> listar() throws SQLException;
    public Jogo localizar(int id) throws SQLException;
    public void excluirTodos() throws SQLException;
    public int nextId() throws SQLException;
    
}
