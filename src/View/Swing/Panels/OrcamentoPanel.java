package View.Swing.Panels;

import Model.Orcamento;
import Service.ClienteService;
import Service.OrcamentoProdutoService;
import Service.OrcamentoService;
import Service.ProdutoService;
import View.Swing.Dialogs.OrcamentoDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrcamentoPanel extends JPanel {

    private final OrcamentoService orcamentoService;
    private final OrcamentoProdutoService orcamentoProdutoService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private JTable table;
    private DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public OrcamentoPanel(OrcamentoService orcService, OrcamentoProdutoService orcProdService,
                          ClienteService cliService, ProdutoService prodService) {
        this.orcamentoService = orcService;
        this.orcamentoProdutoService = orcProdService;
        this.clienteService = cliService;
        this.produtoService = prodService;

        setLayout(new BorderLayout());

        // --- Toolbar ---
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Novo Orçamento");
        JButton btnDetails = new JButton("Ver Detalhes / Editar");
        JButton btnRefresh = new JButton("Atualizar Lista");

        toolBar.add(btnAdd);
        toolBar.add(btnDetails);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);
        add(toolBar, BorderLayout.NORTH);

        // --- Table ---
        // Columns: ID, Client Name, Creation Date, Expiration Date, Status, Total Value
        String[] columns = {"ID", "Cliente", "Data Criação", "Validade", "Status", "Valor Total"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);

        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Actions ---
        btnAdd.addActionListener(e -> openDialog(null));

        btnDetails.addActionListener(e -> {
            Orcamento orc = getSelectedOrcamento();
            if (orc != null) openDialog(orc);
        });

        btnRefresh.addActionListener(e -> loadData());

        // Initial Load
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            // Fetch all budgets. Your Service logic already populates Client Name and calculates Total Value.
            List<Orcamento> lista = orcamentoService.BuscaGeral(); //

            for (Orcamento o : lista) {
                tableModel.addRow(new Object[]{
                        o.getId(),
                        o.getNomeCliente(), // Service populates this
                        o.getDataCriacao() != null ? dateFormat.format(o.getDataCriacao()) : "",
                        o.getDataValidade() != null ? dateFormat.format(o.getDataValidade()) : "",
                        o.getStatus(),
                        String.format("R$ %.2f", o.getValor()) // Service calculates this
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar orçamentos: " + e.getMessage());
        }
    }

    private Orcamento getSelectedOrcamento() {
        int row = table.getSelectedRow();
        if (row != -1) {
            Integer id = (Integer) tableModel.getValueAt(row, 0);
            // Fetch full object by ID to ensure we have all details
            return orcamentoService.BuscaPorId(id); //
        }
        JOptionPane.showMessageDialog(this, "Selecione um orçamento.");
        return null;
    }

    private void openDialog(Orcamento orcamento) {
        OrcamentoDialog dialog = new OrcamentoDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                orcamentoService, orcamentoProdutoService, clienteService, produtoService,
                orcamento
        );
        dialog.setVisible(true);
        loadData(); // Refresh table after dialog closes
    }
}