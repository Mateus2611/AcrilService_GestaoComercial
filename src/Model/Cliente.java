package Model;

import java.util.List;

public class Cliente {

    public Cliente() {
    }

    public Cliente(Integer id, String nome, Date dataCadastro, Date dataInativacao, TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    private Integer Id;
    public String Nome;
    public List<Email> Emails;
    public Endereco Endereco;
    private Date DataCadastro;
    private Date DataInativacao;
    public TipoCliente Tipo;
    private StatusCliente Status;

    public Integer getId() {
        return Id;
    }

    public Date getDataCadastro() {
        return DataCadastro;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getDataInativacao() {
        return DataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        DataInativacao = dataInativacao;
    }

    public TipoCliente getTipo() {
        return Tipo;
    }

    public void setTipo(TipoCliente tipo) {
        Tipo = tipo;
    }

    public StatusCliente getStatus() {
        return Status;
    }

    public void setStatus(StatusCliente status) {
        Status = status;
    }
}

enum TipoCliente {
    CPF,
    CNPJ
}

enum StatusCliente {
    ATIVO,
    INATIVO
}
