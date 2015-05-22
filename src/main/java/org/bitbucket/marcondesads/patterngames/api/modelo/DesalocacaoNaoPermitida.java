package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Exceção de desalocação não permetida pelo sistema.
 * @author José Marcondes do Nascimento Junior
 */
class DesalocacaoNaoPermitida extends AlocacaoException {

    public DesalocacaoNaoPermitida() {
    }
    
    DesalocacaoNaoPermitida(String str) {
        super(str);
    }
    
}
