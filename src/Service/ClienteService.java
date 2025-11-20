package Service;

import DAO.MySQL.ClienteDAO;
import DAO.MySQL.EmailDAO;
import DAO.MySQL.EnderecoDAO;
import Model.Cliente;
import Model.Email;
import Model.Endereco;

import java.util.List;

public class ClienteService {

    private final ClienteDAO _clienteDAO;
    private final EnderecoDAO _enderecoDAO;
    private final EmailDAO _emailDAO;

    public ClienteService(ClienteDAO clienteDAO, EnderecoDAO enderecoDAO, EmailDAO emailDAO) {
        this._clienteDAO = clienteDAO;
        this._enderecoDAO = enderecoDAO;
        this._emailDAO = emailDAO;
    }

    public Cliente Criar(Cliente cliente, Endereco endereco, List<Email> emails) {

        if (cliente == null)
            throw new RuntimeException("Dados do cliente vazio. Preencha as informações");
        if (endereco == null)
            throw new RuntimeException("Dados de endereço vazio. Preencha as informações");
        if (emails== null)
            throw new RuntimeException("Endereço de email vazio. Preencha a informação");

        try {
            endereco = _enderecoDAO.Criar(endereco);
            cliente.setIdEndereco(endereco.getId());
            cliente = _clienteDAO.Criar(cliente);
            cliente.setEndereco(endereco);

            for (Email email :  emails) {
                email.setIdCliente(cliente.getId());
                cliente.Emails.add(_emailDAO.Criar(email));
            }

            return cliente;

        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Cliente> BuscaGeral() {
        try {
            return _clienteDAO.BuscaGeral();
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente BuscaPorId(Integer id) {
        try {
            return _clienteDAO.BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtualizarCliente(Integer id, Cliente objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            _clienteDAO.BuscaPorId(id);

            return _clienteDAO.Atualizar(id, objeto);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente InativarCliente(int id) {
        try {
            _clienteDAO.BuscaPorId(id);

            return _clienteDAO.InativarCliente(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtivarCliente(int id) {
        try {
            _clienteDAO.BuscaPorId(id);

            return _clienteDAO.AtivarCliente(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
