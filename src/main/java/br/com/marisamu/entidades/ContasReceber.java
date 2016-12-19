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
public class ContasReceber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contas_receber_id")
    private Long id;
    @Column(name = "contas_receber_dtVencimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtVencimento;
    @Column(name = "contas_receber_dtRecebimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtRecebimento;
    @Column(name = "contas_receber_dtEmissao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtEmissao = new Date();
    @Column(name = "contas_receber_vrParcela")
    private BigDecimal vrParcela;
    @Column(name = "contas_receber_vrRecebido")
    private BigDecimal vrRecebido;
    @Column(name = "contas_receber_nrParcela")
    private String nrParcela;
    @Column(name = "contas_receber_justificativa")
    private String justificativa;
    @Column(name = "contas_receber_pago")
    private Boolean pago = Boolean.FALSE;
    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Venda venda;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    public ContasReceber() {
    }

    public ContasReceber(Date dtVencimento, BigDecimal vrParcela, Venda venda, Pessoa pessoa) {
        this.dtVencimento = dtVencimento;
        this.vrParcela = vrParcela;
        this.venda = venda;
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

    public Date getDtRecebimento() {
        return dtRecebimento;
    }

    public void setDtRecebimento(Date dtRecebimento) {
        this.dtRecebimento = dtRecebimento;
    }

    public BigDecimal getVrParcela() {
        return vrParcela;
    }

    public void setVrParcela(BigDecimal vrParcela) {
        this.vrParcela = vrParcela;
    }

    public BigDecimal getVrRecebido() {
        return vrRecebido;
    }

    public void setVrRecebido(BigDecimal vrRecebido) {
        this.vrRecebido = vrRecebido;
    }
    
    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }


    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContasReceber)) {
            return false;
        }
        ContasReceber other = (ContasReceber) object;
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
