/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Grupo;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.GrupoFacade;
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
public class GrupoControle extends AbstractControle<Grupo> implements Serializable{

    @EJB
    private GrupoFacade grupoFacade;

    public GrupoControle() {
        super(Grupo.class);
    }
    @Override
    protected AbstractFacade<Grupo> getFacade() {
        return grupoFacade;
    }
    
}
