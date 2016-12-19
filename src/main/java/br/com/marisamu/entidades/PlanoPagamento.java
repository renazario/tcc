/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author renan
 */
@Entity
public class PlanoPagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "planoPagamento_id")
    private Long id;
    @Column(name = "planoPagamento_descricao")
    private String descricao;
    @Column(name = "planoPagamento_qtdMaxParc")
    private Integer qtdMaxParc = 0;
    @Column(name = "planoPagamento_entrada")
    private Boolean entrada = Boolean.FALSE;
    @Enumerated
    @Column(name = "tipoPagamento_id")
    private TipoPagamento tipoPagamento = TipoPagamento.AVISTA;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdMaxParc() {
        return qtdMaxParc;
    }

    public void setQtdMaxParc(Integer qtdMaxParc) {
        if(tipoPagamento == TipoPagamento.APRAZO){
            this.qtdMaxParc = qtdMaxParc;
        }
        else{
            this.qtdMaxParc = 0;
        }
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
//        if(tipoPagamento == TipoPagamento.APRAZO){
            this.entrada = entrada;
//        }
//        else{
//            this.entrada = false;
//        }
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
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
        if (!(object instanceof PlanoPagamento)) {
            return false;
        }
        PlanoPagamento other = (PlanoPagamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
