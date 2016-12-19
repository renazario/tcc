package br.com.marisamu.facade;

import br.com.marisamu.exception.DesencapsulaException;
import br.com.marisamu.exception.PersistenceException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author renan
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T salvar(T entity) {
        entity = getEntityManager().merge(entity);
        getEntityManager().flush();
        return entity;
    }

    public void excluir(T entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
            
    }

    public T pesquisar(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> listar() {
        Query query = getEntityManager().createQuery(
                "FROM " + entityClass.getSimpleName());
        return query.getResultList();
    }

}
