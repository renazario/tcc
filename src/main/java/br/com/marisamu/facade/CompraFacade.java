/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Compra;
import br.com.marisamu.entidades.ContasPagar;
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
public class CompraFacade extends AbstractFacade<Compra> {

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public CompraFacade() {
        super(Compra.class);
    }

    public Boolean validaAberturaCompra(Compra c) {
        List<ContasPagar> cp = new ArrayList<>();
        cp = em.createQuery(
                "FROM ContasPagar as cp WHERE cp.compra=" + c + " and cp.dtPagamento is not null ").getResultList();
        if(cp.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    public void carregaCompra(Compra c) {
        c.setItensCompra(
                em.createQuery(
                        "FROM ItemCompra AS ic WHERE ic.compra=" + c).getResultList());
        c.setContasPagar(
                em.createQuery(
                        "FROM ContasPagar as cp WHERE cp.compra=" + c).getResultList());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
