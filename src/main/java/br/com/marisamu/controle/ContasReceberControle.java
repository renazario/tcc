/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Caixa;
import br.com.marisamu.entidades.ContasReceber;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.CaixaFacade;
import br.com.marisamu.facade.ContasReceberFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class ContasReceberControle extends AbstractControle<ContasReceber> implements Serializable {

    @EJB
    private ContasReceberFacade crFacade;
    @EJB
    private CaixaFacade caixaFacade;
    private Date novaDataVencimento;

    public ContasReceberControle() {
        super(ContasReceber.class);
    }

    @Override
    protected AbstractFacade<ContasReceber> getFacade() {
        return crFacade;
    }

    public List<ContasReceber> getContasReceber() {
        return crFacade.contasReceber();
    }

    public List<ContasReceber> getContasRecebidas() {
        return crFacade.contasRecebidas();
    }

    private void mensagem(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void estornar() {
        if (getEntidade() != null) {
            crFacade.estornar(super.getEntidade());
        } else {
            mensagem("Nenhum elemento titulo foi selecionado para o estorno",
                    FacesMessage.SEVERITY_WARN);
        }

    }

    public void receberConta() {
        if (super.getEntidade().getVrParcela().compareTo(super.getEntidade().getVrRecebido()) > 0) {
            ContasReceber cr = new ContasReceber();
            cr.setDtEmissao(new Date());
            cr.setPago(false);
            cr.setNrParcela(super.getEntidade().getNrParcela());
            cr.setPessoa(super.getEntidade().getPessoa());
            cr.setVenda(super.getEntidade().getVenda());
            cr.setVrParcela(super.getEntidade().getVrParcela().subtract(super.getEntidade().getVrRecebido()));
            cr.setDtVencimento(novaDataVencimento);
            crFacade.salvar(cr);

            super.getEntidade().setVrParcela(super.getEntidade().getVrRecebido());
            super.getEntidade().setPago(true);
            super.getEntidade().setDtRecebimento(new Date());
            crFacade.salvar(super.getEntidade());
            
        
            movimentaCaixa();

        } else if (super.getEntidade().getVrParcela().compareTo(super.getEntidade().getVrRecebido()) < 0) {
            mensagem("Erro: Valor do recebimento é maior que o valor da parcela",
                    FacesMessage.SEVERITY_WARN);
        } else {
            super.getEntidade().setPago(true);
            super.getEntidade().setDtRecebimento(new Date());
            movimentaCaixa();

        }
    }

    public void movimentaCaixa() {
        Caixa c = new Caixa();
        c.setDtLancamento(new Date());
        c.setVrCredito(super.getEntidade().getVrRecebido());
        c = caixaFacade.salvar(c);

        super.getEntidade().setCaixa(c);

        crFacade.salvar(super.getEntidade());

        super.setEntidade(null);
    }

    public Date getNovaDataVencimento() {
        return novaDataVencimento;
    }

    public void setNovaDataVencimento(Date novaDataVencimento) {
        this.novaDataVencimento = novaDataVencimento;
    }

}
