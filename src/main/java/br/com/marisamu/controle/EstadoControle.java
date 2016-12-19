/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Estado;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.EstadoFacade;
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
public class EstadoControle extends AbstractControle<Estado> implements Serializable {

    @EJB
    private EstadoFacade estadoFacade;

    public EstadoControle() {
        super(Estado.class);
    }

    @Override
    protected AbstractFacade<Estado> getFacade() {
        return estadoFacade;
    }


 }
