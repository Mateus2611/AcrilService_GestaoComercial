package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.*;
import Model.Venda;
import Service.ClienteService;
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
    VendaDAO _vendaDAO = new VendaDAO(_conn);

    VendaService _vendaService = new VendaService(_vendaDAO, _orcmaentoDAO);

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

        System.out.println("Informe o prazo em dias para realizar o pagamento: ");
        int prazo = sc.nextInt();
        sc.nextLine();

        System.out.println(_vendaService.Criar(idOrcamento, prazo).toString());
    }

    public void BuscaGeral() {
        List<Venda> vendas = _vendaService.BuscaGeral();

        System.out.println(vendas.toString());
    }

    public void BuscaId() {
        System.out.println("Informe o ID da venda que deseja consultar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println(_vendaService.BuscaPorId(id).toString());
    }

    public void AtualizarStatusPagamento() {
        System.out.println("Informe o ID da venda que deseja alterar o status: ");
        int id = sc.nextInt();

        System.out.println("Selecione o status desejado: ");
        System.out.println(Arrays.toString(Venda.StatusPagamento.values()));
        sc.nextLine();
        String status = sc.nextLine();

        System.out.println(
                _vendaService.AtualizarStatusPagamento(id, status.toUpperCase()).toString()
        );
    }
}
