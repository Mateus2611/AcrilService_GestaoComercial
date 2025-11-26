package View.Swing.Dialogs;

import Model.Cliente;
import Model.Orcamento;
import Model.OrcamentoProduto;
import Model.Produto;
import Service.ClienteService;
import Service.OrcamentoProdutoService;
import Service.OrcamentoService;
import Service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDialog extends JDialog {

    private final OrcamentoService orcamentoService;
    private final OrcamentoProdutoService orcamentoProdutoService;
    private Orcamento orcamento; // Null if creating new

    private JComboBox<ClienteItem> cbClientes;
    private JTextField txtValidade;
    private JTextField txtDesconto;
    private JComboBox<Orcamento.StatusOrcamento> cbStatus;

    // Product Addition Controls
    private JComboBox<ProdutoItem> cbProdutos;
    private JTextField txtQuantidade;
    private DefaultTableModel productTableModel;
    // List to hold products before saving to DB
    private List<OrcamentoProduto> tempProducts = new ArrayList<>();

    public OrcamentoDialog(Frame parent,
                           OrcamentoService orcService,
                           OrcamentoProdutoService orcProdService,
                           ClienteService cliService,
                           ProdutoService prodService,
                           Orcamento orcamento) {
        super(parent, orcamento == null ? "Novo Orçamento" : "Detalhes do Orçamento", true);
        this.orcamentoService = orcService;
        this.orcamentoProdutoService = orcProdService;
        this.orcamento = orcamento;

        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // --- Header Panel (Client & Settings) ---
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBorder(BorderFactory.createTitledBorder("Dados do Orçamento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Client Selection
        gbc.gridx = 0; gbc.gridy = 0;
        headerPanel.add(new JLabel("Cliente:"), gbc);
        cbClientes = new JComboBox<>();
        loadClientes(cliService);
        gbc.gridx = 1; gbc.weightx = 1.0;
        headerPanel.add(cbClientes, gbc);

        // Validity (Days)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        headerPanel.add(new JLabel("Validade (dias):"), gbc);
        txtValidade = new JTextField("15", 10);
        gbc.gridx = 1;
        headerPanel.add(txtValidade, gbc);

        // Discount
        gbc.gridx = 0; gbc.gridy = 2;
        headerPanel.add(new JLabel("Desconto (R$):"), gbc);
        txtDesconto = new JTextField("0.00", 10);
        gbc.gridx = 1;
        headerPanel.add(txtDesconto, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        headerPanel.add(new JLabel("Status:"), gbc);
        cbStatus = new JComboBox<>(Orcamento.StatusOrcamento.values());
        cbStatus.setSelectedItem(Orcamento.StatusOrcamento.PENDENTE);
        gbc.gridx = 1;
        headerPanel.add(cbStatus, gbc);

        add(headerPanel, BorderLayout.NORTH);

        // --- Center Panel (Products) ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Produtos"));

        // Product Selection Controls
        JPanel prodSelectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbProdutos = new JComboBox<>();
        loadProdutos(prodService);
        txtQuantidade = new JTextField("1", 5);
        JButton btnAddProd = new JButton("Adicionar Produto");

        prodSelectPanel.add(new JLabel("Produto:"));
        prodSelectPanel.add(cbProdutos);
        prodSelectPanel.add(new JLabel("Qtd:"));
        prodSelectPanel.add(txtQuantidade);
        prodSelectPanel.add(btnAddProd);

        centerPanel.add(prodSelectPanel, BorderLayout.NORTH);

        // Product Table
        String[] cols = {"ID Produto", "Nome", "Valor Unit.", "Quantidade", "Subtotal"};
        productTableModel = new DefaultTableModel(cols, 0);
        JTable prodTable = new JTable(productTableModel);
        centerPanel.add(new JScrollPane(prodTable), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Panel (Actions) ---
        JPanel bottomPanel = new JPanel();
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Fechar");
        bottomPanel.add(btnSave);
        bottomPanel.add(btnCancel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Logic ---

        // Add Product Button
        btnAddProd.addActionListener(e -> {
            ProdutoItem item = (ProdutoItem) cbProdutos.getSelectedItem();
            if (item == null) return;

            try {
                int qtd = Integer.parseInt(txtQuantidade.getText());
                if (qtd <= 0) throw new NumberFormatException();

                BigDecimal subtotal = item.produto.getValor().multiply(new BigDecimal(qtd));

                // Add to Visual Table
                productTableModel.addRow(new Object[]{
                        item.produto.getId(),
                        item.produto.getNome(),
                        item.produto.getValor(),
                        qtd,
                        subtotal
                });

                // Add to Logic List (ID Orcamento is null for now, will be set after creation)
                tempProducts.add(new OrcamentoProduto(null, item.produto.getId(), qtd));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            }
        });

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        // Load existing data if editing
        if (orcamento != null) {
            setupEditingMode(orcamentoService, prodService);
        }
    }

    private void save() {
        try {
            ClienteItem cliItem = (ClienteItem) cbClientes.getSelectedItem();
            int validade = Integer.parseInt(txtValidade.getText());
            BigDecimal desconto = new BigDecimal(txtDesconto.getText().replace(",", "."));
            Orcamento.StatusOrcamento status = (Orcamento.StatusOrcamento) cbStatus.getSelectedItem();

            if (orcamento == null) {
                // --- Create New Budget ---
                Orcamento novo = new Orcamento(
                        cliItem.cliente.getId(),
                        BigDecimal.ZERO, // Value is recalculated by service/db logic
                        status,
                        desconto
                );

                // 1. Create Header
                Orcamento created = orcamentoService.Criar(novo, validade);

                // 2. Add Products
                for (OrcamentoProduto op : tempProducts) {
                    op.setIdOrcamento(created.getId());
                    orcamentoProdutoService.Criar(op);
                }
                JOptionPane.showMessageDialog(this, "Orçamento criado com sucesso!");
            } else {
                // --- Update Budget ---
                // Note: Editing products is complex (requires diffing list).
                // Here we update Status and Discount as supported by your DAO.
                orcamento.setStatus(status);
                orcamento.setDesconto(desconto);
                orcamentoService.Atualizar(orcamento.getId(), orcamento); //

                // Note: To support editing products, you would need to delete existing
                // items via OrcamentoProdutoService and re-add them, or implement diff logic.

                JOptionPane.showMessageDialog(this, "Orçamento atualizado.");
            }
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void setupEditingMode(OrcamentoService service, ProdutoService prodService) {
        // For editing, we disable Client and Validity changes as they are core to the creation
        cbClientes.setEnabled(false);
        txtValidade.setEnabled(false);

        // Select Client in ComboBox
        for(int i=0; i<cbClientes.getItemCount(); i++) {
            if(cbClientes.getItemAt(i).cliente.getId().equals(orcamento.getIdCliente())) {
                cbClientes.setSelectedIndex(i);
                break;
            }
        }

        txtDesconto.setText(orcamento.getDesconto() != null ? orcamento.getDesconto().toString() : "0.00");
        cbStatus.setSelectedItem(orcamento.getStatus());

        // Load products into the table for viewing
        // Note: In your current logic, Orcamento object might usually contain product names but not raw IDs/Qtys directly in a list structure.
        // You might need to fetch them via OrcamentoProdutoDAO if they aren't in the object.
        // Based on your OrcamentoService.BuscaGeral, names are loaded but not the raw entities list.
        // We will just show what we have.
    }

    private void loadClientes(ClienteService service) {
        List<Cliente> list = service.BuscaGeral();
        for (Cliente c : list) cbClientes.addItem(new ClienteItem(c));
    }

    private void loadProdutos(ProdutoService service) {
        List<Produto> list = service.BuscaGeral();
        for (Produto p : list) cbProdutos.addItem(new ProdutoItem(p));
    }

    // Helper Wrappers for ComboBox display
    class ClienteItem {
        Cliente cliente;
        public ClienteItem(Cliente c) { this.cliente = c; }
        public String toString() { return cliente.getNome(); }
    }
    class ProdutoItem {
        Produto produto;
        public ProdutoItem(Produto p) { this.produto = p; }
        public String toString() { return produto.getNome(); }
    }
}