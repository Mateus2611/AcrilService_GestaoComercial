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
    CRIAR
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
        String resp = sc.next();

        switch (Menu.valueOf(resp)) {
            case CRIAR:
                CriarCliente();
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
}
