package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Cliente;
import Model.Endereco;
import Model.Orcamento;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final Connection _connection;

    public ClienteDAO(Connection connection) {
        _connection = connection;
    }

    public Cliente Criar(Cliente objeto) {
        PreparedStatement statement = null;
        objeto.setDataCadastro(Date.valueOf(LocalDate.now()));

        try {

            statement = _connection.prepareStatement(
                    "INSERT INTO `Cliente` (`Id_Endereco`, `Nome`, `Tipo`, `Data_Cadastro`, `Status_Cliente`) " +
                            "VALUES (?, ?, ?, ?, 'ATIVO');",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdEndereco());
            statement.setString(2, objeto.getNome());
            statement.setString(3, objeto.getTipo().name());
            statement.setDate(4, objeto.getDataCadastro());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar endereço");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            objeto = BuscaPorId(objeto.getId());

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public List<Cliente> BuscaGeral() {
        PreparedStatement statement = null;
        List<Cliente> clientes = new ArrayList<Cliente>();

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Cliente`;"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String clienteTipo = resultSet.getString("Tipo");
                Cliente.TipoCliente tipo= null;

                String statusCliente = resultSet.getString("Status_Cliente");
                Cliente.StatusCliente status = null;

                if ((clienteTipo != null) && (statusCliente != null)) {
                    try {

                        tipo = Cliente.TipoCliente.valueOf(clienteTipo.toUpperCase());
                        status = Cliente.StatusCliente.valueOf(statusCliente.toUpperCase());

                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                }

                Cliente objeto = new Cliente(
                        resultSet.getInt("Id"),
                        resultSet.getString("Nome"),
                        resultSet.getInt("Id_Endereco"),
                        resultSet.getDate("Data_Cadastro"),
                        resultSet.getDate("Data_Inativacao"),
                        tipo,
                        status);

                clientes.add(objeto);
            }

            if (clientes != null)
                return clientes;

            throw new RuntimeException("Não existem endereços cadastrados");

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Cliente BuscaPorId(Integer id) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Cliente` WHERE Id = ?;"
            );

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next())
                throw new RuntimeException("Cliente não encontrado.");

            String clienteTipo = resultSet.getString("Tipo");
            Cliente.TipoCliente tipo= null;

            String statusCliente = resultSet.getString("Status_Cliente");
            Cliente.StatusCliente status = null;

            if ((clienteTipo != null) && (statusCliente != null)) {
                try {

                    tipo = Cliente.TipoCliente.valueOf(clienteTipo.toUpperCase());
                    status = Cliente.StatusCliente.valueOf(statusCliente.toUpperCase());

                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }

            Cliente objeto = new Cliente(
                    resultSet.getInt("Id"),
                    resultSet.getString("Nome"),
                    resultSet.getInt("Id_Endereco"),
                    resultSet.getDate("Data_Cadastro"),
                    resultSet.getDate("Data_Inativacao"),
                    tipo,
                    status);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void AtualizarTipoCliente(Integer id, String tipoCliente) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Tipo` = ? WHERE `Id` = ?;"
            );

            statement.setString(1, tipoCliente);
            statement.setInt(2, id);


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar cliente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void AtualizarNomeCliente(Integer id, String nome) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Nome` = ? WHERE `Id` = ?;"
            );

            statement.setString(1, nome);
            statement.setInt(2, id);


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar cliente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void AtualizarEnderecoCliente(Integer id, Integer idEndereco) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Id_Endereco` = ? WHERE `Id` = ?;"
            );

            statement.setInt(1, idEndereco);
            statement.setInt(2, id);


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar cliente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void InativarCliente(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Status_Cliente` = 'INATIVO', `Data_Inativacao` = ? WHERE `Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, id);

            int rowsAffectes = statement.executeUpdate();

            if (rowsAffectes <= 0)
                throw new RuntimeException("Erro ao inativar cliente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public void AtivarCliente(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Status_Cliente` = 'ATIVO' WHERE `Id` = ?;"
            );

            statement.setInt(1, id);

            int rowsAffectes = statement.executeUpdate();

            if (rowsAffectes <= 0)
                throw new RuntimeException("Erro ao ativar cliente.");
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}
