package View.Console;


import DAO.JDBC.ConexaoDb;
import DAO.MySQL.ClienteDAO;
import DAO.MySQL.OrcamentoDAO;
import DAO.MySQL.OrcamentoProdutoDAO;
import DAO.MySQL.ProdutoDAO;
import Model.Orcamento;
import Service.OrcamentoService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

enum orcamentoOpcoes {
    CRIAR,
    BUSCAGERAL,
    BUSCAID,
    ATUALIZAR
}

public class OrcamentoView {

    Scanner sc = new Scanner(System.in);
    OrcamentoDAO orcamentoDAO = new OrcamentoDAO(ConexaoDb.openConnection());
    ClienteDAO clienteDAO = new ClienteDAO(ConexaoDb.openConnection());
    OrcamentoProdutoDAO orcamentoProdutoDAO = new OrcamentoProdutoDAO(ConexaoDb.openConnection());
    ProdutoDAO produtoDAO = new ProdutoDAO(ConexaoDb.openConnection());

    OrcamentoService orcamentoService = new OrcamentoService(orcamentoDAO, clienteDAO, orcamentoProdutoDAO, produtoDAO);

    public void SelecionarAcaoOrcamento() {

        System.out.println("\n\nSelecione a operação desejada\n\n");
        System.out.println(Arrays.toString(orcamentoOpcoes.values()));
        System.out.println();
        String resp = sc.nextLine();

        switch (orcamentoOpcoes.valueOf(resp.toUpperCase())) {
            case CRIAR:
                CriarOrcamento();
                break;
            case BUSCAGERAL:
                BuscaGeral();
                break;
            case BUSCAID:
                BuscaId();
                break;
            case ATUALIZAR:
                AtualizarStatus();
                break;
        }
    }

    private void CriarOrcamento() {

        int idCliente;
        Date dataCriacao;
        Date dataValidade;
        BigDecimal valor;
        BigDecimal desconto;

        while (true) {
            try {
                System.out.println("Informe o ID do Cliente");
                idCliente = Integer.parseInt(sc.nextLine());
                System.out.println(idCliente);

                System.out.println("Informe a data de criação do orçamento");
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                String dataString = sc.nextLine();
                dataCriacao = format.parse(dataString);

                System.out.println("Informe a data de validade do orçamento");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                String dataString2 = sc.nextLine();

                if (dataString2.isEmpty()) {
                    dataValidade = null;
                } else {
                    dataValidade = format2.parse(dataString2);
                }

                System.out.println(dataValidade);

                System.out.println("Informe o valor do orçamento");
                valor = new BigDecimal(sc.nextLine());
                System.out.println(valor);

                System.out.println("Informe o desconto aplicado em cima do valor");
                String descontoString = sc.nextLine();

                if (descontoString.isEmpty()) {
                    desconto = BigDecimal.ZERO;
                } else {
                    desconto = new BigDecimal(descontoString);
                }

                System.out.println(desconto);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida");
            } catch (ParseException e) {
                System.out.println("Formato de data inválido, utilize o seguinte formato: Dia/Mês/Ano");
            }
        }

        try {

            Orcamento.StatusOrcamento status = Orcamento.StatusOrcamento.PENDENTE;

            Orcamento objeto = new Orcamento(idCliente, dataCriacao, dataValidade,
                    valor, status, desconto);

            orcamentoService.Criar(objeto);
            System.out.println("Orçamento Criado com sucesso");

        } catch (RuntimeException e) {
            System.out.println("Erro ao Criar Orçamento");
        }
    }

    private void BuscaGeral() {
        List<Orcamento> orcamentos = orcamentoService.BuscaGeral();
        if (orcamentos.isEmpty()) {
            System.out.println("Nenhum orçamento encontrado");
        } else {
            orcamentos.forEach(System.out::println);
        }
    }

    private void BuscaId() {
        int id;

        while (true) {
            System.out.println("Informe o ID do orçamento");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Sair")) {
                break;
            }

            try {
                id = Integer.parseInt(input);
                Orcamento objeto = orcamentoService.BuscaPorId(id);
                System.out.println(objeto);
                break;

            } catch (RuntimeException e) {
                System.out.println("Nenhum Orçamento encontrado");
            }
        }
    }

    private void AtualizarStatus() {
        System.out.println("Informe o ID do Orçamento que deseja atualizar");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println(id);

        Orcamento objeto = orcamentoService.BuscaPorId(id);
        if (objeto == null) {
            System.out.println("Orçamento não encontrado");
        } else {
            System.out.println("Status atual do orçamento: " + objeto.getStatus());

            System.out.println("Digite o novo status (APROVADO, REIJETADO, EXPIRADO OU PENDENTE");
            String resp = sc.nextLine().toUpperCase();

            try {
                Orcamento.StatusOrcamento novoStatus = Orcamento.StatusOrcamento.valueOf(resp);
                objeto.setStatus(novoStatus);
                orcamentoService.Atualizar(id, objeto);
                System.out.println("Status atualizado com sucesso");

            } catch (IllegalArgumentException e) {
                System.out.println("Status inválido");
            }
        }
    }
}
