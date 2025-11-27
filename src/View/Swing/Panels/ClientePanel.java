package View.Swing.Panels;

import Model.Cliente;
import Service.ClienteService;
import View.Swing.Dialogs.ClienteDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ClientePanel extends JPanel {

    private final ClienteService clienteService;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClientePanel(ClienteService service) {
        this.clienteService = service;
        setLayout(new BorderLayout());

        // --- Toolbar ---
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAdd = new JButton("Novo Cliente");
        JButton btnEdit = new JButton("Informações do Cliente");
        JButton btnToggleStatus = new JButton("Ativar/Inativar");
        JButton btnRefresh = new JButton("Atualizar Lista");

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnToggleStatus);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnRefresh);

        add(toolBar, BorderLayout.NORTH);

        // --- Table ---
        String[] columns = {"ID", "Nome", "Tipo", "Localização", "Status"};
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
            Cliente c = getSelectedCliente();
            if (c != null) openDialog(c);
        });

        btnToggleStatus.addActionListener(e -> toggleStatus());

        btnRefresh.addActionListener(e -> loadData(null));

        // Initial Load
        loadData(null);
    }

    private void loadData(Integer id) {
        tableModel.setRowCount(0);
        try {
            // Fetch all clients including embedded Address info
            List<Cliente> clientes = clienteService.BuscaGeral();
            for (Cliente c : clientes) {
                String localizacao = "N/A";
                if (c.getEndereco() != null) {
                    localizacao = c.getEndereco().Logradouro + ", " + c.getEndereco().Bairro + ", " + c.getEndereco().Cidade + " - " + c.getEndereco().Estado + ", " + c.getEndereco().Cep ;
                }

                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getTipo(),
                        localizacao,
                        c.getStatus()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private Cliente getSelectedCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            // Always fetch fresh data from DB to ensure we have Emails and Address loaded
            return clienteService.BuscaPorId(id);
        }
        JOptionPane.showMessageDialog(this, "Selecione um cliente na lista.");
        return null;
    }

    private void toggleStatus() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String currentStatus = tableModel.getValueAt(selectedRow, 4).toString();

        try {
            if ("ATIVO".equals(currentStatus)) {
                if (JOptionPane.showConfirmDialog(this, "Deseja inativar este cliente?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    clienteService.InativarCliente(id); //
                }
            } else {
                clienteService.AtivarCliente(id); //
            }
            loadData(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar status: " + ex.getMessage());
        }
    }

    private void openDialog(Cliente cliente) {
        ClienteDialog dialog = new ClienteDialog((Frame) SwingUtilities.getWindowAncestor(this), clienteService, cliente);
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData(null);
        }
    }
}