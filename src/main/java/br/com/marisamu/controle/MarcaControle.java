/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Marca;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.MarcaFacade;
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
public class MarcaControle extends AbstractControle<Marca> implements Serializable{

    @EJB
    private MarcaFacade marcaFacade;

    public MarcaControle() {
        super(Marca.class);
    }
    @Override
    protected AbstractFacade<Marca> getFacade() {
        return marcaFacade;
    }
    
}
