/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.ContasPagar;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.ContasPagarFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author renan
 */
@ManagedBean
@SessionScoped
public class ContasPagarControle extends AbstractControle<ContasPagar> implements Serializable {

    @EJB
    private ContasPagarFacade cpFacade;
    private BigDecimal vrPago;
    private Date novaDataVencimento;

    public ContasPagarControle() {
        super(ContasPagar.class);
    }

    @Override
    protected AbstractFacade<ContasPagar> getFacade() {
        return cpFacade;
    }
    
    public List<ContasPagar> getContasPagar(){
        return cpFacade.contasPagar();
    }
    
    public List<ContasPagar> getContasPagas(){
        return cpFacade.contasPagas();
    }
    

    public void pagarConta() {
        if (super.getEntidade().getVrParcela().compareTo(vrPago) > 0) {
            ContasPagar cp = new ContasPagar();
            cp.setDtEmissao(new Date());
            cp.setPago(false);
            cp.setPessoa(super.getEntidade().getPessoa());
            cp.setCompra(super.getEntidade().getCompra());
            cp.setVrParcela(super.getEntidade().getVrParcela().subtract(vrPago));
            cp.setDtVencimento(novaDataVencimento);
            cpFacade.salvar(cp);
            super.getEntidade().setVrParcela(vrPago);
        }
        super.getEntidade().setPago(true);
        super.getEntidade().setDtPagamento(new Date());
        cpFacade.salvar(super.getEntidade());
        super.setEntidade(null);
    }

    public BigDecimal getVrPago() {
        return vrPago;
    }

    public void setVrPago(BigDecimal vrPago) {
        this.vrPago = vrPago;
    }

    public Date getNovaDataVencimento() {
        return novaDataVencimento;
    }

    public void setNovaDataVencimento(Date novaDataVencimento) {
        this.novaDataVencimento = novaDataVencimento;
    }

}
