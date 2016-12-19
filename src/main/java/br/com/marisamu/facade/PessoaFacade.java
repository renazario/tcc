/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Pessoa;
import br.com.marisamu.entidades.PlanoPagamento;
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
public class PessoaFacade extends AbstractFacade<Pessoa> {

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public PessoaFacade() {
        super(Pessoa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Pessoa> listar() {
               Query query = getEntityManager().createQuery(
                "FROM Pessoa as p where p.fornecedor = false and p.funcionario = false");
        return query.getResultList();
    }
    
    @Override
    public void excluir(Pessoa entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Não é possível excluir, existem vendas para esse cliente");
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }
    
}
