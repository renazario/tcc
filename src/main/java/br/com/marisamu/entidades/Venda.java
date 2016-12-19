/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.marisamu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "venda_dt_venda")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtVenda = new Date();
    @Column(name = "venda_valor_bruto")
    private BigDecimal valorBruto = BigDecimal.ZERO;
    @Column(name = "venda_valor_liquido")
    private BigDecimal valorLiquido = BigDecimal.ZERO;
    @Column(name = "venda_desconto")
    private BigDecimal desconto = BigDecimal.ZERO;
    @Column(name = "venda_valor_entrada")
    private BigDecimal valorEntrada = BigDecimal.ZERO;
    @Column(name = "venda_total_venda")
    private BigDecimal totalVenda = BigDecimal.ZERO;
    @Column(name = "venda_nrParcelas")
    private Integer nrParcelas;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    @Column(name = "venda_status_fechado")
    private Boolean statusFechado = false;
    @ManyToOne
    @JoinColumn(name = "planoPagamento_id")
    private PlanoPagamento planoPagamento;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "venda",
            orphanRemoval = true)
    private List<ItemVenda> itensVenda = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "venda",
            orphanRemoval = true)
    private List<ContasReceber> contasReceber;
    @Transient
    private ItemVenda item = new ItemVenda();

    public void recalcular() {
        valorBruto = BigDecimal.ZERO;
        for (ItemVenda iv : itensVenda) {
            valorBruto = valorBruto.add(iv.getVrUnitario()
                    .multiply(iv.getQuantidade()));
        }
    }

    public void calculaTotais() {
        calculaTotalLiquido();
        calculaTotalVenda();
    }

    public void calculaTotalLiquido() {
        valorLiquido = this.getValorBruto().subtract(this.desconto);
    }

    public void calculaTotalVenda() {
        totalVenda = this.getValorLiquido().subtract(this.valorEntrada);
    }

    public void Subtotal() {
        BigDecimal valorBruto = BigDecimal.ZERO;
        for (ItemVenda i : this.getItensVenda()) {
            valorBruto = valorBruto.add(i.getCalculaItens());
        }

    }

    public void verificaQuantidadeValida() throws Exception {
        if (item.getQuantidade() == null || item.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Quantidade inválida!");
        }
    }

    public void addItem() throws Exception {
        if (!itensVenda.contains(item)) {
            this.verificaQuantidadeValida();
            item.verificaEstoqueProduto();
            item.setVenda(this);
//            item.setVrUnitario(item.getProduto().getPrecoVenda());
            itensVenda.add(item);
            item = new ItemVenda();
            recalcular();
            calculaTotais();
            Subtotal();
        } else {
            throw new Exception("Produto Já foi adicionado na lista de itens");
        }
    }

    public void recalcularParcelas() {
        contasReceber = new ArrayList<>();
        if (contasReceber.isEmpty()) {
            for (int i = 1; i <= nrParcelas; i++) {
                Calendar vencimento = Calendar.getInstance();
                vencimento.add(Calendar.DAY_OF_MONTH, 30 * i);
                BigDecimal vrParc = totalVenda.divide(new BigDecimal(nrParcelas), RoundingMode.CEILING);
                ContasReceber cr = new ContasReceber(vencimento.getTime(), vrParc, this, pessoa);
                cr.setNrParcela(i + "/" + nrParcelas);
                contasReceber.add(cr);
            }
        }
        if (itensVenda.isEmpty()) {
            contasReceber = null;
            this.setValorEntrada(new BigDecimal("0.00"));
            this.setValorLiquido(new BigDecimal("0.00"));
            this.setTotalVenda(new BigDecimal("0.00"));
            nrParcelas = 0;
        }
    }

    public void removerItem() throws Exception {
        if (itensVenda.contains(item)) {
            itensVenda.remove(item);
            item = new ItemVenda();
            recalcular();
            calculaTotais();
            recalcularParcelas();
//            if (itensVenda.isEmpty()) {
//                valorEntrada = BigDecimal.ZERO;
//            }
        } else {
            throw new Exception("Produto foi removido com sucesso!");
        }
    }

