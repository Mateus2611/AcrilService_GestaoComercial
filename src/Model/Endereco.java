package Model;

public class Endereco {

    public Endereco() {
    }

    public Endereco(Integer id, String cep, String bairro, String estado, String cidade, String logradouro, String complemento) {
        Id = id;
        Cep = cep;
        Bairro = bairro;
        Estado = estado;
        Cidade = cidade;
        Logradouro = logradouro;
        Complemento = complemento;
    }

    public Endereco(String cep, String bairro, String estado, String cidade, String logradouro, String complemento) {
        Cep = cep;
        Bairro = bairro;
        Estado = estado;
        Cidade = cidade;
        Logradouro = logradouro;
        Complemento = complemento;
    }

    private Integer Id;
    public String Cep;
    public String Bairro;
    public String Estado;
    public String Cidade;
    public String Logradouro;
    public String Complemento;

    public Integer getId() {
        return Id;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String logradouro) {
        Logradouro = logradouro;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }
}
