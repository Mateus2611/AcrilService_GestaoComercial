package Model;

import java.util.Date;

public class Avaliacao {

    public Avaliacao() {
    }

    public Avaliacao(Integer id, Integer idVenda, Float nota,
                     Date dataCriacao, String descricao, String titulo) {
        Id = id;
        IdVenda = idVenda;
        Nota = nota;
        DataCriacao = dataCriacao;
        Descricao = descricao;
        Titulo = titulo;
    }

    public Avaliacao(String titulo, String descricao,
                     Date dataCriacao, Float nota, Integer idVenda) {
        Titulo = titulo;
        Descricao = descricao;
        DataCriacao = dataCriacao;
        Nota = nota;
        IdVenda = idVenda;
    }

    private Integer Id;
    private Integer IdVenda;
    public String Titulo;
    public String Descricao;
    public Date DataCriacao;
    public Float Nota;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Float getNota() {
        return Nota;
    }

    public void setNota(Float nota) {
        Nota = nota;
    }

    public Integer getIdVenda() {
        return IdVenda;
    }

    public void setIdVenda(Integer idVenda) {
        IdVenda = idVenda;
    }
}
