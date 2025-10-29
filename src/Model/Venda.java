package Model;

import java.util.Date;

public class Venda {

    public Venda() {
    }

    public Venda(Integer idOrcamento, Date dataCriacao, Date prazoPagamento, Date dataConclusao, StatusPagamento statusPagamento) {
        IdOrcamento = idOrcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }

    public Venda(Integer id, Integer idOrcamento, Date dataCriacao, Date prazoPagamento, Date dataConclusao, StatusPagamento statusPagamento) {
        Id = id;
        IdOrcamento = idOrcamento;
        DataCriacao = dataCriacao;
        PrazoPagamento = prazoPagamento;
        DataConclusao = dataConclusao;
        StatusPagamento = statusPagamento;
    }


    private Integer Id;
    private Integer IdOrcamento;
    private Date DataCriacao;
    private Date PrazoPagamento;
    private Date DataConclusao;
    private StatusPagamento StatusPagamento;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdOrcamento() {
        return IdOrcamento;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        IdOrcamento = idOrcamento;
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

    public StatusPagamento getStatusPagamento() {
        return StatusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        StatusPagamento = statusPagamento;
    }

    public static enum StatusPagamento {
        APROVADO,
        REJEITADO,
        PENDENTE
    }
}