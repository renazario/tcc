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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author renan
 */
@Entity
public class ContasPagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "contas_pagar_id")
    private Long id;
    @Column(name = "contas_pagar_dtVencimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "contas_pagar_dtEmissao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtEmissao = new Date();
    @Column(name = "contas_pagar_dtPagamento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtPagamento = new Date();
    @Column (name = "contas_pagar_vrParcela")
    private BigDecimal vrParcela;
    @Column (name = "contas_pagar_vrPago")
    private BigDecimal vrPago;
    @Column (name = "contas_pagar_pago")
    private Boolean pago = Boolean.FALSE;
    @Column(name = "contas_pagar_nrParcela")
    private String nrParcela;
    @Column(name = "contas_pagar_justificativa")
    private String justificativa;
    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    public ContasPagar() {
    }

    public ContasPagar(Date dtVencimento, BigDecimal vrParcela, Compra compra, Pessoa pessoa) {
        this.dtVencimento = dtVencimento;
        this.vrParcela = vrParcela;
        this.compra = compra;
        this.pessoa = pessoa;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Date getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(Date dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }
    
    public BigDecimal getVrParcela() {
        return vrParcela;
    }

    public void setVrParcela(BigDecimal vrParcela) {
        this.vrParcela = vrParcela;
    }

    public Date getDtPagamento() {
        return dtPagamento;
    }

    public void setDtPagamento(Date dtPagamento) {
        this.dtPagamento = dtPagamento;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public String getNrParcela() {
        return nrParcela;
    }

    public void setNrParcela(String nrParcela) {
        this.nrParcela = nrParcela;
    }

    public BigDecimal getVrPago() {
        return vrPago;
    }

    public void setVrPago(BigDecimal vrPago) {
        this.vrPago = vrPago;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
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
        if (!(object instanceof ContasPagar)) {
            return false;
        }
        ContasPagar other = (ContasPagar) object;
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
