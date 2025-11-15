package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Endereco;
import Model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    "INSERT INTO `Produto` (`Nome`, `Valor`)"
                            + " VALUES ( ?, ? );",
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

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    public List<Produto> BuscaGeral() {
        PreparedStatement statement = null;
        List<Produto> produtos = new ArrayList<>();

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Produto`"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Produto objeto = new Produto(
                        resultSet.getInt("Id"),
                        resultSet.getString("Nome"),
                        resultSet.getBigDecimal("Valor"));

                produtos.add(objeto);
            }

            ConexaoDb.closeResultSet(resultSet);

            return produtos;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Produto Atualizar(Integer integer, Produto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Produto`"
                            + " SET `Nome` = ?, `Valor` = ?"
                            + " WHERE `Id` = ?"
            );

            statement.setString(1, objeto.getNome());
            statement.setBigDecimal(2, objeto.getValor());
            statement.setInt(3, integer);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("Nenhum produto encontrado" + integer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    public Produto BuscaPorId(Integer id) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Produto` WHERE Id = ?;"
            );

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Produto objeto = new Produto(
                        resultSet.getInt("Id"),
                        resultSet.getString("Nome"),
                        resultSet.getBigDecimal("Valor"));

                return objeto;
            }

            throw new RuntimeException("Não foi encontrado produto correspondente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public void Excluir(Integer integer) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE FROM `Produto`" +
                            " WHERE `Id` = ?"
            );

            statement.setInt(1, integer);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}