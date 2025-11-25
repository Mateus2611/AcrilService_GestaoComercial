package View.Swing;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.*;
import Service.*;
import javax.swing.*;
import java.sql.Connection;

public class MainSwing {

    public static void main(String[] args) {
        // Setup UI Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize Database and Services
        try {
            Connection conn = ConexaoDb.openConnection(); //

            // DAOs
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            ClienteDAO clienteDAO = new ClienteDAO(conn);
            EnderecoDAO enderecoDAO = new EnderecoDAO(conn);
            EmailDAO emailDAO = new EmailDAO(conn);
            // ... Initialize other DAOs similarly

            // Services
            ProdutoService produtoService = new ProdutoService(produtoDAO);
            ClienteService clienteService = new ClienteService(clienteDAO, enderecoDAO, emailDAO);
            // ... Initialize other Services similarly

            // Launch GUI
            SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame(produtoService, clienteService);
                frame.setVisible(true);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco: " + e.getMessage());
        }
    }
}