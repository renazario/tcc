/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Usuario;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.FuncionarioFacade;
import br.com.marisamu.facade.UsuarioFacade;
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
public class UsuarioControle extends AbstractControle<Usuario> implements Serializable{

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private FuncionarioFacade funcionarioFacade;

    public UsuarioControle() {
        super(Usuario.class);
    }
    @Override
    protected AbstractFacade<Usuario> getFacade() {
        return usuarioFacade;
    }
    
}
