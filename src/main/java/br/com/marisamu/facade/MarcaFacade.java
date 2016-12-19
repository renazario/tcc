
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Grupo;
import br.com.marisamu.entidades.Marca;
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
public class MarcaFacade extends AbstractFacade<Marca>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
    
    public MarcaFacade() {
        super(Marca.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public void excluir(Marca entity) throws PersistenceException {
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
