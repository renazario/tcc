/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Funcao;
import br.com.marisamu.entidades.Funcionario;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.FuncionarioFacade;
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
public class FuncionarioControle extends AbstractControle<Funcionario> implements Serializable{

    @EJB
    private FuncionarioFacade funcionarioFacade;
    private Funcao funcao;
    
    public FuncionarioControle() {
        super(Funcionario.class);
    }

    @Override
    protected AbstractFacade<Funcionario> getFacade() {
        return funcionarioFacade;
    }
    
    @Override
    public String novo(){
        super.setEntidade(new Funcionario());
        super.getEntidade().setFuncionario(true);
        return "form?faces-redirect=true";
    }
    
    public Funcao[] getFuncoes(){
        return Funcao.values();
    }
}
