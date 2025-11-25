package View.Console;

import DAO.JDBC.ConexaoDb;
import DAO.MySQL.AvaliacaoDAO;
import DAO.MySQL.OrcamentoDAO;
import DAO.MySQL.VendaDAO;
import Model.Avaliacao;
import Service.AvaliacaoService;
import Service.VendaService;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

enum avaliacaoOpcoes {
    CRIAR,
    BUSCAGERAL,
    BUSCAID,
    ATUALIZAR,
    EXCLUIR
}

public class AvaliacaoView {

    Scanner sc = new Scanner(System.in);
    Connection _conn = ConexaoDb.openConnection();

    VendaDAO _vendaDao = new VendaDAO(_conn);
    AvaliacaoDAO _avaliacaoDao = new AvaliacaoDAO(_conn);
    OrcamentoDAO _orcamentoDao = new OrcamentoDAO(_conn);

    VendaService _vendaService = new VendaService(_vendaDao, _orcamentoDao);
    AvaliacaoService _avaliacaoService = new AvaliacaoService(_avaliacaoDao, _vendaService);

    public void SelecionarAcaoAvaliacao() {

        System.out.println("\n\nSelecione a operação desejada\n\n ");
        System.out.println(Arrays.toString(View.Console.avaliacaoOpcoes.values()));
        System.out.println();
        String resp = sc.nextLine();

        switch (avaliacaoOpcoes.valueOf(resp.toUpperCase())) {
            case CRIAR:
                CriarAvaliacao();
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

    public void CriarAvaliacao() {
        int idVenda;
        String titulo;
        String descricao;
        Date dataAvaliacao;
        float nota;

        while (true) {
            System.out.println("Informe o ID da Venda: ");
            idVenda = Integer.parseInt(sc.nextLine());

            System.out.println("Informe o titulo da avaliacao: ");
            titulo = sc.nextLine();

            System.out.println("Escreva a sua avaliação");
            descricao = sc.nextLine();

            dataAvaliacao = Date.valueOf(LocalDate.now());

            try {
                System.out.println("Informe a nota para a venda");
                nota = Float.parseFloat(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida");
            }
        }

        try {
            Avaliacao objeto = new Avaliacao(titulo, descricao, dataAvaliacao, nota, idVenda);
            objeto = _avaliacaoService.Criar(objeto, idVenda);

            System.out.println("Avaliacao Criada com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao criar avaliacao: " + e.getMessage());
        }
    }

    private void BuscaGeral() {
        List<Avaliacao> avaliacoes = _avaliacaoService.BuscaGeral();

        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliacao encontrada!");
        } else {
            avaliacoes.forEach(System.out::println);
        }
    }

    private void BuscaId() {
        int id;

        while (true) {
            System.out.println("Informe o ID da Venda. Digite 'Sair' para encerrar");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Sair")) {
                break;
            }

            try {
                id = Integer.parseInt(input);
                Avaliacao objeto = _avaliacaoService.BuscaPorIdVenda(id);
                System.out.println(objeto);
                break;

            } catch (RuntimeException e) {
                System.out.println("Nenhuma avaliacao encontrada");
            }
        }
    }

    public void Atualizar() {
        System.out.println("Informe o ID da avaliacao: ");
        int id = Integer.parseInt(sc.nextLine());

        Avaliacao objeto = _avaliacaoService.BuscaPorId(id);
        if (objeto == null) {
            System.out.println("Avaliação não encontrada");
        } else {
            System.out.println("Avaliação atual: " + objeto.getTitulo() + objeto.getDescricao()
                    + objeto.getNota() + objeto.getDataCriacao());

            System.out.println("Informe o novo título da avaliacao: ");
            String titulo = sc.nextLine();

            System.out.println("Informe a nova descrição: ");
            String descricao = sc.nextLine();

            System.out.println("Informe a nova nota");
            float nota = Float.parseFloat(sc.nextLine());

            objeto = new Avaliacao(titulo, descricao, objeto.getDataCriacao(), nota, id);
            objeto = _avaliacaoService.Atualizar(id, objeto);

            System.out.println("Avaliacao atualizada com sucesso!");
        }
    }

    private void Excluir() {

        while (true) {
            System.out.println("Informe o ID da avaliação para exclusão:");

            try {
                int id = Integer.parseInt(sc.nextLine());
                _avaliacaoService.Excluir(id);
                System.out.println("Avaliacao excluida com sucesso!");
                break;

            } catch (NumberFormatException e) {
                System.out.println("ID inválido");
            } catch (RuntimeException e) {
                System.out.println("Erro ao excluir avaliacao");
            }
        }
    }
}
