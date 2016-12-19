/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Estado;
import br.com.marisamu.entidades.PessoaJuridica;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.FornecedorFacade;
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
public class FornecedorControle extends AbstractControle<PessoaJuridica> implements Serializable {

    @EJB
    private FornecedorFacade fornecedorFacade;
    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public FornecedorControle() {
        super(PessoaJuridica.class);
    }

    @Override
    protected AbstractFacade<PessoaJuridica> getFacade() {
        return fornecedorFacade;
    }

    @Override
    public String novo() {
        super.setEntidade(new PessoaJuridica());
        super.getEntidade().setFornecedor(true);
        return "form?faces-redirect=true";
    }

    

}
