/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author renan
 */
@Entity
public class ItemCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column (name = "item_compra_vrUnitario")
    private BigDecimal vrUnitario;
    @Column (name = "item_compra_quantidade")
    private BigDecimal quantidade;
    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn (name = "produto_id")
    private Produto produto;
    @ManyToOne
    @JoinColumn (name = "compra_id")
    private Compra compra;
    
    public void setValorProdutoSelecionado(){
        this.setVrUnitario(this.getProduto().getPrecoCusto());
    }
    
    public BigDecimal getCalculaItens(){
//        this.vrUnitario = produto.getPrecoCusto();
        return vrUnitario.multiply(quantidade);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVrUnitario() {
        return vrUnitario;
    }

    public void setVrUnitario(BigDecimal vrUnitario) {
        this.vrUnitario = vrUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemCompra)) {
            return false;
        }
        ItemCompra other = (ItemCompra) object;
        if ((this.produto == null && other.produto != null) || (this.produto != null && !this.produto.equals(other.produto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
}
