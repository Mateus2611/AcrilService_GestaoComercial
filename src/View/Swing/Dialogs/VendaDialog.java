package View.Swing.Dialogs;

import Model.Orcamento;
import Model.Venda;
import Service.OrcamentoService;
import Service.VendaService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class VendaDialog extends JDialog {

    private final VendaService vendaService;
    private final OrcamentoService orcamentoService;
    private Venda venda; // Null if creating new

    // Fields
    private JTextField txtIdOrcamento;
    private JTextField txtPrazo; // Only for creation
    private JComboBox<Venda.StatusPagamento> cbStatus; // Only for update
    private JLabel lblOrcamentoInfo; // To show budget details

    public VendaDialog(Frame parent, VendaService vendaService, OrcamentoService orcamentoService, Venda venda) {
        super(parent, venda == null ? "Nova Venda" : "Gerenciar Venda", true);
        this.vendaService = vendaService;
        this.orcamentoService = orcamentoService;
        this.venda = venda;

        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Components ---

        if (venda == null) {
            // --- CREATION MODE ---
            gbc.gridx = 0; gbc.gridy = 0;
            add(new JLabel("ID do Orçamento:"), gbc);
            txtIdOrcamento = new JTextField(10);
            gbc.gridx = 1;
            add(txtIdOrcamento, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            add(new JLabel("Prazo (dias para pagamento):"), gbc);
            txtPrazo = new JTextField(10);
            gbc.gridx = 1;
            add(txtPrazo, gbc);

            JButton btnVerificar = new JButton("Verificar Orçamento");
            gbc.gridx = 1; gbc.gridy = 2;
            add(btnVerificar, gbc);

            btnVerificar.addActionListener(e -> verificarOrcamento());

        } else {
            // --- EDIT MODE ---
            gbc.gridx = 0; gbc.gridy = 0;
            add(new JLabel("ID Venda: " + venda.getId()), gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            add(new JLabel("Orçamento Vinculado: " + venda.getIdOrcamento()), gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            add(new JLabel("Data Criação: " + (venda.getDataCriacao() != null ? new SimpleDateFormat("dd/MM/yyyy").format(venda.getDataCriacao()) : "-")), gbc);

            gbc.gridx = 0; gbc.gridy = 3;
            add(new JLabel("Status Pagamento:"), gbc);
            cbStatus = new JComboBox<>(Venda.StatusPagamento.values());
            cbStatus.setSelectedItem(venda.getStatusPagamento());
            gbc.gridx = 1;
            add(cbStatus, gbc);
        }

        // Info Label area
        lblOrcamentoInfo = new JLabel(" ");
        lblOrcamentoInfo.setForeground(Color.BLUE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblOrcamentoInfo, gbc);

        // Buttons
        JPanel pnlBtn = new JPanel();
        JButton btnSave = new JButton(venda == null ? "Nova Venda" : "Atualizar Status");
        JButton btnCancel = new JButton("Fechar");
        pnlBtn.add(btnSave);
        pnlBtn.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        add(pnlBtn, gbc);

        // --- Actions ---
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void verificarOrcamento() {
        try {
            int id = Integer.parseInt(txtIdOrcamento.getText());
            Orcamento o = orcamentoService.BuscaPorId(id); //
            if (o != null) {
                lblOrcamentoInfo.setText("<html>Cliente: " + o.getNomeCliente() + "<br>Valor: R$ " + o.getValor() + "<br>Status: " + o.getStatus() + "<br>Validade: " + o.getDataValidade() + "</html>");
            } else {
                lblOrcamentoInfo.setText("Orçamento não encontrado.");
            }
        } catch (NumberFormatException ex) {
            lblOrcamentoInfo.setText("ID inválido.");
        } catch (Exception ex) {
            lblOrcamentoInfo.setText("Erro: " + ex.getMessage());
        }
    }

    private void save() {
        try {
            if (venda == null) {
                // Create Logic
                int idOrcamento = Integer.parseInt(txtIdOrcamento.getText());
                int prazo = Integer.parseInt(txtPrazo.getText());

                Orcamento orc = orcamentoService.BuscaPorId(idOrcamento);
                if (orc == null) {
                    JOptionPane.showMessageDialog(this, "Orçamento não existe.");
                    return;
                }

                vendaService.Criar(orc, prazo); //
                JOptionPane.showMessageDialog(this, "Venda gerada com sucesso!");

            } else {
                // Update Logic
                Venda.StatusPagamento newStatus = (Venda.StatusPagamento) cbStatus.getSelectedItem();
                vendaService.AtualizarStatusPagamento(venda.getId(), newStatus.name()); //
                JOptionPane.showMessageDialog(this, "Status atualizado!");
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique se os números (ID, Prazo) estão corretos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}