/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Observer;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class DAOObservadoBD implements DAOObservado{

    private final Connection conn;
    
    public DAOObservadoBD() throws SQLException{
        conn = ConnectionManager.getConnection();
    }
    
    @Override
    public void guardar(String login, int idJogo) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO OBSERVADO VALUES(?,?)")){
            ps.setInt(1, idJogo);
            ps.setString(2, login);
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(String login, int idJogo) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM OBSERVADO "
                + "WHERE id_jogo = ? AND login_cli = ?")){
            ps.setInt(1, idJogo);
            ps.setString(2, login);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Observer> listarObs(int idJogo) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM OBSERVADO WHERE id_jogo = ?")){
            ps.setInt(1, idJogo);
            try(ResultSet rs = ps.executeQuery()){
                List<Observer> list = new ArrayList<>();
                while(rs.next()){
                    String login = rs.getString("login_cli");
                    list.add(new DAOClienteBD().localizar(login));
                }
                return list;
            }
        }
    }

    @Override
    public void excluirObsJogo(int id) throws SQLException {
        for(Observer obs: this.listarObs(id)){
            this.excluir(((Cliente) obs).getLogin(), id);
        }
    }
    
}
