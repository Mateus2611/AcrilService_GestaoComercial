package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.*;
import Model.Orcamento;
import Model.Venda;
import Service.ClienteService;
import Service.OrcamentoService;
import Service.VendaService;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum VendaOpcoes {
    CRIAR,
    BUSCA_GERAL,
    BUSCA_ID,
    ATUALIZAR_STATUS_PAGAMENTO
}

public class VendaView {
    Scanner sc = new Scanner(System.in);
    Connection _conn = ConexaoDb.openConnection();

    OrcamentoDAO _orcmaentoDAO = new OrcamentoDAO(_conn);
    OrcamentoProdutoDAO _orcamentoProdutoDAO = new OrcamentoProdutoDAO(_conn);
    ProdutoDAO _produtoDAO = new ProdutoDAO(_conn);
    ClienteDAO _clienteDAO = new ClienteDAO(_conn);
    VendaDAO _vendaDAO = new VendaDAO(_conn);

    VendaService _vendaService = new VendaService(_vendaDAO, _orcmaentoDAO);
    OrcamentoService _orcamentoService = new OrcamentoService(_orcmaentoDAO, _clienteDAO, _orcamentoProdutoDAO, _produtoDAO);

    public void SelecionarAcaoVenda() {

        System.out.println("\n\nSelecione a operação desejada\n\n ");
        System.out.println(Arrays.toString(View.Console.VendaOpcoes.values()));
        System.out.println();
        String resp = sc.nextLine().toUpperCase();

        switch (View.Console.VendaOpcoes.valueOf(resp)) {
            case CRIAR:
                CriarVenda();
                break;
            case BUSCA_GERAL:
                BuscaGeral();
                break;
            case BUSCA_ID:
                BuscaId();
                break;
            case ATUALIZAR_STATUS_PAGAMENTO:
                AtualizarStatusPagamento();
                break;
        }
    }

    public void CriarVenda() {
        System.out.println("Informe o ID do Orçamento: ");
        int idOrcamento = sc.nextInt();
        sc.nextLine();

        Orcamento orcamento = _orcamentoService.BuscaPorId(idOrcamento);

        if (orcamento == null)
            throw new RuntimeException("Não existe orçamento com o ID informado.");

        System.out.println("Informe o prazo em dias para realizar o pagamento: ");
        int prazo = sc.nextInt();
        sc.nextLine();

        System.out.println(_vendaService.Criar(orcamento, prazo).toString());
    }

    public void BuscaGeral() {
        List<Venda> vendas = _vendaService.BuscaGeral();

        for (Venda venda : vendas) {
            Orcamento orcamento = _orcamentoService.BuscaPorId(venda.getIdOrcamento());
            venda.setOrcamento(orcamento);
        }

        System.out.println(vendas.toString());
    }

    public void BuscaId() {
        System.out.println("Informe o ID da venda que deseja consultar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Venda venda = _vendaService.BuscaPorId(id);

        Orcamento orcamento = _orcamentoService.BuscaPorId(venda.getIdOrcamento());
        venda.setOrcamento(orcamento);

        System.out.println(venda.toString());
    }

    public void AtualizarStatusPagamento() {
        System.out.println("Informe o ID da venda que deseja alterar o status: ");
        int id = sc.nextInt();

        System.out.println("Selecione o status desejado: ");
        System.out.println(Arrays.toString(Venda.StatusPagamento.values()));
        sc.nextLine();
        String status = sc.nextLine();

        Venda venda = _vendaService.AtualizarStatusPagamento(id, status.toUpperCase());
        venda.setOrcamento(_orcamentoService.BuscaPorId(venda.getIdOrcamento()));

        System.out.println(venda.toString());
    }
}
