package Model;

public class Email {

    public Email() {
    }

    public Email(String endereco) {
        Endereco = endereco;
    }

    public Email(Integer id, Integer idCliente, String endereco) {
        Id = id;
        IdCliente = idCliente;
        Endereco = endereco;
    }

    private Integer Id;
    public Integer IdCliente;
    public String Endereco;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(Integer idCliente) {
        IdCliente = idCliente;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    @Override
    public String toString() {
        return "Email{\n" +
                "\nId: " + Id +
                ", \nEndereco: " + Endereco + '\'' +
                "\n}";
    }
}
