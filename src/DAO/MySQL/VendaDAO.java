package DAO.MySQL;

import DAO.JDBC.ConexaoDb;
import Model.Orcamento;
import Model.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private final Connection _connection;

    public VendaDAO(Connection connection) {
        _connection = connection;
    }

    public Venda Criar(Venda objeto) {
        PreparedStatement statement = null;
        LocalDate localDate = LocalDate.now();
        Date currentDate = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());;

        try {
            statement = _connection.prepareStatement(
                    "INSERT INTO `Venda` (`Id_Orcamento`, `Data_Criacao`, "
                            + "`Status_Pagamento`, `Prazo_Pagamento`, `Data_Conclusao`) "
                            + "VALUES ( ?, ?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdOrcamento());
            statement.setDate(2, currentDate);
            statement.setString(3, objeto.getStatusPagamento().name());
            statement.setDate(4, new java.sql.Date(objeto.getPrazoPagamento().getTime()));

            if (objeto.getDataConclusao() != null) {
                statement.setDate(5, new java.sql.Date(objeto.getDataConclusao().getDate()));
            } else {
                statement.setNull(5, java.sql.Types.DATE);
            }

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao inserir venda");

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

    public boolean AtualizarStatusPagamento(Integer id, Enum<Venda.StatusPagamento> statusPagamaneto) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Venda` " +
                            "SET `Status_Pagamento` = ? " +
                            "WHERE `Id_Venda` = ?;"
            );

            statement.setString(1, statusPagamaneto.name());
            statement.setInt(2, id);

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

                Integer idOrcamento = resultSet.getInt("Id_Orcamento");
                java.util.Date dataCriacao = resultSet.getDate("Data_Criacao");
                java.util.Date prazoPagamento = resultSet.getDate("Prazo_Pagamento");
                java.util.Date dataConclusao = resultSet.getDate("Data_Conclusao");

                Venda objeto = new Venda(
                        idOrcamento,
                        dataCriacao,
                        prazoPagamento,
                        dataConclusao,
                        status
                );

                objeto.setId(resultSet.getInt("Id_Venda"));
                vendas.add(objeto);
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
                            "WHERE `Id_Venda` = ?;"
            );
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            Venda objeto = null;

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

                int idOrcamento = resultSet.getInt("Id_Orcamento");
                java.util.Date dataCriacao = resultSet.getDate("Data_Criacao");
                java.util.Date prazoPagamento = resultSet.getDate("Prazo_Pagamento");
                java.util.Date dataConclusao = resultSet.getDate("Data_Conclusao");

                objeto = new Venda(
                        idOrcamento,
                        dataCriacao,
                        prazoPagamento,
                        dataConclusao,
                        status
                );

                objeto.setId(resultSet.getInt("Id_Venda"));
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