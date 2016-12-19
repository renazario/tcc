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
import javax.persistence.Enumerated;
import javax.persistence.Temporal;

/**
 *
 * @author renan
 */
@Entity
public class Funcionario extends PessoaFisica implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "funcionario_salario")
    private BigDecimal salario;
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column (name = "funcionario_dtAdmissao")
    private Date dtAdmissao = new Date();
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column (name = "funcionario_dtDemissao")
    private Date dtDemissao;
    @Enumerated
    @Column (name = "funcao_id")
    private Funcao funcao;

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Date getDtAdmissao() {
        return dtAdmissao;
    }

    public void setDtAdmissao(Date dtAdmissao) {
        this.dtAdmissao = dtAdmissao;
    }

    public Date getDtDemissao() {
        return dtDemissao;
    }

    public void setDtDemissao(Date dtDemissao) {
        this.dtDemissao = dtDemissao;
    }
    
}
