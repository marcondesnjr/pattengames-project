package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class DAOClienteBD implements DAOCliente{
    
    private Connection conn;
    
    public DAOClienteBD() throws SQLException{
        conn = ConnectionManager.getConnection();
    }
    
    @Override
    public void guardar(Cliente obj) throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO CLIENTE VALUES(?,?,?,?,?)")){
            ps.setString(1, obj.getLogin());
            ps.setString(2, obj.getSenha());
            ps.setString(3, obj.getCpf());
            ps.setString(4, obj.getNome());
            ps.setString(5, obj.getEmail());
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public void excluir(String login) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM CLIENTE WHERE login = ?")){
            ps.setString(1, login);
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public void atualizar(Cliente obj) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("UPDATE TABLE CLIENTE SET senha = ?, "
                + "nome = ?, cpf = ?, email = ? "
                + "WHERE login = ?")){
            ps.setString(1, obj.getSenha());
            ps.setString(2, obj.getNome());
            ps.setString(3, obj.getCpf());
            ps.setString(4, obj.getEmail());
            ps.setString(5, obj.getLogin());
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public List<Cliente> listar() throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIENTE")){
            try(ResultSet rs = ps.executeQuery()){
                List<Cliente> list = new ArrayList<>();
                while(rs.next()){
                    String login = rs.getString("login");
                    String senha = rs.getString("senha");
                    String cpf = rs.getString("cpf");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    list.add(new Cliente(login, senha, nome, cpf, email));
                }
                conn.commit();
                return list;
            }
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public Cliente localizar(String login) throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM CLIENTE WHERE login = ?")){
            try(ResultSet rs = ps.executeQuery()){
                Cliente cli = null;
                if(rs.next()){
                    String senha = rs.getString("senha");
                    String cpf = rs.getString("cpf");
                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    return new Cliente(login, senha, nome, cpf, email);
                }
                conn.commit();
                return null;
            }
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
        
    @Override
    public void excluirTodos() throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM CLIENTE")){
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
}
