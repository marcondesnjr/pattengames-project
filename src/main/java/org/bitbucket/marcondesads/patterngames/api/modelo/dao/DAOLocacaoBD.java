package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyValue;
import org.bitbucket.marcondesads.patterngames.api.modelo.Cliente;
import org.bitbucket.marcondesads.patterngames.api.modelo.Jogo;
import org.bitbucket.marcondesads.patterngames.api.modelo.LocStrategyEnum;
import org.bitbucket.marcondesads.patterngames.api.modelo.Locacao;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class DAOLocacaoBD implements DAOLocacao{

    private Connection conn;
    private final int NORMAL = 0;
    private final int ESPECIAL = 1;
    
    
    public DAOLocacaoBD() throws SQLException{
        conn = ConnectionManager.getConnection();
    }
    
    @Override
    public void guardar(Locacao loc) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("INSERT INTO LOCACAO "
                + "VALUES(?,?,?,?,?)")){
           ps.setInt(1, loc.getId());
           ps.setInt(2, loc.getJogo().getId());
           ps.setString(3, loc.getCliente().getLogin());
           ps.setDate(4, Date.valueOf(loc.getData()));
           int tipo = loc.getTipo() == LocStrategyEnum.LOCACAO_NORMAL?0:1;// Locação normal = 0, Locação especial = 1
           ps.setInt(5, tipo);
           ps.executeUpdate();
           ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM LOCACAO WHERE id = ?")){
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public void atualizar(Locacao loc) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("UPDATE TABLE LOCACAO SET "
                + "id_jogo = ?, login_cli = ?, data = ?, tipo = ? "
                + "WHERE id = ?")){
            ps.setInt(1, loc.getJogo().getId());
            ps.setString(2, loc.getCliente().getLogin());
            ps.setDate(3, Date.valueOf(loc.getData()));
            int tipo = loc.getTipo() == LocStrategyEnum.LOCACAO_NORMAL?NORMAL:ESPECIAL;// Locação normal = 0, Locação especial = 1
            ps.setInt(4, tipo);
            ps.setInt(5, loc.getId());
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public List<Locacao> listar() throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM LOCACAO");
                ResultSet rs = ps.executeQuery()){
            List<Locacao> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                Jogo jogo = new DAOJogoBD().localizar(rs.getInt("id_jogo"));
                Cliente cli = new DAOClienteBD().localizar(rs.getString("login_cli"));
                LocalDate data = rs.getDate("data").toLocalDate();
                LocStrategyEnum tipo = rs.getInt("tipo") == NORMAL? LocStrategyEnum.LOCACAO_NORMAL:LocStrategyEnum.LOCACAO_ESPECIAL; // Locação normal = 0, Locação especial = 1
                list.add(new Locacao(id,cli, jogo, data, tipo));
            }
            conn.commit();
            return list;
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public Locacao localizar(int id) throws SQLException {
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM LOCACAO WHERE id = ?")){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    Jogo jogo = new DAOJogoBD().localizar(rs.getInt("id_jogo"));
                    Cliente cli = new DAOClienteBD().localizar(rs.getString("login_cli"));
                    LocalDate data = rs.getDate("data").toLocalDate();
                    LocStrategyEnum tipo = rs.getInt("tipo") == NORMAL? LocStrategyEnum.LOCACAO_NORMAL:LocStrategyEnum.LOCACAO_ESPECIAL; // Locação normal = 0, Locação especial = 1
                    return new Locacao(id,cli, jogo, data, tipo);
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
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM LOCACAO")){
            ps.executeUpdate();
            ps.getConnection().commit();
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }

    @Override
    public int nextLocId() throws SQLException {
        try(CallableStatement cs = conn.prepareCall("{? = call next_id_locacao()}")){
            cs.registerOutParameter(1, Types.INTEGER);
            cs.executeUpdate();
            int id = cs.getInt(1);
            conn.commit();
            return id;
        }catch(SQLException e){
            conn.rollback();
            throw e;
        }
    }
    
    
    
}
