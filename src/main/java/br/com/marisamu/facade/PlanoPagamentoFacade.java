/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Marca;
import br.com.marisamu.entidades.PlanoPagamento;
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
public class PlanoPagamentoFacade extends AbstractFacade<PlanoPagamento>{
    
    @PersistenceContext (unitName = "marisamuPU")
    private EntityManager em;

    public PlanoPagamentoFacade() {
        super(PlanoPagamento.class);
    }
    
    @Override
    protected EntityManager getEntityManager(){
        return em;
    }
    
    @Override
    public void excluir(PlanoPagamento entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Não é possível excluir, existem vinculos com o movimento Venda");
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }
    
}
