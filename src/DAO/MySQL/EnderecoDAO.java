package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Endereco;

import java.sql.*;

public class EnderecoDAO implements IOperacoesGenericasDAO<Integer, Endereco> {

    private final Connection _connection;

    public EnderecoDAO(Connection connection) {
        _connection = connection;
    }


    @Override
    public Endereco Criar(Endereco objeto) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "INSERT INTO `Endereco` (`CEP`, `Bairro`, `Estado`, `Cidade`, `Logradouro`) "
                            + "VALUES ( ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, objeto.getCep());
            statement.setString(2, objeto.getBairro());
            statement.setString(3, objeto.getEstado());
            statement.setString(4, objeto.getCidade());
            statement.setString(5, objeto.getLogradouro());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir endereço");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Endereco BuscaGeral() {
        return null;
    }

    @Override
    public Endereco Atualizar(Integer integer, Endereco objeto) {
        return null;
    }

    @Override
    public Endereco Excluir(Integer integer) {
        return null;
    }
}
