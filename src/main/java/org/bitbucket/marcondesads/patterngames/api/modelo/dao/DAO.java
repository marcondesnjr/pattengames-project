/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public interface DAO<T> {
    public void guardar(T obj) throws SQLException;
    public void excluir(String obj) throws SQLException;
    public void atualizar(T obj) throws SQLException;
    public List<T> listar() throws SQLException;
    public T localizar(String obj) throws SQLException;
}
