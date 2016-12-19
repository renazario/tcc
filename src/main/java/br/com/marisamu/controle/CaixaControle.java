/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Caixa;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.CaixaFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author renan
 */
@ManagedBean
@SessionScoped
public class CaixaControle extends AbstractControle<Caixa> implements Serializable {

    @EJB
    private CaixaFacade caixaFacade;

    public CaixaControle() {
        super(Caixa.class);
    }

    @Override
    protected AbstractFacade<Caixa> getFacade() {
        return caixaFacade;
    }

    public void incluir() {
        super.setEntidade(new Caixa());
    }

}
