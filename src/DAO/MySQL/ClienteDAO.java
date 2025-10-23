package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Cliente;
import Model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO implements IOperacoesGenericasDAO {

    private final Connection _connection;

    public ClienteDAO(Connection connection) {
        _connection = connection;
    }

    @Override
    public Object Criar(Object objeto) {
        return null;
    }

    @Override
    public Object BuscaGeral() {
        return null;
    }


    @Override
    public Object Atualizar(Object o, Object objeto) {
        return null;
    }

    @Override
    public Object Excluir(Object o) {
        return null;
    }
}
