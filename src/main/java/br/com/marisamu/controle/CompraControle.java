/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Caixa;
import br.com.marisamu.entidades.Compra;
import br.com.marisamu.entidades.ContasPagar;
import br.com.marisamu.entidades.TipoPagamento;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.CaixaFacade;
import br.com.marisamu.facade.CompraFacade;
import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author renan
 */
@ManagedBean
@SessionScoped
public class CompraControle extends AbstractControle<Compra> implements Serializable {

    @EJB
    private CompraFacade compraFacade;
    @EJB
    private CaixaFacade caixaFacade;
   

    public CompraControle() {
        super(Compra.class);
    }

    @Override
    protected AbstractFacade<Compra> getFacade() {
        return compraFacade;
    }

    private void mensagem(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void movimentaCaixa(){
        if(super.getEntidade().getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)){
            Caixa c = new Caixa();
            c.setDtLancamento(super.getEntidade().getDtCompra());
            c.setVrDebito(super.getEntidade().getTotalCompra());
            c.setDescricao("Referente a compra: "+super.getEntidade().getId());
            
            c = caixaFacade.salvar(c);
        }else{
            Caixa c = new Caixa();
            c.setDtLancamento(super.getEntidade().getDtCompra());
            c.setVrDebito(super.getEntidade().getVrEntrada());
            
            c = caixaFacade.salvar(c);
        }
    }
    
    public String carregaCompra() {
        if (super.getEntidade() != null) {
            if (super.getEntidade().getStatusFechado()) {
                mensagem("Restrição: Abra a Compra para Alterar",
                        FacesMessage.SEVERITY_WARN);
                return "list";
            }
            compraFacade.carregaCompra(super.getEntidade());
            super.getEntidade().recalcularParcelas();
            return "form?faces-redirect=true";
        } else {
            mensagem("Nenhum elemento foi selecionado para alteração",
                    FacesMessage.SEVERITY_WARN);
            return "list";
        }
    }

    public void addItem() {
        try {
            getEntidade().addItem();
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), "");
            FacesContext
                    .getCurrentInstance().addMessage(null, message);
        }
    }

    public void parcelar() {
        try {
            getEntidade().parcelar();
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    ex.getMessage(), "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String abrirCompra() {
        if (getEntidade() != null) {
            if (this.compraFacade.validaAberturaCompra(super.getEntidade())) {
                super.getEntidade().abrirCompra();
                super.salvar();
                mensagem("Compra Aberta",
                        FacesMessage.SEVERITY_INFO);
            } else {
                mensagem("Existem titulos baixados dessa compra",
                        FacesMessage.SEVERITY_FATAL);
            }
        } else {
            mensagem("Nenhum elemento foi selecionado",
                    FacesMessage.SEVERITY_WARN);
        }
        return "list";
    }
    
    public void validaTipoPagamento(){
        if(getEntidade().getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)){
            getEntidade().setContasPagar(new ArrayList<ContasPagar>());
            getEntidade().setNrParcelas(0);
            getEntidade().setVrEntrada(getEntidade().getVrLiquido());
        }
    }

    public String fecharCompra() {
        try {
            getEntidade().fecharVenda();
            movimentaCaixa();
            super.salvar();
            return "list?faces-redirect=true";

        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), "");
            FacesContext
                    .getCurrentInstance().addMessage(null, message);

        }
        return null;
    }

    @Override
    public String salvar() {
        super.getEntidade().setContasPagar(new ArrayList<ContasPagar>());
        return super.salvar(); 
    }
    
    

    public void removeItem() {
        try {
            getEntidade().removerItem();
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), "");
            FacesContext
                    .getCurrentInstance().addMessage(null, message);
        }
    }

    @Override
    public void excluir() {
        if(super.getEntidade().getStatusFechado()){
            mensagem("Restrição: Abra a Venda para Excluir", 
                    FacesMessage.SEVERITY_ERROR);
        }
        else{
            super.excluir();
        }
    }
    
    

}
