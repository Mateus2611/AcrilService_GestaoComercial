package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Cliente;
import Model.Endereco;
import Model.Orcamento;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ClienteDAO {

    private final Connection _connection;

    public ClienteDAO(Connection connection) {
        _connection = connection;
    }

    public Cliente Criar(Cliente objeto, Integer idEndereco) {
        PreparedStatement statement = null;
        LocalDate localDate = LocalDate.now();
        Date currentDate = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());;

        try {

            statement = _connection.prepareStatement(
                    "IINSERT INTO `Cliente` (`Id_Endereco`, `Nome`, `Tipo`, `Data_Cadastro`, `Status_Cliente`) " +
                            "VALUES (?, ?, ?, ?, `ATIVO`);",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, idEndereco);
            statement.setString(2, objeto.getNome());
            statement.setString(3, objeto.getTipo().name());
            statement.setDate(4, (Date) currentDate);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao criar cliente.");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

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
        List<Cliente> clientes = null;

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

    public Cliente Atualizar(Integer id, Cliente objeto) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Id_Endereco` = ?, `Nome` = ?, `Tipo` = ?, `Data_Cadastro` = ?, `Status_Cliente` = ?, `Data_Inativacao` = ? WHERE `cliente`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, objeto.getIdEndereco());
            statement.setString(2, objeto.getNome());
            statement.setString(3, objeto.getTipo().name());
            statement.setDate(4, objeto.getDataCadastro());
            statement.setString(5, objeto.getStatus().name());
            statement.setDate(6, objeto.getDataInativacao());
            statement.setInt(7, id);


            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar cliente.");

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next())
                objeto.setId(resultSet.getInt(1));

            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Cliente InativarCliente(Integer id) {
        PreparedStatement statement = null;
        LocalDate localDate = LocalDate.now();
        Date currentDate = (Date) Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Status_Cliente` = 'INATIVO', `Data_Inativacao` = ? WHERE `Cliente`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setDate(1, currentDate);
            statement.setInt(2, id);

            int rowsAffectes = statement.executeUpdate();

            if (rowsAffectes <= 0)
                throw new RuntimeException("Erro ao inativar cliente.");

            ResultSet resultSet = statement.getGeneratedKeys();

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

            Cliente objeto = new Cliente(resultSet.getInt("Id"),
                                        resultSet.getString("Nome"),
                                        resultSet.getInt("Id_Endereco"),
                                        resultSet.getDate("Data_Cadastro"),
                                        resultSet.getDate("Data_Inativação"),
                                        tipo,
                                        status);


            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Cliente AtivarCliente(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = _connection.prepareStatement(
                    "UPDATE `Cliente` SET `Status_Cliente` = 'ATIVO' WHERE `Cliente`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setInt(1, id);

            int rowsAffectes = statement.executeUpdate();

            if (rowsAffectes <= 0)
                throw new RuntimeException("Erro ao ativar cliente.");

            ResultSet resultSet = statement.getGeneratedKeys();

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

            Cliente objeto = new Cliente(resultSet.getInt("Id"),
                    resultSet.getString("Nome"),
                    resultSet.getInt("Id_Endereco"),
                    resultSet.getDate("Data_Cadastro"),
                    resultSet.getDate("Data_Inativação"),
                    tipo,
                    status);


            ConexaoDb.closeResultSet(resultSet);

            return objeto;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }
}
