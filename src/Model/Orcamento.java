package Model;

import java.math.BigDecimal;
import java.sql.Date;

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

    private Integer Id;
    private Integer IdCliente;
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

    public java.sql.Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public java.sql.Date getDataValidade() {
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

    public void setStatus(StatusOrcamento status) {
        Status = status;
    }

    public enum StatusOrcamento {
        APROVADO,
        REJEITADO
    }
}
