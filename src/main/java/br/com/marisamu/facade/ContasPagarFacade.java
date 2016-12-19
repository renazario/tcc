/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.ContasPagar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class ContasPagarFacade extends AbstractFacade<ContasPagar>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
    
    public ContasPagarFacade() {
        super(ContasPagar.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<ContasPagar> contasPagar(){
        return em.createQuery("FROM ContasPagar AS cp WHERE cp.pago=false").getResultList();
    }
    
    public List<ContasPagar> contasPagas(){
        return em.createQuery("FROM ContasPagar AS cp WHERE cp.pago=true").getResultList();
    }
    
}
