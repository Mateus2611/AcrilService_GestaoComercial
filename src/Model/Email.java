package Model;

public class Email {

    public Email() {
    }

    public Email(Integer id, String endereco) {
        Id = id;
        Endereco = endereco;
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
