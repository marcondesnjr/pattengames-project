package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.EstadoJogoEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.Observer;

/**
 * Classe de acesso aos dados de jogos
 * @author José Marcondes do Nascimento Junior
 */
public class DAOJogoBD implements DAOJogo{

    private Connection conn;
    private final int LOCADO = 0;
    private final int DISPONIVEL = 1;
    
    
    public DAOJogoBD() throws SQLException{
        conn = ConnectionManager.getConnection();
    }
    
    @Override
    public void guardar(Jogo obj) throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO JOGO VALUES(?,?,?)")){
            ps.setInt(1, obj.getId());
            ps.setString(2, obj.getNome());
            ps.setInt(3, obj.getEstado() == EstadoJogoEnum.DISPONIVEL?DISPONIVEL:LOCADO);
            ps.executeUpdate();
            DAOObservado dao = new DAOObservadoBD();
            for(Observer cli: obj.getObservers()){
                dao.guardar(((Cliente) cli).getLogin(), obj.getId());
            }
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    public void excluir(int id) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM JOGO WHERE id = ?")){
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public void atualizar(Jogo obj) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("UPDATE JOGO SET estado = ? WHERE nome = ?")){
            ps.setInt(1, obj.getEstado() == EstadoJogoEnum.DISPONIVEL?DISPONIVEL:LOCADO);
            ps.setString(2, obj.getNome());
            DAOObservado dao = new DAOObservadoBD();
            ps.executeUpdate();
            dao.excluirObsJogo(obj.getId());
            for(Observer cli: obj.getObservers()){
                dao.guardar(((Cliente) cli).getLogin(), obj.getId());
            }
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public List<Jogo> listar() throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM JOGO")){
            try(ResultSet rs = ps.executeQuery()){
                List<Jogo> list = new ArrayList<>();
                while(rs.next()){
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    int estado = rs.getInt("estado");
                    Jogo jg = new Jogo(id,nome, estado == DISPONIVEL?EstadoJogoEnum.DISPONIVEL:EstadoJogoEnum.ALOCADO); // 0 Alocado, 1 Disponivel
                    DAOObservado dao = new DAOObservadoBD();
                    jg.setObservers(dao.listarObs(id));
                    list.add(jg);
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
    public Jogo localizar(int id) throws SQLException{
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM JOGO WHERE id = ?")){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                Jogo jg = null;
                if(rs.next()){
                    String nome = rs.getString("nome");
                    int estado = rs.getInt("estado");
                    jg = new Jogo(id,nome, estado == DISPONIVEL?EstadoJogoEnum.DISPONIVEL:EstadoJogoEnum.ALOCADO);
                    DAOObservado dao = new DAOObservadoBD();
                    jg.setObservers(dao.listarObs(id));
                }
                conn.commit();
                return jg;
            }
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
    @Override
    public int nextId() throws SQLException{
        try(CallableStatement cs = conn.prepareCall("{? = call next_id_jogo()}")){
            cs.registerOutParameter(1, Types.INTEGER);
            cs.executeUpdate();
            int result = cs.getInt(1);
            conn.commit();
            return result;
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
    @Override
    public void excluirTodos() throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM JOGO")){
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
}
