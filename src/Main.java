import View.Console.EnderecoView;
import View.Console.OrcamentoProdutoView;
import View.Console.OrcamentoView;
import View.Console.ProdutoView;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EnderecoView enderecoView = new EnderecoView();
        ProdutoView produtoView = new ProdutoView();
        OrcamentoView orcamentoView = new OrcamentoView();
        OrcamentoProdutoView orcamentoProdutoView = new OrcamentoProdutoView();

        System.out.println("\n\nSelecione a rota desejada\n\n");
        System.out.println(Arrays.toString(Rotas.values()));
        System.out.println();
        String escolha = sc.nextLine().toUpperCase();

        switch (Rotas.valueOf(escolha)) {
            case ENDERECO -> enderecoView.SelecionarAcaoEndereco();
            case PRODUTO -> produtoView.SelecionarAcaoProduto();
            case ORCAMENTO -> orcamentoView.SelecionarAcaoOrcamento();
            case ORCAMENTOPRODUTO -> orcamentoProdutoView.SelecionarAcaoOrcamentoProduto();
        }
    }

    enum Rotas {
        ENDERECO,
        PRODUTO,
        ORCAMENTO,
        ORCAMENTOPRODUTO,
        SAIR
    }
}