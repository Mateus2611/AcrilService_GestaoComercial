package DAO.MySQL;

import DAO.JDBC.ConexaoDb;
import Model.OrcamentoProduto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoProdutoDAO {

    private final Connection _connection;

    public OrcamentoProdutoDAO(Connection connection) {
        _connection = connection;
    }

    public OrcamentoProduto AdicionarProduto(OrcamentoProduto objeto) {
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
        return objeto;
    }

    public List<OrcamentoProduto> BuscaOrcamentoId(int idOrcamento) {
        PreparedStatement statement = null;
        List<OrcamentoProduto> produtos = new ArrayList<>();

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Orcamento_Produto`" +
                            "WHERE `Id_Orcamento` = ?;"
            );

            statement.setInt(1, idOrcamento);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrcamentoProduto objeto = new OrcamentoProduto(
                        resultSet.getInt("Id_Orcamento"),
                        resultSet.getInt("Id_Produto"),
                        resultSet.getInt("Quantidade")
                );
                produtos.add(objeto);
            }

            ConexaoDb.closeResultSet(resultSet);
            return produtos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos do orçamento");
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public OrcamentoProduto AtualizarQuantidade(OrcamentoProduto objeto) {
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
        return objeto;
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
