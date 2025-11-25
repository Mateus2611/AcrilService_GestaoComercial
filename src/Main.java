import View.Console.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClienteView clienteView = new ClienteView();
        ProdutoView produtoView = new ProdutoView();
        OrcamentoView orcamentoView = new OrcamentoView();
        VendaView vendaView = new VendaView();
        AvaliacaoView avaliacaoView = new AvaliacaoView();

        System.out.println("\n\nSelecione a rota desejada\n\n");
        System.out.println(Arrays.toString(Rotas.values()));
        System.out.println();
        String escolha = sc.nextLine().toUpperCase();

        switch (Rotas.valueOf(escolha)) {
            case CLIENTE -> clienteView.SelecionarAcaoCliente();
            case PRODUTO -> produtoView.SelecionarAcaoProduto();
            case ORCAMENTO -> orcamentoView.SelecionarAcaoOrcamento();
            case VENDA -> vendaView.SelecionarAcaoVenda();
            case AVALIACAO -> avaliacaoView.SelecionarAcaoAvaliacao();
        }
    }

    enum Rotas {
        CLIENTE,
        PRODUTO,
        ORCAMENTO,
        VENDA,
        AVALIACAO,
        SAIR
    }
}