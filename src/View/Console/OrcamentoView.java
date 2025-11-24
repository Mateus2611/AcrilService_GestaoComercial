package View.Console;


import DAO.JDBC.ConexaoDb;
import DAO.MySQL.ClienteDAO;
import DAO.MySQL.OrcamentoDAO;
import DAO.MySQL.OrcamentoProdutoDAO;
import DAO.MySQL.ProdutoDAO;
import Model.Orcamento;
import Model.OrcamentoProduto;
import Model.Produto;
import Service.OrcamentoProdutoService;
import Service.OrcamentoService;
import Service.ProdutoService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

enum orcamentoOpcoes {
    CRIAR,
    BUSCAGERAL,
    BUSCAID,
    ATUALIZAR_STATUS,
    ATUALIZAR_QUANTIDADE,
    EXCLUIR_PRODUTO
}

public class OrcamentoView {

    Scanner sc = new Scanner(System.in);
    OrcamentoDAO orcamentoDAO = new OrcamentoDAO(ConexaoDb.openConnection());
    ClienteDAO clienteDAO = new ClienteDAO(ConexaoDb.openConnection());
    OrcamentoProdutoDAO orcamentoProdutoDAO = new OrcamentoProdutoDAO(ConexaoDb.openConnection());
    ProdutoDAO produtoDAO = new ProdutoDAO(ConexaoDb.openConnection());
    ProdutoService produtoService = new ProdutoService(produtoDAO);
    OrcamentoProdutoService orcamentoProdutoService = new OrcamentoProdutoService(orcamentoProdutoDAO);

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
            case ATUALIZAR_STATUS:
                AtualizarStatus();
                break;
            case ATUALIZAR_QUANTIDADE:
                AtualizarQuantidadeProduto();
                break;
            case EXCLUIR_PRODUTO:
                ExcluirProduto();
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
                dataCriacao = new Date(format.parse(dataString).getTime());

                System.out.println("Informe a data de validade do orçamento");
                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                String dataString2 = sc.nextLine();

                if (dataString2.isEmpty()) {
                    dataValidade = null;
                } else {
                    dataValidade = new Date(format2.parse(dataString2).getTime());
                }

                System.out.println("Informe o desconto aplicado em cima do valor");
                String descontoString = sc.nextLine();

                if (descontoString.isEmpty()) {
                    desconto = BigDecimal.ZERO;
                } else {
                    desconto = new BigDecimal(descontoString);
                }

                Orcamento.StatusOrcamento status = Orcamento.StatusOrcamento.PENDENTE;
                Orcamento orcamento = new Orcamento(idCliente, dataCriacao, dataValidade, BigDecimal.ZERO, status, desconto);

                orcamento = orcamentoService.Criar(orcamento);

                System.out.println("Informe o ID do produto");
                int produtoId = Integer.parseInt(sc.nextLine());
                System.out.println(produtoId);

                Produto produtoObjeto = produtoService.BuscaPorId(produtoId);

                if (produtoObjeto == null) {
                    System.out.println("Orçamento ou produto nõo encontrado");
                } else {
                    System.out.println("Digite a quantidade do produto");

                    int quantidade = Integer.parseInt(sc.nextLine());

                    try {
                        OrcamentoProduto orcamentoProduto = new OrcamentoProduto(orcamento.getId(), produtoId, quantidade);
                        orcamentoProdutoService.Criar(orcamentoProduto);
                        System.out.println("Orçamento adicionado com sucesso!");
                        break;
                    } catch (RuntimeException e) {
                        System.out.println("Erro ao adicionar produto ao orçamento");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida");
            } catch (ParseException e) {
                System.out.println("Formato de data inválido, utilize o seguinte formato: Dia/Mês/Ano");
            }
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

    private void AtualizarQuantidadeProduto() {

        System.out.println("Informe o ID do orçamento");
        int orcamentoId = Integer.parseInt(sc.nextLine());
        System.out.println(orcamentoId);

        System.out.println("Informe o ID do produto");
        int produtoId = Integer.parseInt(sc.nextLine());
        System.out.println(produtoId);

        Produto produtoObjeto = produtoService.BuscaPorId(produtoId);
        Orcamento orcamentoObjeto = orcamentoService.BuscaPorId(orcamentoId);

        if (produtoObjeto == null || orcamentoObjeto == null) {
            System.out.println("Produto ou orçamento não encontrado");

        } else {
            System.out.println("Informe a nova quantidade do produto");
            Integer quantidade = Integer.parseInt(sc.nextLine());
            System.out.println(quantidade);

            OrcamentoProduto orcamentoProduto = new OrcamentoProduto(orcamentoId, produtoId, quantidade);
            orcamentoProdutoService.AtualizarQuantidade(orcamentoId, orcamentoProduto);
            System.out.println("Quantidade atualizada com sucesso");
        }
    }

    private void ExcluirProduto() {

        System.out.println("Informe o ID do orçamento");
        int idOrcamento = Integer.parseInt(sc.nextLine());
        System.out.println(idOrcamento);

        System.out.println("Informe o ID do produto para remover do orçamento");
        int idProduto = Integer.parseInt(sc.nextLine());

        Produto produtoObjeto = produtoService.BuscaPorId(idProduto);
        Orcamento orcamentoObjeto = orcamentoService.BuscaPorId(idOrcamento);
        OrcamentoProduto orcamentoProduto = new OrcamentoProduto(idOrcamento, idProduto);
        orcamentoProdutoService.Excluir(idOrcamento, orcamentoProduto);
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

            System.out.println("Digite o novo status (APROVADO, REPROVADO, EXPIRADO OU PENDENTE");
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
