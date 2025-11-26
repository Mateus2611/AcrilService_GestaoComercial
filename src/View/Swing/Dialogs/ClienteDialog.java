package View.Swing.Dialogs;

import Model.Cliente;
import Model.Email;
import Model.Endereco;
import Service.ClienteService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDialog extends JDialog {

    private final ClienteService service;
    private Cliente cliente;
    private boolean saved = false;

    private JTextField txtNome;
    private JComboBox<Cliente.TipoCliente> cbTipo;

    private JTextField txtCep, txtLogradouro, txtBairro, txtCidade, txtEstado;

    private DefaultListModel<Email> emailListModel;
    private JList<Email> listEmails;
    private JTextField txtNewEmail;

    public ClienteDialog(Frame parent, ClienteService service, Cliente cliente) {
        super(parent, cliente == null ? "Novo Cliente" : "Editar Cliente", true);
        this.service = service;
        this.cliente = cliente;

        setLayout(new BorderLayout());
        setSize(500, 400);
        setLocationRelativeTo(parent);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel pnlDados = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlDados.add(new JLabel("Nome Completo:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1;
        pnlDados.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlDados.add(new JLabel("Tipo (CPF/CNPJ):"), gbc);
        cbTipo = new JComboBox<>(Cliente.TipoCliente.values());
        gbc.gridx = 1;
        pnlDados.add(cbTipo, gbc);

        tabbedPane.addTab("Dados Gerais", pnlDados);

        JPanel pnlEndereco = new JPanel(new GridBagLayout());

        gbc.gridx = 0; gbc.gridy = 0;
        pnlEndereco.add(new JLabel("CEP:"), gbc);
        txtCep = new JTextField(10);
        gbc.gridx = 1;
        pnlEndereco.add(txtCep, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlEndereco.add(new JLabel("Logradouro:"), gbc);
        txtLogradouro = new JTextField(20);
        gbc.gridx = 1;
        pnlEndereco.add(txtLogradouro, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlEndereco.add(new JLabel("Bairro:"), gbc);
        txtBairro = new JTextField(15);
        gbc.gridx = 1;
        pnlEndereco.add(txtBairro, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        pnlEndereco.add(new JLabel("Cidade:"), gbc);
        txtCidade = new JTextField(15);
        gbc.gridx = 1;
        pnlEndereco.add(txtCidade, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        pnlEndereco.add(new JLabel("Estado (UF):"), gbc);
        txtEstado = new JTextField(5);
        gbc.gridx = 1;
        pnlEndereco.add(txtEstado, gbc);

        tabbedPane.addTab("Endereço", pnlEndereco);

        JPanel pnlEmails = new JPanel(new BorderLayout());
        emailListModel = new DefaultListModel<>();
        listEmails = new JList<>(emailListModel);

        listEmails.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value.getEndereco());
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            return label;
        });

        pnlEmails.add(new JScrollPane(listEmails), BorderLayout.CENTER);

        JPanel pnlEmailControls = new JPanel(new BorderLayout());
        txtNewEmail = new JTextField();
        JButton btnAddEmail = new JButton("Adicionar");
        JButton btnRemoveEmail = new JButton("Remover Selecionado");

        pnlEmailControls.add(txtNewEmail, BorderLayout.CENTER);
        pnlEmailControls.add(btnAddEmail, BorderLayout.EAST);
        pnlEmailControls.add(btnRemoveEmail, BorderLayout.SOUTH);
        pnlEmails.add(pnlEmailControls, BorderLayout.SOUTH);

        tabbedPane.addTab("Emails", pnlEmails);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel pnlBtn = new JPanel();
        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");
        pnlBtn.add(btnSave);
        pnlBtn.add(btnCancel);
        add(pnlBtn, BorderLayout.SOUTH);

        btnAddEmail.addActionListener(e -> {
            String mail = txtNewEmail.getText().trim();
            if (!mail.isEmpty()) {
                emailListModel.addElement(new Email(mail));
                txtNewEmail.setText("");
            }
        });

        btnRemoveEmail.addActionListener(e -> {
            int selected = listEmails.getSelectedIndex();
            if (selected != -1) {
                emailListModel.remove(selected);
            }
        });

        btnSave.addActionListener(e -> saveData());
        btnCancel.addActionListener(e -> dispose());

        if (cliente != null) {
            loadClienteData();
        }
    }

    private void loadClienteData() {
        txtNome.setText(cliente.getNome());
        cbTipo.setSelectedItem(cliente.getTipo());

        if (cliente.getEndereco() != null) {
            txtCep.setText(cliente.getEndereco().getCep());
            txtLogradouro.setText(cliente.getEndereco().getLogradouro());
            txtBairro.setText(cliente.getEndereco().getBairro());
            txtCidade.setText(cliente.getEndereco().getCidade());
            txtEstado.setText(cliente.getEndereco().getEstado());
        }

        if (cliente.getEmails() != null) {
            for (Email e : cliente.getEmails()) {
                emailListModel.addElement(e);
            }
        }
    }

    private void saveData() {
        try {
            // 1. Gather Address Data
            Endereco endereco = new Endereco(
                    txtCep.getText(),
                    txtBairro.getText(),
                    txtEstado.getText(),
                    txtCidade.getText(),
                    txtLogradouro.getText()
            );

            // 2. Gather Email Data
            List<Email> emailList = new ArrayList<>();
            for (int i = 0; i < emailListModel.size(); i++) {
                emailList.add(emailListModel.get(i));
            }

            if (cliente == null) {
                Cliente newCliente = new Cliente((Cliente.TipoCliente) cbTipo.getSelectedItem(), txtNome.getText());
                service.Criar(newCliente, endereco, emailList); //
            } else {

                if (!cliente.getNome().equals(txtNome.getText())) {
                    service.AtualizarNomeCliente(cliente.getId(), txtNome.getText());
                }
                if (!cliente.getTipo().equals(cbTipo.getSelectedItem())) {
                    service.AtualizarTipoCliente(cliente.getId(), ((Cliente.TipoCliente) cbTipo.getSelectedItem()).name());
                }

                service.AtualizarEnderecoCliente(cliente.getId(), endereco); //

                List<Email> toAdd = new ArrayList<>();
                List<Integer> currentIds = new ArrayList<>();

                for (Email e : emailList) {
                    if (e.getId() == null) {
                        toAdd.add(e);
                    } else {
                        currentIds.add(e.getId());
                    }
                }

                if (!toAdd.isEmpty()) {
                    service.CriarEmailCliente(cliente.getId(), toAdd);
                }

                // 2. Identify removed emails (In old list but not in current list)
                for (Email oldEmail : cliente.getEmails()) {
                    if (!currentIds.contains(oldEmail.getId())) {
                        service.ExcluirEmailCliente(oldEmail.getId()); //
                    }
                }
            }

            saved = true;
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean isSaved() {
        return saved;
    }
}