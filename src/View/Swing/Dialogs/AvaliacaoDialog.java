package View.Swing.Dialogs;

import Model.Avaliacao;
import Model.Venda;
import Service.AvaliacaoService;
import Service.VendaService;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AvaliacaoDialog extends JDialog {

    private final AvaliacaoService avaliacaoService;
    private final VendaService vendaService;
    private Avaliacao avaliacao;
    private boolean saved = false;

    private JTextField txtIdVenda;
    private JTextField txtTitulo;
    private JTextArea txtDescricao;
    private JSpinner spnNota;

    public AvaliacaoDialog(Frame parent, AvaliacaoService avaliacaoService, VendaService vendaService, Avaliacao avaliacao) {
        super(parent, avaliacao == null ? "Nova Avaliação" : "Editar Avaliação", true);
        this.avaliacaoService = avaliacaoService;
        this.vendaService = vendaService;
        this.avaliacao = avaliacao;

        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ID Venda ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Venda:"), gbc);
        txtIdVenda = new JTextField(10);
        gbc.gridx = 1;
        add(txtIdVenda, gbc);

        //Título
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Título:"), gbc);
        txtTitulo = new JTextField(20);
        gbc.gridx = 1;
        add(txtTitulo, gbc);

        //Nota
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Nota (0-5):"), gbc);

        spnNota = new JSpinner(new SpinnerNumberModel
                (5.0, 0.0, 5.0, 0.5));

        ((JSpinner.DefaultEditor) spnNota.getEditor()).getTextField().setEditable(false);

        gbc.gridx = 1;

        add(spnNota, gbc);

        //Descrição
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        add(new JLabel("Descrição:"), gbc);

        txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        gbc.gridx = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        add(scrollDesc, gbc);

        //Buttons
        JPanel pnlBtn = new JPanel();
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");
        pnlBtn.add(btnSave);
        pnlBtn.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBtn, gbc);

        if (avaliacao != null) {
            txtIdVenda.setText(String.valueOf(avaliacao.getIdVenda()));
            txtIdVenda.setEditable(false);
            txtTitulo.setText(avaliacao.getTitulo());
            txtDescricao.setText(avaliacao.getDescricao());
            spnNota.setValue(Double.valueOf(avaliacao.getNota()));
        }

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        try {
            int idVenda = Integer.parseInt(txtIdVenda.getText());
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            float nota = ((Double) spnNota.getValue()).floatValue();

            if (titulo.isEmpty()) throw new Exception("O título é obrigatório.");

            if (avaliacao == null) {

                Venda v = vendaService.BuscaPorId(idVenda);
                if (v == null || v.getId() == null || v.getId() == 0) {
                    throw new Exception("Venda com ID " + idVenda + " não encontrada.");
                }

                Avaliacao novo = new Avaliacao(titulo, descricao, new java.sql.Date(new Date().getTime()), nota, idVenda);
                avaliacaoService.Criar(novo, idVenda); //

            } else {
                avaliacao.setTitulo(titulo);
                avaliacao.setDescricao(descricao);
                avaliacao.setNota(nota);

                avaliacaoService.Atualizar(avaliacao.getId(), avaliacao); //
            }

            saved = true;
            JOptionPane.showMessageDialog(this, "Avaliação salva com sucesso!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Venda inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}