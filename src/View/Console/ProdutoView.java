package View.Console;


import DAO.JDBC.ConexaoDb;
import DAO.MySQL.ProdutoDAO;
import Model.Produto;
import Service.ProdutoService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


enum produtoOpcoes {
    Criar,
    BuscaGeral,
    Atualizar,
    Excluir
}

public class ProdutoView {

    Scanner sc = new Scanner(System.in);
    ProdutoDAO produtoDAO = new ProdutoDAO(ConexaoDb.openConnection());
    ProdutoService produtoService = new ProdutoService(produtoDAO);


    public void SelecionarAcaoProduto() {

        System.out.println("\n\nSelecione a operação desejada\n\n");
        System.out.println(Arrays.toString(produtoOpcoes.values()));
        System.out.println();
        String resp = sc.nextLine();

        switch (Opcoes.valueOf(resp.toLowerCase())) {
            case Criar:
                CriarProduto();
            case BuscaGeral:
                BuscaGeral();
        }
    }

    private void CriarProduto() {

        System.out.println("Informe o nome do produto");
        String nome = sc.nextLine();
        System.out.println(nome);

        System.out.println("Informe o valor do produto");
        BigDecimal valor = BigDecimal.valueOf(sc.nextDouble());
        System.out.println(valor);

        Produto objeto = new Produto(nome, valor);
        objeto = produtoService.Criar(objeto);
    }

    private void BuscaGeral() {
        List<Produto> produtos = produtoService.BuscaGeral();
        produtos.forEach(System.out::println);
    }

    private void Atualizar() {
        System.out.println("Informe o id do produto");
        int id = Integer.parseInt(sc.nextLine());
        System.out.println(id);

        System.out.println("Informe o novo nome do produto");
        String nome = sc.nextLine();
        System.out.println(nome);

        System.out.println("Informe o novo valor do produto");
        BigDecimal valor = BigDecimal.valueOf(sc.nextDouble());
    }
}
