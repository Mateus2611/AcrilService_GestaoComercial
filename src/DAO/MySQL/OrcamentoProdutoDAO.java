package DAO.MySQL;

import DAO.JDBC.ConexaoDb;
import Model.OrcamentoProduto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrcamentoProdutoDAO {

    private final Connection _connection;

    public OrcamentoProdutoDAO(Connection connection) {
        _connection = connection;
    }

    public void AdicionarProduto(OrcamentoProduto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Orcamento_Produto` " +
                            "(`Id_Orcamento`, `Id_Produto`, `Quantidade`) "
                            + "VALUES ( ?, ?, ? );"
            );

            statement.setInt(1, objeto.getIdOrcamento());
            statement.setInt(2, objeto.getIdProduto());
            statement.setInt(3, objeto.getQuantidade());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir Produto ao Orçamento");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void AtualizarQuantidade(OrcamentoProduto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Orcamento_Produto` SET `Quantidade` = ? " +
                            "WHERE `Id_Orcamento` = ? AND  `Id_Produto` = ?;"
            );

            statement.setInt(1, objeto.getQuantidade());
            statement.setInt(2, objeto.getIdOrcamento());
            statement.setInt(3, objeto.getIdProduto());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar quantidade do produto");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void RemoverItem(OrcamentoProduto objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE FROM `Orcamento_Produto` " +
                            "WHERE `Id_Orcamento` = ? AND `Id_Produto` = ?; "
            );

            statement.setInt(1, objeto.getIdOrcamento());
            statement.setInt(2, objeto.getIdProduto());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}
