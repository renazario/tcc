/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author renan
 */
@Entity
public class Caixa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "caixa_id")
    private Long id;
    @Column(name = "caixa_vrCredito")
    private BigDecimal vrCredito = BigDecimal.ZERO;
    @Column(name = "caixa_vrDebito")
    private BigDecimal vrDebito = BigDecimal.ZERO;
    @Column(name = "caixa_total_debito")
    private BigDecimal totalDebito = BigDecimal.ZERO;
    @Column(name = "caixa_total_credito")
    private BigDecimal totalCredito = BigDecimal.ZERO;
    @Column(name = "caixa_saldo")
    private BigDecimal saldo = BigDecimal.ZERO;
    @Column(name = "caixa_dtLancamento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtLancamento = new Date();
    @Column(name = "caixa_descricao")
    private String descricao;
    @Column(name = "caixa_tipo_credito")
    private Boolean credito;
    @Column(name = "caixa_tipo_debito")
    private Boolean debito;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVrCredito() {
        return vrCredito;
    }

    public void setVrCredito(BigDecimal vrCredito) {
        this.vrCredito = vrCredito;
    }

    public BigDecimal getVrDebito() {
        return vrDebito;
    }

    public void setVrDebito(BigDecimal vrDebito) {
        this.vrDebito = vrDebito;
    }

    public BigDecimal getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(BigDecimal totalDebito) {
        this.totalDebito = totalDebito;
    }

    public BigDecimal getTotalCredito() {
        return totalCredito;
    }

    public void setTotalCredito(BigDecimal totalCredito) {
        this.totalCredito = totalCredito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDtLancamento() {
        return dtLancamento;
    }

    public void setDtLancamento(Date dtLancamento) {
        this.dtLancamento = dtLancamento;
    }

    public Boolean getCredito() {
        return credito;
    }

    public void setCredito(Boolean credito) {
        this.credito = credito;
    }

    public Boolean getDebito() {
        return debito;
    }

    public void setDebito(Boolean debito) {
        this.debito = debito;
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
        if (!(object instanceof Caixa)) {
            return false;
        }
        Caixa other = (Caixa) object;
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
