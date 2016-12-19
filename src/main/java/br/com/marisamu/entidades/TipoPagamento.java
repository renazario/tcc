/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

/**
 *
 * @author renan
 */
public enum TipoPagamento {
    
    AVISTA("A vista"),
    APRAZO("A Prazo");
    
    private final String descricao;

    private TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
