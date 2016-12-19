/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author renan
 */
@Entity
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "compra_dtCompra")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtCompra = new Date();
    @Column(name = "compra_vrBruto")
    private BigDecimal vrBruto = BigDecimal.ZERO;
    @Column(name = "compra_vrLiquido")
    private BigDecimal vrLiquido = BigDecimal.ZERO;
    @Column(name = "compra_vrEntrada")
    private BigDecimal vrEntrada = BigDecimal.ZERO;
    @Column(name = "compra_totalCompra")
    private BigDecimal totalCompra = BigDecimal.ZERO;
    @Column(name = "compra_desconto")
    private BigDecimal desconto = BigDecimal.ZERO;
    @Column(name = "compra_nrParcelas")
    private Integer nrParcelas;
    @Column(name = "compra_status_fechado")
    private Boolean statusFechado = false;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @ManyToOne
    @JoinColumn(name = "planoPagamento_id")
    private PlanoPagamento planoPagamento;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "compra",
            orphanRemoval = true)
    private List<ItemCompra> itensCompra = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "compra",
            orphanRemoval = true)
    private List<ContasPagar> contasPagar;
    @Transient
    private ItemCompra item = new ItemCompra();

    public void recalcular() {
        vrBruto = BigDecimal.ZERO;
        for (ItemCompra ic : itensCompra) {
            vrBruto = vrBruto.add(ic.getVrUnitario()
                    .multiply(ic.getQuantidade()));
        }
    }

    public void calculaTotais() {
        calculaTotalCompra();
        calculaTotalLiquido();
    }

    public void calculaTotalLiquido() {
        vrLiquido = this.getVrBruto().subtract(this.desconto);
    }

    public void calculaTotalCompra() {
        totalCompra = this.getVrLiquido().subtract(this.vrEntrada);
    }

    public void SubTotal() {
        BigDecimal vrBruto = BigDecimal.ZERO;
        for (ItemCompra i : this.getItensCompra()) {
            vrBruto = vrBruto.add(i.getCalculaItens());
        }
    }

    public void verificaQuantidadeValida() throws Exception {
        if (item.getQuantidade() == null || item.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Quantidade inválida");
        }
    }

    public void addItem() throws Exception {
        if (!itensCompra.contains(item)) {
            this.verificaQuantidadeValida();
            item.setCompra(this);
//            item.setVrUnitario(item.getProduto().getPrecoCusto());
            itensCompra.add(item);
            item = new ItemCompra();
            recalcular();
            calculaTotais();
            SubTotal();
        } else {
            throw new Exception("Produto já foi adicionado na lista");
        }
    }

    public void recalcularParcelas() {
        contasPagar = new ArrayList<>();
        if (contasPagar.isEmpty()) {
            for (int i = 0; i < nrParcelas; i++) {
                Calendar vencimento = Calendar.getInstance();
                vencimento.add(Calendar.DAY_OF_MONTH, 30 * i);
                BigDecimal vrParc = totalCompra.divide(new BigDecimal(nrParcelas));
                ContasPagar cp = new ContasPagar(vencimento.getTime(), vrParc, this, pessoa);
                cp.setNrParcela(i + "/" + nrParcelas);
                contasPagar.add(cp);
            }
        }
        if (itensCompra.isEmpty()) {
            contasPagar = null;
            this.setDesconto(new BigDecimal("0.00"));
            this.setTotalCompra(new BigDecimal("0.00"));
            this.setVrEntrada(new BigDecimal("0.00"));
            nrParcelas = 0;
        }
    }

    public void removerItem() throws Exception {
        if (itensCompra.contains(item)) {
            itensCompra.remove(item);
            item = new ItemCompra();
            recalcular();
            recalcularParcelas();
            calculaTotais();
        } else {
            throw new Exception("Produto removido com sucesso");
        }
    }

    public boolean validaValores() {
        if (planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO)) {
            BigDecimal total = BigDecimal.ZERO;
            for (ContasPagar c : contasPagar) {
                total = total.add(c.getVrParcela());
            }
            return total.compareTo(totalCompra) == 0;
        } else {
            return true;
        }
    }

    public void fecharVenda() throws Exception {
        if (this.getItensCompra().isEmpty() || this.getItensCompra() == null) {
            throw new Exception("Não é possivel fechar compra vazia!");
        }
        if (this.getVrLiquido().compareTo(new BigDecimal("0.00")) <= 0) {
            throw new Exception("Não é possivel fechar compra com valor menor ou igual a zero");
        }
        if (this.getVrLiquido().compareTo(new BigDecimal("0.00")) > 0) {
            this.getPessoa().setDtUltimaCompra(new Date());
        }
        if (this.getPessoa().getDtPrimeiraCompra() == null) {
            this.getPessoa().setDtPrimeiraCompra(new Date());
        }
        if (this.getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.APRAZO) && contasPagar.isEmpty()) {
            throw new Exception("Não é possivel fechar compra a Prazo sem gerar as parcelas");
        }
        if (!validaValores()) {
            throw new Exception("Valor total das parcelas difere do total da compra");
        } else {
            this.statusFechado = true;
        }
    }

    public void abrirCompra() {
        this.setContasPagar(new ArrayList<ContasPagar>());
        this.statusFechado = false;
    }

    public void parcelar() throws Exception {
        if (nrParcelas != null && planoPagamento != null && planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO)) {
            if (verificaEntrada() && nrParcelas.compareTo(planoPagamento.getQtdMaxParc()) <= 0) {
                if (totalCompra.compareTo(new BigDecimal("0.00")) <= 0 && planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO) && !itensCompra.isEmpty()) {
                    contasPagar = null;
                    throw new Exception("Não é possivel parcelas: total da compra menor ou igual a zero!");
                }
                recalcular();
                calculaTotais();
                contasPagar = new ArrayList<>();
                for (int i = 0; i < nrParcelas; i++) {
                    Calendar vencimento = Calendar.getInstance();
                    vencimento.add(Calendar.DAY_OF_MONTH, 30 * i);
                    BigDecimal vrParc = totalCompra.divide(new BigDecimal(nrParcelas));
                    ContasPagar cp = new ContasPagar(vencimento.getTime(), vrParc, this, pessoa);
                    contasPagar.add(cp);
                }
            }
            if (nrParcelas.compareTo(planoPagamento.getQtdMaxParc()) > 0 || nrParcelas < 0) {
                throw new Exception("Quantidade de Parcelas Invalidas");
            }
        }
        if(planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO) && nrParcelas == null){
            throw new Exception("Informe a quantidade de Parcelas");
        }

    }

    public boolean verificaEntrada() throws Exception {
        if (pessoa != null) {
            if (!planoPagamento.getEntrada() || vrEntrada.compareTo(new BigDecimal("0.00")) > 0) {
                return true;
            }
            throw new Exception("Plano de pagamento exige entrada!");
        } else if (verificaValorEntrada()) {
            return true;
        }
        return false;
    }

    public boolean verificaValorEntrada() throws Exception {
        if (vrEntrada.compareTo(new BigDecimal("0.00")) > 0 && vrEntrada.compareTo(vrLiquido) <= 0) {
            return true;
        }
        throw new Exception("Valor de entrada Invalido");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDtCompra() {
        return dtCompra;
    }

    public void setDtCompra(Date dtCompra) {
        this.dtCompra = dtCompra;
    }

    public BigDecimal getVrBruto() {
        return vrBruto;
    }

    public void setVrBruto(BigDecimal vrBruto) {
        this.vrBruto = vrBruto;
    }

    public BigDecimal getVrLiquido() {
        return vrLiquido;
    }

    public void setVrLiquido(BigDecimal vrLiquido) {
        this.vrLiquido = vrLiquido;
    }

    public BigDecimal getVrEntrada() {
        return vrEntrada;
    }

    public void setVrEntrada(BigDecimal vrEntrada) {
        this.vrEntrada = vrEntrada;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Integer getNrParcelas() {
        return nrParcelas;
    }

    public void setNrParcelas(Integer nrParcelas) {
        this.nrParcelas = nrParcelas;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public PlanoPagamento getPlanoPagamento() {
        return planoPagamento;
    }

    public void setPlanoPagamento(PlanoPagamento planoPagamento) {
        this.planoPagamento = planoPagamento;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public List<ContasPagar> getContasPagar() {
        return contasPagar;
    }

    public void setContasPagar(List<ContasPagar> contasPagar) {
        this.contasPagar = contasPagar;
    }

    public ItemCompra getItem() {
        return item;
    }

    public void setItem(ItemCompra item) {
        this.item = item;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Boolean getStatusFechado() {
        return statusFechado;
    }

    public void setStatusFechado(Boolean statusFechado) {
        this.statusFechado = statusFechado;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
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
