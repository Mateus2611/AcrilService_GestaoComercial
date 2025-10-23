package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Orcamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    "INSERT INTO `Orcamento` (`Id_Cliente`, `Data_Criacao`, "
                            + "`Data_Validade`, `Valor`, `Desconto`, `Status_Orcamento`) "
                            + "VALUES ( ?, ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdCliente());
            statement.setDate(2, objeto.getDataCriacao());
            statement.setDate(3, objeto.getDataValidade());
            statement.setBigDecimal(4, objeto.getValor());
            statement.setBigDecimal(5, objeto.getDesconto());
            statement.setString(6, objeto.getStatus().name());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir orçamento");

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

    @Override
    public List<Orcamento> BuscaGeral() {
        PreparedStatement statement = null;
        List<Orcamento> orcamentos = new ArrayList<>();

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Orcamento`"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String orcamentoSatusString = resultSet.getString("Status_Orcamento");
                Orcamento.StatusOrcamento status = null;

                if (orcamentoSatusString != null) {
                    try {
                        status = Orcamento.StatusOrcamento.valueOf(orcamentoSatusString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Status inválido: " + orcamentoSatusString);
                    }
                }

                Orcamento objeto = new Orcamento(
                        resultSet.getInt("Id_Cliente"),
                        resultSet.getDate("Data_Criacao"),
                        resultSet.getDate("Data_Validade"),
                        resultSet.getBigDecimal("Valor"),
                        status,
                        resultSet.getBigDecimal("Desconto"));

                objeto.setId(resultSet.getInt("Id"));
                orcamentos.add(objeto);
            }

            ConexaoDb.closeResultSet(resultSet);

            return orcamentos;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Orcamento Atualizar(Integer integer, Orcamento objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Orcamento`"
                            + " SET `Data_Validade` = ?, `Valor` = ?, `Desconto` = ?, `Status_Orcamento` = ?"
                            + " WHERE `Id` = ?"
            );

            statement.setDate(1, objeto.getDataValidade());
            statement.setBigDecimal(2, objeto.getValor());
            statement.setBigDecimal(3, objeto.getDesconto());
            statement.setString(4, objeto.getStatus().name());
            statement.setInt(5, integer);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0)
                throw new RuntimeException("Nenhum orçamento encontrado" + integer);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    @Override
    public void Excluir(Integer integer) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE FROM `Orcamento`" +
                            " WHERE `Id` = ?"
            );

            statement.setInt(1, integer);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.err.println("Nenhum orçamento excluído. ID não encontrado: " + integer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir orçamento: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}