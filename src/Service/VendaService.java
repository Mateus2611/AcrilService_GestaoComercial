package Service;

import DAO.MySQL.OrcamentoDAO;
import DAO.MySQL.VendaDAO;
import Model.Orcamento;
import Model.Venda;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class VendaService {

    private final VendaDAO _vendaDao;
    private final OrcamentoDAO _orcamentoDao;

    public VendaService(VendaDAO vendaDao, OrcamentoDAO orcamentoDao) {
        _vendaDao = vendaDao;
        _orcamentoDao = orcamentoDao;
    }

    public Venda Criar(Integer idOrcamento, Integer prazo) {
        Venda objeto = new Venda();

        try {
            Orcamento orcamento = _orcamentoDao.BuscaId(idOrcamento);
            objeto.setIdOrcamento(idOrcamento);
            if (orcamento == null) {
                throw new RuntimeException("Erro: O Orçamento informado não existe.");
            }
            objeto = _vendaDao.Criar(objeto, prazo);
            objeto.setOrcamento(orcamento);

            return objeto;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Venda> BuscaGeral() {
        try {

            List<Venda> vendas = _vendaDao.BuscaGeral();

            for (Venda venda : vendas) {
                Orcamento orcamento = _orcamentoDao.BuscaId(venda.getIdOrcamento());
                venda.setOrcamento(orcamento);
            }

            return vendas;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Venda BuscaPorId(Integer id) {
        try {
            Venda venda = _vendaDao.BuscaId(id);
            venda.setOrcamento(
                    _orcamentoDao.BuscaId(
                            venda.getIdOrcamento()
                    ));

            return venda;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Venda AtualizarStatusPagamento(Integer id, String statusPagamento) {
        if (statusPagamento == null) {
            throw new RuntimeException("Objeto vazio. Preencha as informações.");
        }

        try {
            if (!_vendaDao.AtualizarStatusPagamento(id, statusPagamento))
                throw new RuntimeException("Falha ao atualizar status da venda.");

            return BuscaPorId(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}