package View.Swing;

import Service.ClienteService;
import Service.ProdutoService;
//import View.Swing.Panels.ClientePanel;
import View.Swing.Panels.ProdutoPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(ProdutoService produtoService, ClienteService clienteService) {
        setTitle("AcrilService - Gestão Comercial");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Add Modules
        tabbedPane.addTab("Clientes", new JPanel());
        tabbedPane.addTab("Produtos", new ProdutoPanel(produtoService));
        //tabbedPane.addTab("Clientes", new ClientePanel(clienteService));

        // Placeholders for future modules
        tabbedPane.addTab("Orçamentos", new JPanel());
        tabbedPane.addTab("Vendas", new JPanel());
        tabbedPane.addTab("Avaliações", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}