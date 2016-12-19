/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author renan
 */
@Entity
public class PessoaFisica extends Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "pf_rg")
    private String rg;
    @CPF(message = "CPF invalido")
    @Column(name = "pf_cpf")
    private String cpf;
    @Column(name = "pf_sexo")
    private String sexo;
    @Column(name = "pf_dt_nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dt_nascimento;

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
      
            this.cpf = cpf;
       
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(Date dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    @Override
    public String getDocumentoFederal() {
        return cpf;
    }

    @Override
    public String getDocumentoEstadual() {
        return rg;
    }

    @Override
    public String getNomes() {
        return getNome();
    }

}
