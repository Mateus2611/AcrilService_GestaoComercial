package View.Swing.Panels;

import Model.Venda;
import Service.OrcamentoService;
import Service.VendaService;
import View.Swing.Dialogs.VendaDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class VendaPanel extends JPanel {

    private final VendaService vendaService;
    private final OrcamentoService orcamentoService;
    private JTable table;
    private DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public VendaPanel(VendaService vendaService, OrcamentoService orcamentoService) {
        this.vendaService = vendaService;
        this.orcamentoService = orcamentoService;

        setLayout(new BorderLayout());

        // --- Toolbar ---
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Nova Venda");
        JButton btnEdit = new JButton("Processar Pagamento");
        JButton btnRefresh = new JButton("Atualizar Lista");

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);

        add(toolBar, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"ID", "ID Orçamento", "Data Criação", "Prazo Pagamento", "Data Conclusão", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Actions ---
        btnAdd.addActionListener(e -> openDialog(null));

        btnEdit.addActionListener(e -> {
            Venda v = getSelectedVenda();
            if (v != null) openDialog(v);
        });

        btnRefresh.addActionListener(e -> loadData());

        // Initial Load
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Venda> vendas = vendaService.BuscaGeral(); //
            for (Venda v : vendas) {
                tableModel.addRow(new Object[]{
                        v.getId(),
                        v.getIdOrcamento(),
                        v.getDataCriacao() != null ? dateFormat.format(v.getDataCriacao()) : "",
                        v.getPrazoPagamento() != null ? dateFormat.format(v.getPrazoPagamento()) : "",
                        v.getDataConclusao() != null ? dateFormat.format(v.getDataConclusao()) : "-",
                        v.getStatusPagamento()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas: " + ex.getMessage());
        }
    }

    private Venda getSelectedVenda() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            return vendaService.BuscaPorId(id); //
        }
        JOptionPane.showMessageDialog(this, "Selecione uma venda na lista.");
        return null;
    }

    private void openDialog(Venda venda) {
        VendaDialog dialog = new VendaDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                vendaService,
                orcamentoService,
                venda
        );
        dialog.setVisible(true);
        loadData();
    }
}