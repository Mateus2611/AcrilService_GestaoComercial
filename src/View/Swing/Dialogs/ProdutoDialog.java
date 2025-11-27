package View.Swing.Dialogs;

import Model.Produto;
import Service.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class ProdutoDialog extends JDialog {

    private final ProdutoService service;
    private Produto produto;
    private boolean saved = false;

    private JTextField txtNome;
    private JTextField txtValor;

    public ProdutoDialog(Frame parent, ProdutoService service, Produto produto) {
        super(parent, "Produto", true);
        this.service = service;
        this.produto = produto;

        setLayout(new GridBagLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //Nome
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1;
        add(txtNome, gbc);

        //Valor
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Valor (R$):"), gbc);
        txtValor = new JTextField(20);
        gbc.gridx = 1;
        add(txtValor, gbc);

        JPanel panelBtn = new JPanel();
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");
        panelBtn.add(btnSave);
        panelBtn.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(panelBtn, gbc);

        if (produto != null) {
            txtNome.setText(produto.getNome());
            txtValor.setText(produto.getValor().toString());
        }

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
    }

    private void save() {
        try {
            String nome = txtNome.getText();
            BigDecimal valor = new BigDecimal(txtValor.getText().replace(",", "."));

            if (produto == null) {
                //Criar
                Produto novo = new Produto(nome, valor);
                service.Criar(novo);
            } else {
                //Atualizar
                produto.setNome(nome);
                produto.setValor(valor);
                service.Atualizar(produto.getId(), produto);
            }
            saved = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    public boolean isSaved() {
        return saved;
    }
}