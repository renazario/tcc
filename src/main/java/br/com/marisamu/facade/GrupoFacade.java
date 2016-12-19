
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Estado;
import br.com.marisamu.entidades.Grupo;
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
public class GrupoFacade extends AbstractFacade<Grupo>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
    
    public GrupoFacade() {
        super(Grupo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public void excluir(Grupo entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Não é possível excluir, existem vinculos com Produtos");
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }
    
}
