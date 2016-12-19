
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
    
    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
   

}
