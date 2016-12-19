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

/**
 *
 * @author renan
 */
@Entity
public class PessoaJuridica extends Pessoa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Column (name = "pj_nome_fantasia")
    private String nomeFantasia;
    @Column (name = "pj_cnpj")
    private String cnpj;
    @Column (name = "pj_ie")
    private String ie;
    @Column (name = "pj_razaoSocial")
    private String razaoSocial;


    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getDocumentoFederal() {
        return cnpj;
    }

    @Override
    public String getDocumentoEstadual() {
        return ie;
    }    

    @Override
    public String getNomes() {
        return getNome();
    }

}
