package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Cliente {

    public Cliente() {
    }

    public Cliente(Integer id, String nome, List<Email> emails,
                   Integer idEndereco, Date dataCadastro, Date dataInativacao,
                   TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        Emails = emails;
        IdEndereco = idEndereco;
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    public Cliente(Integer id, String nome, Integer idEndereco,
                   Date dataCadastro, Date dataInativacao,
                   TipoCliente tipo, StatusCliente status) {
        Id = id;
        Nome = nome;
        IdEndereco = idEndereco;
        DataCadastro = dataCadastro;
        DataInativacao = dataInativacao;
        Tipo = tipo;
        Status = status;
    }

    public Cliente(TipoCliente tipo, String nome) {
        Tipo = tipo;
        Nome = nome;
    }

    public Integer Id;
    public String Nome;
    public List<Email> Emails = new ArrayList<Email>();
    public Integer IdEndereco;
    public Endereco Endereco;
    public Date DataCadastro;
    public Date DataInativacao;
    public TipoCliente Tipo;
    public StatusCliente Status;

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

    public Integer getIdEndereco() {
        return IdEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        IdEndereco = idEndereco;
    }

    public void setDataCadastro(Date dataCadastro) {
        DataCadastro = dataCadastro;
    }

    public Endereco getEndereco() {
        return Endereco;
    }

    public void setEndereco(Endereco endereco) {
        Endereco = endereco;
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

    @Override
    public String toString() {
        return "\nId: " + Id +
                ", \nNome: '" + Nome + '\'' +
                ", \n" + Emails.toString() +
                ", \nIdEndereco: " + IdEndereco +
                ", \n" + Endereco.toString() +
                ", \nDataCadastro: " + DataCadastro +
                ", \nDataInativacao: " + DataInativacao +
                ", \nTipo: " + Tipo +
                ", \nStatus: " + Status;
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
