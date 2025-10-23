package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Produto;

import java.sql.*;

public class ProdutoDAO implements IOperacoesGenericasDAO<Integer, Produto> {

    private final Connection _connection;

    public ProdutoDAO(Connection connection) {
        this._connection = connection;
    }

    @Override
    public Produto Criar(Produto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Produto` (`nome`, `valor`,"
                            + "VALUES ( ?, ? );",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, objeto.getNome());
            statement.setBigDecimal(2, objeto.getValor());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir produto");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
            ConexaoDb.closeStatement(statement);
        }

    }

    @Override
    public Produto BuscaGeral() {
        return null;
    }

    //@Override
    public Produto BuscaGeral(Produto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM 'Produto'",
                    Statement.RETURN_GENERATED_KEYS
            );

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                throw new RuntimeException("Erro ao buscar produto");


        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return objeto;

    }

    @Override
    public Produto Atualizar(Integer integer, Produto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE ´Produto´"
                            + "SET nome = ?, valor = ?"
                            + "WHERE 'Id' = ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, objeto.getNome());
            statement.setBigDecimal(2, objeto.getValor());
            statement.setInt(3, objeto.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("Erro ao atualizar valores");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    @Override
    public Produto Excluir(Integer integer) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE from 'Produto'" +
                            "WHERE 'Id' = ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, integer);

            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto");
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return null;
    }
}
