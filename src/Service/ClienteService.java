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
            List<Cliente> clientes = _clienteDAO.BuscaGeral();

            for (Cliente cliente : clientes) {
                cliente.setEndereco(_enderecoDAO.BuscaPorId(cliente.getIdEndereco()));
            }

            return clientes;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente BuscaPorId(Integer id) {
        try {
            Cliente cliente = _clienteDAO.BuscaPorId(id);

            cliente.setEndereco(_enderecoDAO.BuscaPorId(cliente.getIdEndereco()));

            cliente.setEmails(_emailDAO.BuscaGeral(cliente.getId()));

            return cliente;
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtualizarTipoCliente(Integer id, String tipoCliente) {
        try {

            _clienteDAO.AtualizarTipoCliente(id, tipoCliente);

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtualizarNomeCliente(Integer id, String nome) {
        try {

            _clienteDAO.AtualizarNomeCliente(id, nome);

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtualizarEnderecoCliente(Integer idCliente, Endereco endereco) {
        try {
            Cliente cliente = _clienteDAO.BuscaPorId(idCliente);
            Integer idEnderecoDesatualizado = cliente.IdEndereco;

            endereco = _enderecoDAO.Criar(endereco);
            _clienteDAO.AtualizarEnderecoCliente(idCliente, endereco.getId());

            _enderecoDAO.Excluir(idEnderecoDesatualizado);

            return BuscaPorId(idCliente);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente InativarCliente(int id) {
        try {

            _clienteDAO.InativarCliente(id);

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Cliente AtivarCliente(int id) {
        try {

            _clienteDAO.AtivarCliente(id);

            return BuscaPorId(id);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
