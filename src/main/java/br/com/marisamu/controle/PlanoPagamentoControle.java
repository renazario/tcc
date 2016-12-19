/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.PlanoPagamento;
import br.com.marisamu.entidades.TipoPagamento;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.PlanoPagamentoFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author renan
 */
@ManagedBean
@SessionScoped
public class PlanoPagamentoControle extends AbstractControle<PlanoPagamento> implements Serializable {

    @EJB
    private PlanoPagamentoFacade planoPagamentoFacade;
    private TipoPagamento tipoPagamento;

    public PlanoPagamentoControle() {
        super(PlanoPagamento.class);

    }

    public void tipoPagamentoAvista() {
        if (super.getEntidade().getTipoPagamento().equals(TipoPagamento.AVISTA) ) {
            super.getEntidade().setQtdMaxParc(1);
        } else {
            super.getEntidade().setQtdMaxParc(0);
        }
    }

    @Override
    protected AbstractFacade<PlanoPagamento> getFacade() {
        return planoPagamentoFacade;
    }

    public TipoPagamento[] getTipoPagamentos() {
        return TipoPagamento.values();
    }

}
