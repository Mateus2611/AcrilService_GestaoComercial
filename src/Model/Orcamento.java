package Model;

import java.math.BigDecimal;
import java.util.Date;

public class Orcamento {


    public Orcamento() {
    }

    public Orcamento(Integer id, Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        Id = id;
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    public Orcamento(Date dataCriacao, Date dataValidade,
                     BigDecimal valor, StatusOrcamento status, BigDecimal desconto) {
        DataCriacao = dataCriacao;
        DataValidade = dataValidade;
        Valor = valor;
        Status = status;
        Desconto = desconto;
    }

    private Integer Id;
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

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getDataValidade() {
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

    public void setStatus(StatusOrcamento status) {
        Status = status;
    }

    enum StatusOrcamento {
        APROVADO,
        REJEITADO
    }
}
