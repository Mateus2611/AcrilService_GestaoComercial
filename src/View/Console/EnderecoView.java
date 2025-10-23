package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.EnderecoDAO;
import Model.Endereco;
import Service.EnderecoService;

import java.util.Arrays;
import java.util.Scanner;

enum Opcoes {
    Criar
}

public class EnderecoView {

    Scanner sc = new Scanner(System.in);

    public void SelecionarAcaoEndereco() {

        System.out.println("\n\nSelecione a operação desejada\n\n ");
        System.out.println(Arrays.toString(Opcoes.values()));
        System.out.println();
        String resp = sc.next();

        switch (Opcoes.valueOf(resp)) {
            case Criar -> CriarEndereco();
        }
    }

    private void CriarEndereco() {

        EnderecoDAO dao = new EnderecoDAO(ConexaoDb.openConnection());
        EnderecoService endereco = new EnderecoService(dao);

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
        objeto = endereco.Criar(objeto);

        System.out.println(objeto.toString());
    }

}
