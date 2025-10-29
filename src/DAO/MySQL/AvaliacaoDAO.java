package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Avaliacao;
import Model.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO implements IOperacoesGenericasDAO<Integer, Avaliacao> {

    private final Connection _connection;

    public AvaliacaoDAO(Connection connection) {
        _connection = connection;
    }

    @Override
    public Avaliacao Criar(Avaliacao objeto) {
        PreparedStatement statement = null;
        LocalDate localDate = LocalDate.now();
        Date currentDate = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());;


        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Avaliacao` (`Id_Venda`, `Titulo`, `descricao`, `Data_Criação`, `Nota`) "
                            + "VALUES (?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdVenda());
            statement.setString(2, objeto.getTitulo());
            statement.setString(3, objeto.getDescricao());
            statement.setDate(4, currentDate);
            statement.setFloat(5, objeto.getNota());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir avaliação");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar avaliação: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
        return objeto;
    }

    @Override
    public Avaliacao Atualizar(Integer id, Avaliacao objeto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Avaliacao` " +
                            "SET `Id_Venda` = ?, `Titulo` = ?, `descricao` = ?, `Nota` = ? " +
                            "WHERE `Id_avaliacao` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdVenda());
            statement.setString(2, objeto.getTitulo());
            statement.setString(3, objeto.getDescricao());
            statement.setFloat(4, objeto.getNota());
            statement.setInt(5, objeto.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar avaliação.");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar avaliação: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public List<Avaliacao> BuscaGeral() {
        PreparedStatement statement = null;
        List<Avaliacao> avaliacoes = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Avaliacao`"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idVenda = resultSet.getInt("Id_Venda");
                int idAvaliacao = resultSet.getInt("Id_avaliacao");
                String titulo = resultSet.getString("Titulo");
                String descricao = resultSet.getString("descricao");
                java.util.Date dataCriacao = resultSet.getDate("Data_Criação");
                float nota = resultSet.getFloat("Nota");

                Avaliacao objeto = new Avaliacao(
                        idAvaliacao,
                        idVenda,
                        nota,
                        dataCriacao,
                        descricao,
                        titulo
                );

                avaliacoes.add(objeto);
            }

            ConexaoDb.closeResultSet(resultSet);
            return avaliacoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Avaliacao BuscaId(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Avaliacao` " +
                            "WHERE `Id_avaliacao` = ?;"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Avaliacao objeto = null;

            if (resultSet.next()) {
                int idVenda = resultSet.getInt("Id_Venda");
                int idAvaliacao = resultSet.getInt("Id_avaliacao");
                String titulo = resultSet.getString("Titulo");
                String descricao = resultSet.getString("descricao");
                java.util.Date dataCriacao = resultSet.getDate("Data_Criação");
                float nota = resultSet.getFloat("Nota");

                objeto = new Avaliacao(
                        idAvaliacao,
                        idVenda,
                        nota,
                        dataCriacao,
                        descricao,
                        titulo
                );
            }

            ConexaoDb.closeResultSet(resultSet);
            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação por ID: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public void Excluir(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "DELETE FROM `Avaliacao` WHERE `Id_avaliacao` = ?;"
            );
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar avaliação: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}