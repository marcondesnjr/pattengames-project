package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Exceção de alocação não permitida no sistema.
 * @author José Marcondes do Nascimento Junior
 */
class AlocacaoNaoPermitida extends AlocacaoException {

    public AlocacaoNaoPermitida() {
    }
    
    AlocacaoNaoPermitida(String str) {
        super(str);
    }
    
}
