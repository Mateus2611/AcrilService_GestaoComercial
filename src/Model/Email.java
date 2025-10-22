package Model;

public class Email {

    public Email() {
    }

    public Email(Integer id, String endereco, Model.Cliente cliente) {
        Id = id;
        Endereco = endereco;
        Cliente = cliente;
    }

    private Integer Id;
    public String Endereco;
    public Cliente Cliente;

    public Integer getId() {
        return Id;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }
}
