package br.com.marisamu.facade;

import br.com.marisamu.entidades.Estado;
import br.com.marisamu.exception.DesencapsulaException;
import br.com.marisamu.exception.PersistenceException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class EstadoFacade extends AbstractFacade<Estado> {

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public EstadoFacade() {
        super(Estado.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void excluir(Estado entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e){
            throw new PersistenceException("Não é possível excluir o resgistro pois está vinculado com outras Cidades");
        }catch (Exception ex){
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }

}
