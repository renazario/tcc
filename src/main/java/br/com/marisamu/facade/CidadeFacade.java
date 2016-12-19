
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Cidade;
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
public class CidadeFacade extends AbstractFacade<Cidade>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
      
    public CidadeFacade() {
        super(Cidade.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public void excluir(Cidade entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e){
            throw new PersistenceException("Não é possível excluir, existem vinculos com outros cadastros!");
        }catch (Exception ex){
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }
    
}
