package Model;

import java.math.BigDecimal;

public class Produto {

    public Produto() {
    }

    public Produto(Integer id, String nome, BigDecimal valor) {
        Id = id;
        Nome = nome;
        Valor = valor;
    }

    public Produto(String nome, BigDecimal valor) {
        Nome = nome;
        Valor = valor;
    }

    private Integer Id;
    public String Nome;
    public BigDecimal Valor;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public BigDecimal getValor() {
        return Valor;
    }

    public void setValor(BigDecimal valor) {
        Valor = valor;
    }

    @Override
    public String toString() {
        return "Produto: " +
                "Id = " + Id +
                " | Nome = " + Nome +
                " | Valor = R$" + Valor;
    }
}
