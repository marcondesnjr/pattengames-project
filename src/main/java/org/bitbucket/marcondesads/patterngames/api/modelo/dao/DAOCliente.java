package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface DAOCliente {
    public void guardar(Cliente obj) throws SQLException;
    public void excluir(String obj) throws SQLException;
    public void atualizar(Cliente obj) throws SQLException;
    public List<Cliente> listar() throws SQLException;
    public Cliente localizar(String obj) throws SQLException;
    public void excluirTodos() throws SQLException;
}
