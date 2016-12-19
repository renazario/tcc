/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Pessoa;
import br.com.marisamu.entidades.PessoaJuridica;
import br.com.marisamu.exception.DesencapsulaException;
import br.com.marisamu.exception.PersistenceException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author renan
 */
@Stateless
public class FornecedorFacade extends AbstractFacade<PessoaJuridica>{
    
    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public FornecedorFacade() {
        super(PessoaJuridica.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PessoaJuridica> listar() {
         Query query = getEntityManager().createQuery(
                "FROM Pessoa as p where p.fornecedor = true");
        return query.getResultList();
    }
    
    @Override
    public void excluir(PessoaJuridica entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Não é possível excluir, existem compras para esse fornecedor");
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }
    
}
