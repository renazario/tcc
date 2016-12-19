/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Cidade;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.CidadeFacade;
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
public class CidadeControle extends AbstractControle<Cidade> implements Serializable {

    @EJB
    private CidadeFacade cidadeFacade;

    public CidadeControle() {
        super(Cidade.class);
    }

    @Override
    protected AbstractFacade<Cidade> getFacade() {
        return cidadeFacade;
    }
   
}
