/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.controle;

import br.com.marisamu.entidades.Produto;
import br.com.marisamu.facade.AbstractFacade;
import br.com.marisamu.facade.ProdutoFacade;
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
public class ProdutoControle extends AbstractControle<Produto> implements Serializable{

    @EJB
    private ProdutoFacade produtoFacade;
    
    public ProdutoControle(){
        super(Produto.class);
    } 
    @Override
    protected AbstractFacade<Produto> getFacade() {
       return produtoFacade;
    }
    
}
