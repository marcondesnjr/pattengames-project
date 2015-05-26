package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.EstadoJogoEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;

/**
 * Classe de acesso aos dados de jogos
 * @author José Marcondes do Nascimento Junior
 */
public class DaoJogo implements DAO<Jogo>{

    private Connection conn;
    
    public DaoJogo() throws SQLException{
        conn = ConnectionManager.getConnection();
    }
    
    @Override
    public void guardar(Jogo obj) throws SQLException{
        try(PreparedStatement ps = conn.prepareCall("INSERT INTO JOGO VALUES(?,?)")){
            ps.setString(1, obj.getNome());
            ps.setInt(2, obj.getEstado() == EstadoJogoEnum.DISPONIVEL?1:0);
            ps.executeUpdate();
            ps.getConnection().commit();
        }
    }

    @Override
    public void excluir(String obj) throws SQLException {
        try(PreparedStatement ps = conn.prepareCall("DELETE FROM JOGO WHERE nome = ?")){
            ps.setString(1, obj);
            ps.executeUpdate();
            ps.getConnection().commit();
        }
    }

    @Override
    public void atualizar(Jogo obj) throws SQLException {
        try(PreparedStatement ps = conn.prepareCall("UPDATE TABLE JOGO SET estado = ? WHERE nome = ?")){
            ps.setString(1, obj.getEstado().toString());
            ps.setString(2, obj.getNome());
            ps.executeUpdate();
            ps.getConnection().commit();
        }
    }

    @Override
    public List<Jogo> listar() throws SQLException {
        try(PreparedStatement ps = conn.prepareCall("SELECT * FROM JOGO")){
            try(ResultSet rs = ps.executeQuery()){
                List<Jogo> list = new ArrayList<>();
                while(rs.next()){
                    String nome = rs.getString("nome");
                    int estado = rs.getInt("estado");
                    list.add(new Jogo(nome, estado == 1?EstadoJogoEnum.DISPONIVEL:EstadoJogoEnum.ALOCADO));
                }
                return list;
            }
        }
    }

    @Override
    public Jogo localizar(String obj) {
        
    }
    
}
