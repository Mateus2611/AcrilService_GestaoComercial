package DAO.MySQL;

//*Aqui está o código AvaliacaoDAO com o novo método implementado.

//Eu o nomeei buscaPorIdVenda para manter o padrão de nomenclatura (como BuscaId) e a lógica Java (camelCase).
//Também corrigi um pequeno bug no seu método  BuscaGeral que causaria um NullPointerException

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Avaliacao;
import Model.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId; // Import não mais necessário após correção
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

        // CORREÇÃO: O PreparedStatement.setDate() espera um java.sql.Date.
        // Esta é a forma correta de obtê-lo a partir do LocalDate.
        java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());


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
                            "WHERE `Id_avaliacao` = ?;"
                    // Removido RETURN_GENERATED_KEYS, desnecessário para update.
            );

            statement.setInt(1, objeto.getIdVenda());
            statement.setString(2, objeto.getTitulo());
            statement.setString(3, objeto.getDescricao());
            statement.setFloat(4, objeto.getNota());
            statement.setInt(5, objeto.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar avaliação.");

            // Removido bloco de getGeneratedKeys, o ID não muda no update.

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar avaliação: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public List<Avaliacao> BuscaGeral() {
        PreparedStatement statement = null;

        // CORREÇÃO: A lista precisa ser inicializada, senão você terá um NullPointerException
        // na linha avaliacoes.add(objeto).
        List<Avaliacao> avaliacoes = new ArrayList<>();

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

    // ==========================================================
    // == NOVO MÉTODO (consultar_avaliacao_venda) INICIA AQUI ==
    // ==========================================================

    /**
     * Busca uma avaliação específica com base no ID da Venda associada.
     * Este método assume que uma Venda só pode ter uma Avaliação (relação 1 para 1).
     *
     * @param idVenda O ID da Venda (Id_Venda) para filtrar a busca.
     * @return O objeto Avaliacao encontrado, ou null se nenhuma avaliação
     * corresponder àquele ID de Venda.
     */
    public Avaliacao buscaPorIdVenda(Integer idVenda) {
        PreparedStatement statement = null;

        try {
            // A query SQL filtra pela coluna `Id_Venda`
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Avaliacao` " +
                            "WHERE `Id_Venda` = ?;"
            );

            // Define o parâmetro da query (o '?' na string SQL) com o ID recebido
            statement.setInt(1, idVenda);

            ResultSet resultSet = statement.executeQuery();

            Avaliacao objeto = null;

            // Usamos if (resultSet.next()) porque esperamos apenas UM resultado
            if (resultSet.next()) {
                // Lê os dados do resultado do banco
                int idVendaResultado = resultSet.getInt("Id_Venda");
                int idAvaliacao = resultSet.getInt("Id_avaliacao");
                String titulo = resultSet.getString("Titulo");
                String descricao = resultSet.getString("descricao");
                java.util.Date dataCriacao = resultSet.getDate("Data_Criação");
                float nota = resultSet.getFloat("Nota");

                // Cria o objeto Avaliacao com os dados
                objeto = new Avaliacao(
                        idAvaliacao,
                        idVendaResultado,
                        nota,
                        dataCriacao,
                        descricao,
                        titulo
                );
            }

            ConexaoDb.closeResultSet(resultSet);
            return objeto; // Retorna a avaliação encontrada (ou null se não achou)

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliação por ID da Venda: " + e.getMessage(), e);
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    // ========================================================
    // == NOVO MÉTODO (consultar_avaliacao_venda) TERMINA AQUI ==
    // ========================================================


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
