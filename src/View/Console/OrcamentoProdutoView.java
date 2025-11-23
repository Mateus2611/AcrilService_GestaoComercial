package View.Console;


import DAO.JDBC.ConexaoDb;
import DAO.MySQL.*;
import Model.Email;
import Model.Orcamento;
import Model.OrcamentoProduto;
import Model.Produto;
import Service.ClienteService;
import Service.OrcamentoProdutoService;
import Service.OrcamentoService;
import Service.ProdutoService;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Scanner;

enum orcamentoProdutoOpcoes {
    ADICIONAR,
    ATUALIZAR,
    EXCLUIR
}

public class OrcamentoProdutoView {
    Connection _conn = ConexaoDb.openConnection();
    Scanner sc = new Scanner(System.in);
    OrcamentoProdutoDAO orcamentoProdutoDao = new OrcamentoProdutoDAO(_conn);
    OrcamentoProdutoService orcamentoProdutoService = new OrcamentoProdutoService(orcamentoProdutoDao);

    ProdutoDAO produtoDAO = new ProdutoDAO(_conn);
    ProdutoService produtoService = new ProdutoService(produtoDAO);

    ClienteDAO clienteDAO = new ClienteDAO(_conn);
    EnderecoDAO enderecoDAO = new EnderecoDAO(_conn);
    EmailDAO emailDAO = new EmailDAO(_conn);
    ClienteService clienteService = new ClienteService(clienteDAO, enderecoDAO, emailDAO);

    OrcamentoDAO orcamentoDao = new OrcamentoDAO(ConexaoDb.openConnection());
    OrcamentoService orcamentoService = new OrcamentoService(orcamentoDao, clienteDAO, orcamentoProdutoDao, produtoDAO);



    public void SelecionarAcaoOrcamentoProduto() {

        System.out.println("\n\nSelecione a operação desejada\n\n");
        System.out.println(Arrays.toString(orcamentoProdutoOpcoes.values()));
        System.out.println();
        String resp = sc.nextLine();

        switch (orcamentoProdutoOpcoes.valueOf(resp.toUpperCase())) {
            case ADICIONAR:
                AdicionarProduto();
                break;
            case ATUALIZAR:
                AtualizarQuantidadeProduto();
                break;
            case EXCLUIR:
                Excluir();
                break;
        }
    }

    private void AdicionarProduto() {

        System.out.println("Informe o ID do orçamento");
        int orcamentoId = Integer.parseInt(sc.nextLine());
        System.out.println(orcamentoId);

        Orcamento orcamentoObjeto = orcamentoService.BuscaPorId(orcamentoId);

        System.out.println("Informe o ID do produto");
        int produtoId = Integer.parseInt(sc.nextLine());
        System.out.println(produtoId);

        Produto produtoObjeto = produtoService.BuscaPorId(produtoId);

        if (orcamentoObjeto == null || produtoObjeto == null) {
            System.out.println("Orçamento ou produto nõo encontrado");
        } else {
            System.out.println("Digite a quantidade do produto");
            int quantidade = Integer.parseInt(sc.nextLine());

            try {
                OrcamentoProduto objeto = new OrcamentoProduto(orcamentoId, produtoId, quantidade);
                orcamentoProdutoService.Criar(objeto);
            } catch (RuntimeException e) {
                System.out.println("Erro ao adicionar produto ao orçamento");
            }
        }
    }

    private void AtualizarQuantidadeProduto() {

        System.out.println("Informe o ID do produto");
        int produtoId = Integer.parseInt(sc.nextLine());
        System.out.println(produtoId);

        System.out.println("Informe o ID do orçamento");
        int orcamentoId = Integer.parseInt(sc.nextLine());
        System.out.println(orcamentoId);

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

    private void Excluir() {
        System.out.println("Informe o ID do produto para remover do orçamento");
        int idProduto = Integer.parseInt(sc.nextLine());

        System.out.println("Informe o ID do orçamento");
        int idOrcamento = Integer.parseInt(sc.nextLine());
        System.out.println(idOrcamento);

        Produto produtoObjeto = produtoService.BuscaPorId(idProduto);
        Orcamento orcamentoObjeto = orcamentoService.BuscaPorId(idOrcamento);
        OrcamentoProduto orcamentoProduto = new OrcamentoProduto(idOrcamento, idProduto);
        orcamentoProdutoService.Excluir(idOrcamento, orcamentoProduto);
    }
}
