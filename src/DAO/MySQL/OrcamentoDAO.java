package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Orcamento;

import java.sql.*;

public class OrcamentoDAO implements IOperacoesGenericasDAO<Integer, Orcamento> {

    private final Connection _connection;

    public OrcamentoDAO(Connection connection) {
        _connection = connection;
    }

    @Override
    public Orcamento Criar(Orcamento objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Orcamento` (`id_Cliente`, `data_criacao`,"
                            + "`data_validade`, `valor`, `desconto`) "
                            + "VALUES ( ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getId());
            statement.setDate(2, objeto.getDataCriacao());
            statement.setDate(3, objeto.getDataValidade());
            statement.setBigDecimal(4, objeto.Valor);
            statement.setBigDecimal(5, objeto.Desconto);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir orcamento");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    @Override
    public Orcamento BuscaGeral() {
        return null;
    }

    //@Override
    public Orcamento BuscaGeral(Orcamento objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM 'Orcamento'",
                    Statement.RETURN_GENERATED_KEYS
            );

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0)
                throw new RuntimeException("Erro ao buscar orçamento");


        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    @Override
    public Orcamento Atualizar(Integer integer, Orcamento objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE ´Orcamento´"
                            + "SET data_validade = ?, valor = ?, desconto = ?"
                            + "WHERE 'Id' = ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setDate(1, objeto.getDataValidade());
            statement.setBigDecimal(2, objeto.getValor());
            statement.setBigDecimal(3, objeto.getDesconto());
            statement.setInt(4, objeto.getId());

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
    public Orcamento Excluir(Integer integer) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE from 'Orcamento'" +
                            "WHERE 'Id' = ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, integer);

            int rowsAffected = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir orçamento");
        } finally {
            ConexaoDb.closeConnection();
            ConexaoDb.closeStatement(statement);
        }
        return null;
    }
}
