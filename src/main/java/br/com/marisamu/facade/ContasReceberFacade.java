/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.facade;

import br.com.marisamu.entidades.Cidade;
import br.com.marisamu.entidades.ContasReceber;
import br.com.marisamu.exception.DesencapsulaException;
import br.com.marisamu.exception.PersistenceException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author renan
 */
@Stateless
public class ContasReceberFacade extends AbstractFacade<ContasReceber> {

    @PersistenceContext(unitName = "marisamuPU")
    private EntityManager em;

    public ContasReceberFacade() {
        super(ContasReceber.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void excluir(ContasReceber entity) throws PersistenceException {
        try {
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Não é possível excluir, existem vinculo com a venda!");
        } catch (Exception ex) {
            throw new PersistenceException(DesencapsulaException.desencapsula(ex));
        }
    }

    public List<ContasReceber> contasReceber() {
        return em.createQuery("FROM ContasReceber AS cr WHERE cr.pago=false").getResultList();
    }

    public List<ContasReceber> contasRecebidas() {
        return em.createQuery("FROM ContasReceber AS cr WHERE cr.pago=true").getResultList();
    }

    public void estornar(ContasReceber cr) {
        List<ContasReceber> lista = em.createQuery("FROM ContasReceber as cr WHERE cr.venda=" + cr.getVenda().getId() + " AND cr.pago=false AND cr.nrParcela='" + cr.getNrParcela() + "'").getResultList();
        if (lista.isEmpty()) {
            cr.setVrRecebido(BigDecimal.ZERO);
            cr.setPago(false);
            cr.setDtRecebimento(null);
            em.merge(cr);
        } else {
            ContasReceber c = (ContasReceber) lista.get(0);
            c.setVrParcela(c.getVrParcela().add(cr.getVrParcela()));
            em.merge(c);
            em.remove(em.merge(cr));
        }
    }
    
    
}
