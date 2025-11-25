package DAO.MySQL;

import DAO.JDBC.ConexaoDb;
import Model.Orcamento;
import Model.Venda;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private final Connection _connection;

    public VendaDAO(Connection connection) {
        _connection = connection;
    }

    public Venda Criar(Venda objeto, Integer prazo) {
        PreparedStatement statement = null;
        objeto.setDataCriacao(Date.valueOf(LocalDate.now()));
        objeto.setPrazoPagamento(Date.valueOf(LocalDate.now().plusDays(prazo)));

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Venda` (`Id_Orcamento`, `Data_Criacao`, "
                            + "`Status_Pagamento`, `Prazo_Pagamento`) "
                            + "VALUES ( ?, ?, 'PENDENTE', ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdOrcamento());
            statement.setDate(2, objeto.getDataCriacao());
            statement.setDate(3, objeto.getPrazoPagamento());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir venda");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            objeto = BuscaId(objeto.getId());

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public boolean AtualizarStatusPagamento(Integer id, String statusPagamaneto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Venda` " +
                            "SET `Status_Pagamento` = ?, `Data_Conclusao` = ? " +
                            "WHERE `Id` = ?;"
            );

            statement.setString(1, statusPagamaneto);
            statement.setDate(2, (statusPagamaneto.equalsIgnoreCase("APROVADO") ?
                    Date.valueOf(LocalDate.now()) : null));
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public List<Venda> BuscaGeral() {
        PreparedStatement statement = null;
        List<Venda> vendas = new ArrayList<>();

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Venda`"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String statusPagamentoString = resultSet.getString("Status_Pagamento");
                Venda.StatusPagamento status = null;

                if (statusPagamentoString != null) {
                    try {
                        status = Venda.StatusPagamento.valueOf(statusPagamentoString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Status de pagamento inválido no DB: " + statusPagamentoString);
                    }
                }

                int id = resultSet.getInt("Id");
                int idOrcamento = resultSet.getInt("Id_Orcamento");
                Date dataCriacao = resultSet.getDate("Data_Criacao");
                Date prazoPagamento = resultSet.getDate("Prazo_Pagamento");
                Date dataConclusao = resultSet.getDate("Data_Conclusao");

                vendas.add(new Venda
                (
                    id,
                    idOrcamento,
                    dataCriacao,
                    prazoPagamento,
                    dataConclusao,
                    status
                ));
            }

            ConexaoDb.closeResultSet(resultSet);

            return vendas;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Venda BuscaId(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "SELECT * FROM `Venda` " +
                            "WHERE `Id` = ?;"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Venda objeto = null;

            objeto = new Venda();

            if (resultSet.next()) {

                String statusPagamentoString = resultSet.getString("Status_Pagamento");
                Venda.StatusPagamento status = null;

                if (statusPagamentoString != null) {
                    try {
                        status = Venda.StatusPagamento.valueOf(statusPagamentoString.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Status de pagamento inválido no DB: " + statusPagamentoString);
                    }
                }

                id = resultSet.getInt("Id");
                int idOrcamento = resultSet.getInt("Id_Orcamento");
                Date dataCriacao = resultSet.getDate("Data_Criacao");
                Date prazoPagamento = resultSet.getDate("Prazo_Pagamento");
                Date dataConclusao = resultSet.getDate("Data_Conclusao");

                objeto = new Venda(
                        id,
                        idOrcamento,
                        dataCriacao,
                        prazoPagamento,
                        dataConclusao,
                        status
                );
            }

            ConexaoDb.closeResultSet(resultSet);
            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}