/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Estado;
import br.com.marisamu.entidades.Pessoa;
import br.com.marisamu.entidades.PessoaFisica;
import br.com.marisamu.entidades.PessoaJuridica;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.PessoaFacade;
import java.io.Serializable;
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
public class PessoaControle extends AbstractControle<Pessoa> implements Serializable {

    @EJB
    private PessoaFacade pessoaFacade;
    private String tipoPessoa;
    private List<Pessoa> listaPessoa;
    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public PessoaControle() {
        super(Pessoa.class);
    }

    @Override
    protected AbstractFacade<Pessoa> getFacade() {
        return pessoaFacade;
    }

    @Override
    public String novo() {
        tipoPessoa = "PF";
        setEntidade(new PessoaFisica());
        return "form?faces-redirect=true";
    }

    @Override
    public void setEntidade(Pessoa pessoa) {
        if (pessoa instanceof PessoaFisica) {
            tipoPessoa = "PF";
        } else {
            tipoPessoa = "PJ";
        }
        super.setEntidade(pessoa);
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        if (tipoPessoa.equals("PF")) {
            setEntidade(new PessoaFisica());
        } else {
            setEntidade(new PessoaJuridica());
        }
        this.tipoPessoa = tipoPessoa;
    }

    
}
