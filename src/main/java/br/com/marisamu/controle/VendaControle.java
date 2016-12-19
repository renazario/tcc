/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Caixa;
import br.com.marisamu.entidades.ContasReceber;
import br.com.marisamu.entidades.TipoPagamento;
import br.com.marisamu.entidades.Venda;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.CaixaFacade;
import br.com.marisamu.facade.ContasReceberFacade;
import br.com.marisamu.facade.VendaFacade;
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
public class VendaControle extends AbstractControle<Venda> implements Serializable {

    @EJB
    private VendaFacade vendaFacade;
    @EJB
    private CaixaFacade caixaFacade;
    @EJB
    private ContasReceberFacade crFacade;

    public VendaControle() {
        super(Venda.class);
    }
    
    public void movimentaContasRecebidas(){
        if(super.getEntidade().getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)){
            ContasReceber cr = new ContasReceber();
            cr.setDtRecebimento(super.getEntidade().getDtVenda());
            cr.setVrRecebido(super.getEntidade().getTotalVenda());
            cr.setPessoa(super.getEntidade().getPessoa());
            cr.setVenda(super.getEntidade());
           
            cr = crFacade.salvar(cr);
            
        }
    }

    public void movimentaCaixa(){
        if(super.getEntidade().getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)){
            Caixa c = new Caixa();
            c.setDtLancamento(super.getEntidade().getDtVenda());
            c.setVrCredito(super.getEntidade().getTotalVenda());
            c.setDescricao("Referente a venda: "+ super.getEntidade().getId());
            
            c = caixaFacade.salvar(c);
            
        }else{
            Caixa c = new Caixa();
            c.setDtLancamento(super.getEntidade().getDtVenda());
            c.setVrCredito(super.getEntidade().getValorEntrada());
            
            c=caixaFacade.salvar(c);
        }
    }
    
    public String carregaVenda() throws Exception {
        if (super.getEntidade() != null) {
            if (super.getEntidade().getStatusFechado()) {
                mensagem("Restrição: Abra a Venda para Alterar",
                        FacesMessage.SEVERITY_WARN);
                return "list";
            }
            vendaFacade.carregaVenda(super.getEntidade());
            super.getEntidade().recalcularParcelas();
            return "form?faces-redirect=true";
        } else {
            mensagem("Nenhum elemento foi selecionado para a alteração!",
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
            FacesContext
                    .getCurrentInstance().addMessage(null, message);
        }
    }

    public String abrirVenda() {
        if (getEntidade() != null) {
            if (this.vendaFacade.validaAberturaVenda(super.getEntidade())) {
                super.getEntidade().abrirVenda();
                super.salvar();
                mensagem("Venda Aberta",
                        FacesMessage.SEVERITY_INFO);
            }
            else{
                mensagem("Existem titulos baixados dessa venda!",
                        FacesMessage.SEVERITY_FATAL);
            }
        } else {
            mensagem("Nenhum elemento foi selecionado",
                    FacesMessage.SEVERITY_WARN);
        }
        return "list";
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

    private void mensagem(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    protected AbstractFacade<Venda> getFacade() {
        return vendaFacade;
    }

    @Override
    public String salvar() {

//        if (super.getEntidade().getValorLiquido().compareTo(new BigDecimal("0.00")) > 0) {
//            super.getEntidade().getPessoa().setDtUltimaCompra(new Date());
//        } else if (super.getEntidade().getValorLiquido().compareTo(new BigDecimal("0.00")) < 0) {
//            mensagem("Não é permitido salvar venda com valor negativo!",
//                    FacesMessage.SEVERITY_ERROR);
//            return null;
//        }
//        if (super.getEntidade().getPlanoPagamento().getTipoPagamento() == TipoPagamento.AVISTA) {
//            return super.salvar();
//        }
//        if (super.getEntidade().getContasReceber() == null || super.getEntidade().getContasReceber().isEmpty()) {
//            return "form?faces-redirect=true";
//        }
//        for (ItemVenda itensVenda : super.getEntidade().getItensVenda()) {
//            itensVenda.baixaEstoque();
//        }
        super.getEntidade().setContasReceber(new ArrayList<ContasReceber>());
        return super.salvar();

    }

    public void validaTipoPagamento() {
        if (getEntidade().getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.AVISTA)) {
            getEntidade().setContasReceber(new ArrayList<ContasReceber>());
            getEntidade().setNrParcelas(0);
            getEntidade().setValorEntrada(getEntidade().getValorLiquido());
        }
    }

    public String fecharVenda() {
        try {
            getEntidade().fecharVenda();
            movimentaCaixa();
            movimentaContasRecebidas();
            super.salvar();
            return "list?faces-redirect=true";

//            else {
//                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        "Total das parcelas diverge do total liquido", "");
//                FacesContext
//                        .getCurrentInstance().addMessage(null, message);
//                return null;
//            }
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), "");
            FacesContext
                    .getCurrentInstance().addMessage(null, message);
        }
        return null;
    }

    @Override
    public void excluir() {
        if (super.getEntidade().getStatusFechado()) {
            mensagem("Restrição: Abra a Venda para Excluir", FacesMessage.SEVERITY_WARN);
        } else {
            super.excluir();
        }

    }

}
