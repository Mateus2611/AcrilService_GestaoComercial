package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.EnderecoDAO;
import Model.Endereco;
import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class EnderecoView {

    Scanner sc = new Scanner(System.in);
    EnderecoDAO enderecoDAO = new EnderecoDAO(ConexaoDb.openConnection());

    public Endereco CriarEndereco() {

        System.out.println("Informe o seu CEP: ");
        String Cep = sc.next();
        sc.nextLine();
        System.out.println(Cep);

        System.out.println("Informe o seu Bairro:");
        String Bairro = sc.nextLine();
        System.out.println(Bairro);

        System.out.println("Informe o seu Estado: ");
        String Estado = sc.nextLine();
        System.out.println(Estado);

        System.out.println("Informe a sua Cidade:");
        String Cidade = sc.nextLine();
        System.out.println(Cidade);

        System.out.println("Informe o seu Logradouro: ");
        String Logradouro = sc.nextLine();
        System.out.println(Logradouro);

        Endereco objeto = new Endereco(Cep, Bairro, Estado, Cidade, Logradouro);
        return objeto;
    }
}
