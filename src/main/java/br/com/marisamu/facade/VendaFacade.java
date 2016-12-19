/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.ContasReceber;
import br.com.marisamu.entidades.Venda;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class VendaFacade extends AbstractFacade<Venda> {

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public VendaFacade() {
        super(Venda.class);
    }

    public Boolean validaAberturaVenda(Venda v){
        List<ContasReceber> cr = new ArrayList<>();
        cr = em.createQuery(
                        "FROM ContasReceber as cr WHERE cr.venda="+ v +" and cr.dtRecebimento is not null ").getResultList();
        if(cr.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
    public void carregaVenda(Venda v) {
        v.setItensVenda(
                em.createQuery(
                        "FROM ItemVenda AS iv WHERE iv.venda=" + v).getResultList());
        v.setContasReceber(
                em.createQuery(
                        "FROM ContasReceber as cr WHERE cr.venda="+v).getResultList());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
