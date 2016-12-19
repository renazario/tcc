/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Funcionario;
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
public class FuncionarioFacade extends AbstractFacade<Funcionario>{

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;
    
    public FuncionarioFacade() {
        super(Funcionario.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Funcionario> listar() {
         Query query = getEntityManager().createQuery(
                "FROM Pessoa as p where p.funcionario = true");
        return query.getResultList();
    }
    
}
