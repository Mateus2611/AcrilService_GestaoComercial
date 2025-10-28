package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Email;

import java.sql.*;
import java.util.List;

public class EmailDAO implements IOperacoesGenericasDAO<Integer, Email> {

    private final Connection _connection;

    public EmailDAO(Connection _connection) {
        this._connection = _connection;
    }

    @Override
    public Email Criar(Email objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `email` (`Id_Cliente`, `Endereco`) " +
                            "VALUES ( ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdCliente());
            statement.setString(2, objeto.getEndereco());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir email");

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

    public List<Email> BuscaGeral(Integer idCliente) {
        PreparedStatement statement = null;
        List<Email> emails = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Email` WHERE Id_Cliente = ? ;"
            );

            statement.setInt(1, idCliente);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Email objeto = new Email(
                  resultSet.getInt("Id"),
                  resultSet.getInt("Id_Cliente"),
                  resultSet.getString("Endereco")
                );

                emails.add(objeto);
            }

            ConexaoDb.closeResultSet(resultSet);

            if (emails != null)
                return emails;

            throw new RuntimeException("Não existem emails relacionados a este cliente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Email Atualizar(Integer id, Email objeto) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Email` SET `Id_Cliente` = ?, `Endereco` = ? " +
                            "WHERE `email`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdCliente());
            statement.setString(2, objeto.getEndereco());
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar endereço");

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
    public void Excluir(Integer id) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "DELETE FROM `Email` WHERE Id = ?;"
            );

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}
