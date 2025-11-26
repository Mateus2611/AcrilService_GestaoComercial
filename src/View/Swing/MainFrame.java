package View.Swing;

import Service.*;
//import View.Swing.Panels.ClientePanel;
import View.Swing.Panels.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(ProdutoService produtoService, ClienteService clienteService, OrcamentoService orcamentoService,
                     OrcamentoProdutoService orcamentoProdutoService, VendaService vendaService, AvaliacaoService avaliacaoService) {
        setTitle("AcrilService - Gestão Comercial");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Placeholders for future modules
        tabbedPane.addTab("Vendas", new VendaPanel(vendaService, orcamentoService));
        tabbedPane.addTab("Orçamentos", new OrcamentoPanel
                (orcamentoService, orcamentoProdutoService, clienteService, produtoService));
        tabbedPane.addTab("Avaliações", new AvaliacaoPanel(avaliacaoService, vendaService));
        tabbedPane.addTab("Clientes", new ClientePanel(clienteService));
        tabbedPane.addTab("Produtos", new ProdutoPanel(produtoService));

        add(tabbedPane, BorderLayout.CENTER);
    }
}