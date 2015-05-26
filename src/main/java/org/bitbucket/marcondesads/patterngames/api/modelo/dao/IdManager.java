package org.bitbucket.marcondesads.patterngames.api.modelo.dao;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public abstract class IdManager {
    private static AtomicInteger atomicIdJogo;
    private static AtomicInteger atomicIdLocacao;
    
    /**
     * Retona um novo id válido para jogos, caso a base de dados não possa ser acessada o id começará a ser contado
     * do 0 novamente.
     * @return novo id.
     */
    public static int getJogoId(){
        if(atomicIdJogo == null){
            try{
                DAOJogo dao = new DAOJogoBD();
                atomicIdJogo = new AtomicInteger(dao.nextId());
            }catch(SQLException ex){
                Logger.getLogger(IdManager.class.getName()).log(Level.SEVERE,null,ex);
                atomicIdJogo = new AtomicInteger();
            }
        }
        return atomicIdJogo.getAndIncrement();
    }
    
    public static int getLocacaoId(){
        if(atomicIdLocacao == null){
            try{
                DAOLocacao dao = new DAOLocacaoBD();
                atomicIdLocacao = new AtomicInteger(dao.nextLocId());
            }catch(SQLException ex){
                Logger.getLogger(IdManager.class.getName()).log(Level.SEVERE,null,ex);
                atomicIdJogo = new AtomicInteger();
            }
        }
        return atomicIdJogo.getAndIncrement();
    }
}
