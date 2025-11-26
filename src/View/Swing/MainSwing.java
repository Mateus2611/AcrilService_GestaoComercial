package View.Swing;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.*;
import Service.*;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.sql.Connection;

public class MainSwing {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch( Exception ex ) {
            System.err.println("Falha ao inciar LaF");
        }

        // Initialize Database and Services
        try {
            Connection conn = ConexaoDb.openConnection(); //

            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            ClienteDAO clienteDAO = new ClienteDAO(conn);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
            EmailDAO emailDAO = new EmailDAO(conn);
            VendaDAO vendaDAO = new VendaDAO(conn);
            OrcamentoDAO orcamentoDAO = new OrcamentoDAO(conn);
            OrcamentoProdutoDAO orcamentoProdutoDAO = new OrcamentoProdutoDAO(conn);
            AvaliacaoDAO avaliacaoDAO = new AvaliacaoDAO(conn);

            // Services
            ProdutoService produtoService = new ProdutoService(produtoDAO);
            ClienteService clienteService = new ClienteService(clienteDAO, enderecoDAO, emailDAO);
            OrcamentoService orcamentoService = new OrcamentoService(orcamentoDAO, clienteDAO, orcamentoProdutoDAO, produtoDAO);
            VendaService vendaService = new VendaService(vendaDAO, orcamentoDAO);
            AvaliacaoService avaliacaoService = new AvaliacaoService(avaliacaoDAO, vendaService);
            OrcamentoProdutoService orcamentoProdutoService = new OrcamentoProdutoService(orcamentoProdutoDAO);

            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(produtoService, clienteService, orcamentoService, orcamentoProdutoService, vendaService, avaliacaoService);
                frame.setVisible(true);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + e.getMessage());
        }
    }
}