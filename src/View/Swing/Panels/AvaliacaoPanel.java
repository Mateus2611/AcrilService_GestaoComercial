package View.Swing.Panels;

import Model.Avaliacao;
import Service.AvaliacaoService;
import Service.VendaService;
import View.Swing.Dialogs.AvaliacaoDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class AvaliacaoPanel extends JPanel {

    private final AvaliacaoService avaliacaoService;
    private final VendaService vendaService;
    private JTable table;
    private DefaultTableModel tableModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public AvaliacaoPanel(AvaliacaoService avaliacaoService, VendaService vendaService) {
        this.avaliacaoService = avaliacaoService;
        this.vendaService = vendaService;

        setLayout(new BorderLayout());

        // --- Toolbar ---
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Nova Avaliação");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnRefresh = new JButton("Atualizar Lista");

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnDelete);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);

        add(toolBar, BorderLayout.NORTH);

        // --- Table ---
        // Columns: ID, Sale ID, Title, Rating, Date
        String[] columns = {"ID", "ID Venda", "Título", "Nota", "Data"};
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
            Avaliacao a = getSelectedAvaliacao();
            if (a != null) openDialog(a);
        });

        btnDelete.addActionListener(e -> deleteAvaliacao());

        btnRefresh.addActionListener(e -> loadData());

        // Initial Load
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Avaliacao> lista = avaliacaoService.BuscaGeral(); //
            for (Avaliacao a : lista) {
                tableModel.addRow(new Object[]{
                        a.getId(),
                        a.getIdVenda(),
                        a.getTitulo(),
                        String.format("%.1f", a.getNota()),
                        a.getDataCriacao() != null ? dateFormat.format(a.getDataCriacao()) : ""
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar avaliações: " + ex.getMessage());
        }
    }

    private Avaliacao getSelectedAvaliacao() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            return avaliacaoService.BuscaPorId(id); //
        }
        JOptionPane.showMessageDialog(this, "Selecione uma avaliação na lista.");
        return null;
    }

    private void deleteAvaliacao() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma avaliação para excluir.");
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Deseja excluir esta avaliação?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                avaliacaoService.Excluir(id); //
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    private void openDialog(Avaliacao avaliacao) {
        AvaliacaoDialog dialog = new AvaliacaoDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                avaliacaoService,
                vendaService,
                avaliacao
        );
        dialog.setVisible(true);
        if(dialog.isSaved()) {
            loadData();
        }
    }
}