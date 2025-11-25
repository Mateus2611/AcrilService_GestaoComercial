package Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Orcamento {


    public Orcamento() {
    }

    public Orcamento(Integer id, Integer idCliente, Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        Id = id;
        IdCliente = idCliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Orcamento(Integer idCliente, Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        IdCliente = idCliente;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Orcamento(Integer idCliente, BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        IdCliente = idCliente;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private Integer Id;
    private Integer IdCliente;
    private String nomeCliente;
    private List<String> nomeProdutos;
    public Date DataCriacao;
    public Date DataValidade;
    public BigDecimal Valor;
    public StatusOrcamento Status;
    public BigDecimal Desconto;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer idCliente) {
        IdCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<String> getNomeProdutos() {
        return nomeProdutos;
    }

    public void setNomeProdutos(List<String> nomeProdutos) {
        this.nomeProdutos = nomeProdutos;
    }

    public java.util.Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public java.util.Date getDataValidade() {
        return DataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        DataValidade = dataValidade;
    }

    public BigDecimal getValor() {
        return Valor;
    }

    public void setValor(BigDecimal valor) {
        Valor = valor;
    }

    public StatusOrcamento getStatus() {
        return Status;
    }

    public BigDecimal getDesconto() {
        return Desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        Desconto = desconto;
    }

    public void setStatus(StatusOrcamento status) {
        Status = status;
    }

    public enum StatusOrcamento {
        APROVADO,
        REPROVADO,
        EXPIRADO,
        PENDENTE
    }

    @Override
    public String toString() {

        String produtos = "";
        if (nomeProdutos != null && !nomeProdutos.isEmpty()) {
            produtos = String.join(", ", nomeProdutos);
        }

        return "Orcamento: " +
                "\nId = " + Id +
                "\nIdCliente = " + IdCliente +
                "\nCliente = " + nomeCliente +
                "\nProdutos = " + produtos +
                "\nDataCriacao = " + format.format(DataCriacao) +
                "\nDataValidade = " + format.format(DataValidade) +
                "\nValor = " + Valor +
                "\nStatus = " + Status +
                "\nDesconto = " + Desconto + "\n";
    }
}
