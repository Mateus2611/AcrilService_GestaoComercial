package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.ClienteDAO;
import DAO.MySQL.EmailDAO;
import DAO.MySQL.EnderecoDAO;
import Model.Cliente;
import Model.Email;
import Model.Endereco;
import Service.ClienteService;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum Menu {
    CRIAR,
    BUSCA,
    BUSCA_POR_ID,
    ATIVAR,
    INATIVAR,
    ATUALIZAR_NOME,
    ATUALIZAR_TIPO,
    ATUALIZAR_ENDERECO
}

public class ClienteView {

    Scanner sc = new Scanner(System.in);
    Connection _conn = ConexaoDb.openConnection();

    ClienteDAO _clienteDAO = new ClienteDAO(_conn);
    EnderecoDAO _enderecoDAO = new EnderecoDAO(_conn);
    EmailDAO _emailDAO = new EmailDAO(_conn);

    EnderecoView _enderecoView = new EnderecoView();
    EmailView _emailView = new EmailView();

    ClienteService _clienteServices = new ClienteService(_clienteDAO, _enderecoDAO, _emailDAO);

    public void SelecionarAcaoCliente() {

        System.out.println("\n\nSelecione a operação desejada\n\n ");
        System.out.println(Arrays.toString(Menu.values()));
        System.out.println();
        String resp = sc.next().toUpperCase();

        switch (Menu.valueOf(resp)) {
            case CRIAR:
                CriarCliente();
                break;
            case BUSCA:
                BuscaGeral();
                break;
            case BUSCA_POR_ID:
                BuscaPorId();
                break;
            case ATIVAR:
                AtivarCliente();
                break;
            case INATIVAR:
                InativarCliente();
                break;
            case ATUALIZAR_NOME:
                AtualizarNomeCliente();
                break;
            case ATUALIZAR_TIPO:
                AtualizarTipoCliente();
                break;
            case ATUALIZAR_ENDERECO:
                AtualizarEnderecoCliente();
                break;
        }
    }

    public void CriarCliente() {

        System.out.println("Informe o nome do cliente.");
        String nomeCliente = sc.nextLine();

        System.out.println("Informa o Tipo do cliente. (CPF/CNPJ)");
        String tipoCliente = sc.next().toUpperCase();

        Cliente cliente = new Cliente(Cliente.TipoCliente.valueOf(tipoCliente), nomeCliente);

        Endereco endereco = _enderecoView.CriarEndereco();
        List<Email> emails = _emailView.CriarEmail();

        System.out.println(_clienteServices.Criar(cliente, endereco, emails).toString());
    }

    public void BuscaGeral() {
        List<Cliente> clientes = _clienteServices.BuscaGeral();

        System.out.println(clientes.toString());
    }

    public void BuscaPorId() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println(_clienteServices.BuscaPorId(id).toString());
    }

    public void AtivarCliente() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println(_clienteServices.AtivarCliente(id).toString());
    }

    public void InativarCliente() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println(_clienteServices.InativarCliente(id).toString());
    }

    public void AtualizarTipoCliente() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println("Selecione o tipo do cliente: ");
        System.out.println(Arrays.toString(Cliente.TipoCliente.values()));
        String tipo = sc.next().toUpperCase();

        System.out.println(_clienteServices.AtualizarTipoCliente(id, tipo));
    }

    public void AtualizarNomeCliente() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        System.out.println("Selecione o nome do cliente: ");
        String nome = sc.nextLine();

        System.out.println(_clienteServices.AtualizarNomeCliente(id, nome));
    }

    public void AtualizarEnderecoCliente() {
        System.out.println("Informe o ID do usuário: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        Endereco endereco = _enderecoView.CriarEndereco();

        System.out.println(_clienteServices.AtualizarEnderecoCliente(id, endereco));
    }
}
