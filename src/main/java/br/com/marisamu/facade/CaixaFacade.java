/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Caixa;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class CaixaFacade extends AbstractFacade<Caixa>{
    
    @PersistenceContext(name = "marisamuPU")
    private EntityManager em;

    public CaixaFacade() {
        super(Caixa.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
