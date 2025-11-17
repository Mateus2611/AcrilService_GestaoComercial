import View.Console.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteView clienteView = new ClienteView();
        ProdutoView produtoView = new ProdutoView();
        OrcamentoView orcamentoView = new OrcamentoView();
        OrcamentoProdutoView orcamentoProdutoView = new OrcamentoProdutoView();

        System.out.println("\n\nSelecione a rota desejada\n\n");
        System.out.println(Arrays.toString(Rotas.values()));
        System.out.println();
        String escolha = sc.nextLine().toUpperCase();

        switch (Rotas.valueOf(escolha)) {
            case CLIENTE -> clienteView.CriarCliente();
            case PRODUTO -> produtoView.SelecionarAcaoProduto();
            case ORCAMENTO -> orcamentoView.SelecionarAcaoOrcamento();
            case ORCAMENTOPRODUTO -> orcamentoProdutoView.SelecionarAcaoOrcamentoProduto();
        }
    }

    enum Rotas {
        CLIENTE,
        PRODUTO,
        ORCAMENTO,
        ORCAMENTOPRODUTO,
        SAIR
    }
}