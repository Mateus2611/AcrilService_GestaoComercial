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
    CRIAR,
    BUSCAGERAL,
    BUSCAID,
    ATUALIZAR,
    EXCLUIR
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

        switch (produtoOpcoes.valueOf(resp.toUpperCase())) {
            case CRIAR:
                CriarProduto();
                break;
            case BUSCAGERAL:
                BuscaGeral();
                break;
            case BUSCAID:
                BuscaId();
                break;
            case ATUALIZAR:
                Atualizar();
                break;
            case EXCLUIR:
                Excluir();
                break;
        }
    }

    private void CriarProduto() {
        BigDecimal valor;
        String nome;

        while (true) {
            System.out.println("Informe o nome do produto");
            nome = sc.nextLine();

            System.out.println("Informe o valor do produto");

            try {
                valor = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));
                break;

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida");
            }
        }

        try {
            Produto objeto = new Produto(nome, valor);
            objeto = produtoService.Criar(objeto);

            System.out.println("Produto criado com sucesso");
        } catch (RuntimeException e) {
            System.out.println("Erro ao Criar Produto");
        }
    }

    private void BuscaGeral() {
        List<Produto> produtos = produtoService.BuscaGeral();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private void BuscaId() {
        int id;

        while (true) {
            System.out.println("Informe o id do produto. Digite 'Sair' para encerrar");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Sair")) {
                break;
            }

            try {
                id = Integer.parseInt(input);
                Produto objeto = produtoService.BuscaPorId(id);
                System.out.println(objeto);
                break;

            } catch (RuntimeException e) {
                System.out.println("Nenhum Produto Encontrado");
            }
        }
    }

    private void Atualizar() {
        System.out.println("Informe o id do produto");
        int id = Integer.parseInt(sc.nextLine());

        Produto objeto = produtoService.BuscaPorId(id);
        if (objeto == null) {
            System.out.println("Produto não encontrado");
        } else {
            System.out.println("Produto atual: " + objeto.getNome() + " | " + objeto.getValor());

            System.out.println("Informe o novo nome do produto");
            String nome = sc.nextLine();

            System.out.println("Informe o novo valor do produto");
            BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));

            objeto = new Produto(id, nome, valor);
            objeto = produtoService.Atualizar(objeto.getId(), objeto);

            System.out.println("Produto atualizado com sucesso");
        }
    }

    private void Excluir() {

        while (true) {
            System.out.println("Informe o id do produto para exclusão:");

            try {
                int id = Integer.parseInt(sc.nextLine());
                produtoService.Excluir(id);
                System.out.println("Produto excluído com sucesso");
                break;

            } catch (NumberFormatException e) {
                System.out.println("ID inválido");
            } catch (RuntimeException e) {
                System.out.println("Não foi possível excluir o produto");
            }
        }
    }
}
