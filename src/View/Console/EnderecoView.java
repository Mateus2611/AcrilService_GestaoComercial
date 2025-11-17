package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.EnderecoDAO;
import Model.Endereco;
import Service.EnderecoService;
import com.sun.tools.javac.Main;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum Opcoes {
    Criar,
    BuscaGeral,
    BuscarId,
    Atualizar,
    Excluir
}

public class EnderecoView {

    Scanner sc = new Scanner(System.in);
    EnderecoDAO enderecoDAO = new EnderecoDAO(ConexaoDb.openConnection());
    EnderecoService enderecoService = new EnderecoService(enderecoDAO);

    public void SelecionarAcaoEndereco() {

        System.out.println("\n\nSelecione a operação desejada\n\n ");
        System.out.println(Arrays.toString(Opcoes.values()));
        System.out.println();
        String resp = sc.next();

        switch (Opcoes.valueOf(resp)) {
            case Criar:
                CriarEndereco();
            case BuscaGeral:
                BuscaGeral();
                break;
            case BuscarId:
                BuscaPorId();
                break;
            case Atualizar:
                Atualizar();
                break;
            case Excluir:
                Excluir();
                break;
        }
    }

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

    private void BuscaGeral() {
        List<Endereco> enderecos = enderecoService.BuscaGeral();

        enderecos.forEach(System.out::println);
    }

    private void BuscaPorId() {

        System.out.println("Informe o ID do endereço que deseja buscar");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            System.out.println(enderecoService.BuscaPorId(id));
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void Atualizar() {
        System.out.println("Informe o ID do endereço que deseja atualizar:");
        int id = sc.nextInt();
        sc.nextLine();

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
        objeto = enderecoService.Atualizar(id, objeto);

        System.out.println(objeto.toString());
    }

    private void Excluir() {
        System.out.println("Informe o ID do endereço que deseja excluir");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            enderecoService.Excluir(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
