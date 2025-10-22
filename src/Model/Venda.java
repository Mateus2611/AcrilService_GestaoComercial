package Model;

import java.util.Date;

public class Venda {

    public Venda() {
    }

    public Venda(Integer id, Model.Orcamento orcamento, Date dataCriacao,
                 Date prazoPagamento, Date dataConclusao, Venda.StatusPagamento statusPagamento) {
        this.id = id;
        Orcamento = orcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }

    public Venda(Model.Orcamento orcamento, Date dataCriacao,
                 Date prazoPagamento, Date dataConclusao, Venda.StatusPagamento statusPagamento) {
        Orcamento = orcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }

    private Integer id;
    public Orcamento Orcamento;
    public Date DataCriacao;
    public Date PrazoPagamento;
    public Date DataConclusao;
    public StatusPagamento StatusPagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Model.Orcamento getOrcamento() {
        return Orcamento;
    }

    public void setOrcamento(Model.Orcamento orcamento) {
        Orcamento = orcamento;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getPrazoPagamento() {
        return PrazoPagamento;
    }

    public void setPrazoPagamento(Date prazoPagamento) {
        PrazoPagamento = prazoPagamento;
    }

    public Date getDataConclusao() {
        return DataConclusao;
    }

    public void setDataConclusao(Date dataConclusao) {
        DataConclusao = dataConclusao;
    }

    public Venda.StatusPagamento getStatusPagamento() {
        return StatusPagamento;
    }

    public void setStatusPagamento(Venda.StatusPagamento statusPagamento) {
        StatusPagamento = statusPagamento;
    }

    enum StatusPagamento {
        APROVADO,
        REJEITADO,
        PENDENTE
    }
}