//    public void gerarContasReceber() {
//        contasReceber = new ArrayList<>();
//        for (VendaParcelas vendaParcelas : ) {
//            ContasReceber cr = new ContasReceber(vendaParcelas.getDtVencimento(), vendaParcelas.getVrParcelas(), this, pessoa);
//            contasReceber.add(cr);
//        }
//    }
    public boolean validaValores() {
        if (planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO)) {
            BigDecimal total = BigDecimal.ZERO;
//            total = valorEntrada.add(total);
            for (ContasReceber i : contasReceber) {
                total = total.add(i.getVrParcela());
            }
            return total.compareTo(totalVenda) == 0;
        } else {
            return true;
        }
    }

    public void fecharVenda() throws Exception {
        if (this.getItensVenda().isEmpty() || this.getItensVenda() == null) {
            throw new Exception("Não é permitido fechar venda Vazia");
        }
        if (this.getValorLiquido().compareTo(new BigDecimal("0.00")) <= 0) {
            throw new Exception("Não é permitido fechar a venda com valor menor ou igual a zero!");
        }
        if (this.getValorLiquido().compareTo(new BigDecimal("0.00")) > 0) {
            this.getPessoa().setDtUltimaCompra(new Date());
        }
        if (this.getPlanoPagamento().getTipoPagamento().equals(TipoPagamento.APRAZO) && contasReceber.isEmpty()) {
            throw new Exception("Não é possivel fechar venda a PRAZO sem gerar as parcelas");
        }
        if (!validaValores()) {
            throw new Exception("Valor total das parcelas difere do total da venda");
        }else{
             this.statusFechado = true;
        }
////        if (this.statusFechado.equals(false)) {
////            gerarContasReceber();
////            parcelar();
//            this.statusFechado = true;
////        }

    }

    public void abrirVenda() {
        this.setContasReceber(new ArrayList<ContasReceber>());
        this.statusFechado = false;
    }

    public void parcelar() throws Exception {
        if (nrParcelas != null && planoPagamento != null && planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO)) {
            if (verificaLimete() && verificaEntrada() && nrParcelas.compareTo(planoPagamento.getQtdMaxParc()) <= 0) {
//                if (valorEntrada.compareTo(valorBruto) >= 0) {
//                    throw new Exception("Valor de Entrada não pode ser maior ou igual o total bruto!");
//                }
                if (totalVenda.compareTo(new BigDecimal("0.00")) <= 0 && planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO) && !itensVenda.isEmpty()) {
                    contasReceber = null;
                    throw new Exception("Não é possivel parcelar: Total da Venda menor ou igual a zero!");
                }
                recalcular();
                calculaTotais();
                contasReceber = new ArrayList<>();
                for (int i = 1; i <= nrParcelas; i++) {
                    Calendar vencimento = Calendar.getInstance();
                    vencimento.add(Calendar.DAY_OF_MONTH, 30 * i);
                    BigDecimal vrParc = totalVenda.divide(new BigDecimal(nrParcelas), RoundingMode.CEILING);
                    ContasReceber cr = new ContasReceber(vencimento.getTime(), vrParc, this, pessoa);
                    cr.setNrParcela(i + "/" + nrParcelas);
                    contasReceber.add(cr);
                }
            }
            if (nrParcelas.compareTo(planoPagamento.getQtdMaxParc()) > 0 || nrParcelas < 0) {
                throw new Exception("Quantidade de Parcelas Invalidas");
            }

        }
        if (planoPagamento.getTipoPagamento().equals(TipoPagamento.APRAZO) && nrParcelas == null) {
            throw new Exception("Informe a quantidade de Parcelas");
        }
    }

    private boolean verificaEntrada() throws Exception {
        return verificaUltimaCompraCliente();

    }

    private boolean verificaUltimaCompraCliente() throws Exception {
        if (pessoa != null && pessoa.getDtUltimaCompra() != null) {
            if (!planoPagamento.getEntrada() || valorEntrada.compareTo(new BigDecimal("0.00")) > 0) {
                return true;
            }
            throw new Exception("Plano de pagamento exige entrada!");
        } else if (verificaClienteNovo()) {
            return true;
        }
        return false;
    }

    public boolean verificaClienteNovo() throws Exception {
        if (valorEntrada == null || valorEntrada.compareTo(new BigDecimal("0.00")) == 0) {
            throw new Exception("O Cliente é novo, necessita entrada!");
        } else if (verificaValorEntrada()) {
            return true;
        }
        return false;
    }

    public boolean verificaValorEntrada() throws Exception {
        if (valorEntrada.compareTo(BigDecimal.ZERO) > 0 && valorBruto.compareTo(valorEntrada) > 0 && valorEntrada.compareTo(valorBruto.multiply(new BigDecimal("0.5"))) >= 0) {
            return true;
        }
        throw new Exception("Valor de entrada deve ser igual ou maior que: " + this.valorBruto.divide(new BigDecimal("2")));
    }

    public boolean verificaLimete() throws Exception {
        if (totalVenda.compareTo(pessoa.getLimiteCredito()) <= 0) {
            return true;
        }
        throw new Exception("Limite de Crédito Insulficiente, disponivel: R$" + this.getPessoa().getLimiteCredito());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatusFechado() {
        return statusFechado;
    }

    public void setStatusFechado(Boolean statusFechado) {
        this.statusFechado = statusFechado;
    }

    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(BigDecimal valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Integer getNrParcelas() {
        return nrParcelas;
    }

    public void setNrParcelas(Integer nrParcelas) {
        this.nrParcelas = nrParcelas;
//        if (nrParcelas > 0 && nrParcelas <= planoPagamento.getQtdMaxParc()) {
//            this.nrParcelas = nrParcelas;
//        } else {
//            throw new Exception("Quantidade de parcelas inválidas");
//        }

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

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public List<ContasReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContasReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

    public ItemVenda getItem() {
        return item;
    }

    public void setItem(ItemVenda item) {
        this.item = item;
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
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
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
