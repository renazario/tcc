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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author renan
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "pessoa_id")
    private Long id;
    @Column (name = "pessoa_nome")
    private String nome;
    @Column (name = "pessoa_endereco")
    private String endereco;
    @Column (name = "pessoa_telefone_residencial")
    private String telefoneRes;
    @Column (name = "pessoa_telefone_comercial")
    private String telefoneCel;
    @Column (name = "pessoa_contato")
    private String contato;
    @Column (name = "pessoa_endereco_comercial")
    private String enderecoComercial;
    @Column (name = "pessoa_dt_cadastro")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtCadastro = new Date();
    @Column (name = "pessoa_dt_ultima_compra")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtUltimaCompra;
    @Column (name = "pessoa_dt_primeira_compra")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtPrimeiraCompra;
    @Column (name = "pessoa_limite_credito")
    private BigDecimal limiteCredito = BigDecimal.ZERO;
    @Column (name = "pessoa_ativo")
    private Boolean ativo = Boolean.TRUE;
    @Column (name = "pessoa_pendencia")
    private Boolean pendencia;
    @ManyToOne
    @JoinColumn (name = "cidade_id")
    private Cidade cidade;
    
    private boolean fornecedor = false;
    private boolean funcionario = false;
    
    public abstract String getDocumentoFederal();
    public abstract String getDocumentoEstadual();
    public abstract String getNomes();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public boolean isFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean isFuncionario() {
        return funcionario;
    }

    public void setFuncionario(boolean funcionario) {
        this.funcionario = funcionario;
    }
    
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefoneRes() {
        return telefoneRes;
    }

    public void setTelefoneRes(String telefoneRes) {
        this.telefoneRes = telefoneRes;
    }

    public String getTelefoneCel() {
        return telefoneCel;
    }

    public void setTelefoneCel(String telefoneCel) {
        this.telefoneCel = telefoneCel;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEnderecoComercial() {
        return enderecoComercial;
    }

    public void setEnderecoComercial(String enderecoComercial) {
        this.enderecoComercial = enderecoComercial;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Date getDtUltimaCompra() {
        return dtUltimaCompra;
    }

    public void setDtUltimaCompra(Date dtUltimaCompra) {
        this.dtUltimaCompra = dtUltimaCompra;
    }

    public Date getDtPrimeiraCompra() {
        return dtPrimeiraCompra;
    }

    public void setDtPrimeiraCompra(Date dtPrimeiraCompra) {
        this.dtPrimeiraCompra = dtPrimeiraCompra;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getPendencia() {
        return pendencia;
    }

    public void setPendencia(Boolean pendencia) {
        this.pendencia = pendencia;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
        if (!(object instanceof Pessoa)) {
            return false;
        }
        Pessoa other = (Pessoa) object;
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
