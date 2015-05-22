package org.bitbucket.marcondesads.patterngames.api.modelo;

/**
 * Enum possuindo todos os estado em que um jogo pode se encontrar.
 * @author José Marcondes do Nascimento Junior
 */
public enum EstadoJogoEnum implements EstadoJogo{
    /**
     * Estado alocado, um jogo alocado não pode ser alocado novamente até ser desalocado.
     */
    ALOCADO{

        @Override
        public void alocar(Jogo jogo) throws AlocacaoException {
            throw new AlocacaoNaoPermitida("Este jogo já está alocado para outro cliente");
        }

        @Override
        public void desalocar(Jogo jogo) throws AlocacaoException {
            jogo.setEstado(DISPONIVEL);
        }
        
    },
    DISPONIVEL{

        @Override
        public void alocar(Jogo jogo) throws AlocacaoException {
            jogo.setEstado(ALOCADO);
        }

        @Override
        public void desalocar(Jogo jogo) throws AlocacaoException {
            throw new DesalocacaoNaoPermitida("Este jogo não está alocado");
        }
        
    }
    
}
