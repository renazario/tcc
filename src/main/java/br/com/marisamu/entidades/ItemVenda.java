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
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "iv_vrUnitario")
    private BigDecimal vrUnitario;
    @Column(name = "iv_quantidade")
    private BigDecimal quantidade;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;

//    public void verificaEstoque() throws Exception {
//        if (produto.getEstoque().compareTo(quantidade) >= 0) {
//            produto.AtualizaEstoque(quantidade.multiply(new BigDecimal("-1")));
//        } 
//    }
    public void verificaEstoqueProduto() throws Exception {
        if(produto.getEstoque().compareTo(quantidade)<0){
            throw new Exception("Quantidade indisponivel no estoque, o estoque do produto " 
                    + produto.getNome() + " é de " + produto.getEstoque());
        }
    }
    
    public void baixaEstoque(){
        produto.AtualizaEstoque(quantidade.multiply(new BigDecimal("-1")));
    }
    
    public BigDecimal getCalculaItens() {
//        this.vrUnitario = produto.getPrecoVenda();
        return vrUnitario.multiply(quantidade);
    }

    public void setValorProdutoSelecionado() {
        if(this.getProduto() != null){
            this.setVrUnitario(this.getProduto().getPrecoVenda());
        }
        else{
            this.setVrUnitario(null);
            this.setQuantidade(null);
        }
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

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produto != null ? produto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemVenda)) {
            return false;
        }
        ItemVenda other = (ItemVenda) object;
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
