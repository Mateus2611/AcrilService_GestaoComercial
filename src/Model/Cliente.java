package Model;

import java.util.Date;
import java.util.List;

public class Cliente {

    public Cliente() {
    }

    public Cliente(Integer id, String nome, List<Email> emails, Endereco endereco, Date dataCadastro,
                   Date dataInativacao, TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        Emails = emails;
        Endereco = endereco;
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    public Cliente(Integer id, String nome, Date dataCadastro,
                   Date dataInativacao, TipoCliente tipo, StatusCliente status) {
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
    public Date DataCadastro;
    public Date DataInativacao;
    public TipoCliente Tipo;
    private StatusCliente Status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public List<Email> getEmails() {
        return Emails;
    }

    public void setEmails(List<Email> emails) {
        Emails = emails;
    }

    public Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(Endereco endereco) {
        Endereco = endereco;
    }

    public void setDataCadastro(Date dataCadastro) {
        DataCadastro = dataCadastro;
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

    public enum TipoCliente {
        CPF,
        CNPJ
    }

    public enum StatusCliente {
        ATIVO,
        INATIVO
    }
}
