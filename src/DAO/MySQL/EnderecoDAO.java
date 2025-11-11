package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Endereco;

import java.sql.*;
import java.util.List;

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
    public List<Endereco> BuscaGeral() {
       PreparedStatement statement = null;
       List<Endereco> enderecos = null;

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Endereco`;"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Endereco objeto = new Endereco(
                        resultSet.getInt("Id"),
                        resultSet.getString("CEP"),
                        resultSet.getString("Bairro"),
                        resultSet.getString("Estado"),
                        resultSet.getString("Cidade"),
                        resultSet.getString("Logradouro"));

                enderecos.add(objeto);
            }

            if (enderecos == null)
                throw new RuntimeException("Não existem endereços cadastrados");

            return enderecos;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Endereco Atualizar(Integer integer, Endereco objeto) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Endereco` SET `CEP` = ?, `Bairro` = ?, `Estado` = ?, `Cidade` = ?, `Logradouro` = ? WHERE `endereco`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, objeto.getCep());
            statement.setString(2, objeto.getBairro());
            statement.setString(3, objeto.getEstado());
            statement.setString(4, objeto.getCidade());
            statement.setString(5, objeto.getLogradouro());
            statement.setInt(6, integer);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar endereço");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public void Excluir(Integer integer) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "DELETE FROM `Endereco` WHERE Id = ?;"
            );

            statement.setInt(1, integer);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}
