package DAO.MySQL;

import DAO.Interfaces.IOperacoesGenericasDAO;
import DAO.JDBC.ConexaoDb;
import Model.Cliente;
import Model.Endereco;

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
            statement.setDate(4, currentDate);

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

    /*
    public List<Endereco> BuscaGeral() {
        PreparedStatement statement = null;
        List<Endereco> enderecos = null;

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Cliente`;"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Cliente objeto = new Endereco(
                        resultSet.getInt("Id"),
                        resultSet.getInt("Id_Endereco"),
                        resultSet.getString("Bairro"),
                        resultSet.getString("Estado"),
                        resultSet.getString("Cidade"),
                        resultSet.getString("Logradouro"));

                enderecos.add(objeto);
            }

            if (enderecos != null)
                return enderecos;

            throw new RuntimeException("Não existem endereços cadastrados");

        } catch (SQLException e) {
            throw new RuntimeException((e.getMessage()));
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    public Endereco BuscaPorId(Integer id) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "SELECT * FROM `Endereco` WHERE Id = ?;"
            );

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Endereco objeto = new Endereco(
                        resultSet.getInt("Id"),
                        resultSet.getString("CEP"),
                        resultSet.getString("Bairro"),
                        resultSet.getString("Estado"),
                        resultSet.getString("Cidade"),
                        resultSet.getString("Logradouro"));

                return objeto;
            }

            throw new RuntimeException("Não foi encontrado endereço correspondente.");

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            ConexaoDb.closeStatement(statement);
        }
    }

    @Override
    public Endereco Atualizar(Integer id, Endereco objeto) {
        PreparedStatement statement = null;

        try {

            statement = _connection.prepareStatement(
                    "UPDATE `Endereco` SET `CEP` = ?, `Bairro` = ?, `Estado` = ?, `Cidade` = ?, `Logradouro` = ? WHERE `endereco`.`Id` = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, objeto.getCep());
            statement.setString(2, objeto.getBairro());
            statement.setString(3, objeto.getEstado());
            statement.setString(4, objeto.getCidade());
            statement.setString(5, objeto.getLogradouro());
            statement.setInt(6, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected <= 0)
                throw new RuntimeException("Erro ao atualizar endereço");

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
     */
}
