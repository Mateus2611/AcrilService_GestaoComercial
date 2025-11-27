package View.Swing.Panels;

import Model.Produto;
import Service.ProdutoService;
import View.Swing.Dialogs.ProdutoDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ProdutoPanel extends JPanel {

    private final ProdutoService produtoService;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProdutoPanel(ProdutoService service) {
        this.produtoService = service;
        setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Novo Produto");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnRefresh = new JButton("Atualizar Lista");

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnDelete);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);

        add(toolBar, BorderLayout.NORTH);

        //Colunas
        String[] columns = {"ID", "Nome", "Valor"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1 && e.getClickCount() == 2) {
                    Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                    Produto p = produtoService.BuscaPorId(id);
                    openDialog(p);
                }
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> openDialog(null));

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                Produto p = produtoService.BuscaPorId(id);
                openDialog(p);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
            }
        });

        btnDelete.addActionListener(e -> deleteProduct());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            List<Produto> produtos = produtoService.BuscaGeral(); //
            for (Produto p : produtos) {
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), String.format("R$ %.2f", p.getValor())});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produtos: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
                produtoService.Excluir(id);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }

    private void openDialog(Produto produto) {
        ProdutoDialog dialog = new ProdutoDialog((Frame) SwingUtilities.getWindowAncestor(this), produtoService, produto);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData();
        }
    }
}