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

    public Venda Criar(Venda objeto, Integer idOrcamento) {
        if (objeto == null) {
            throw new RuntimeException("Objeto Venda vazio. Preencha as informações.");
        }

        try {
            if (objeto.getIdOrcamento() != null) {
                Orcamento orcamento = _orcamentoDao.BuscaId(idOrcamento);
                objeto.setIdOrcamento(idOrcamento);
                if (orcamento == null) {
                    throw new RuntimeException("Erro: O Orçamento informado não existe.");
                }
            } else {
                throw new RuntimeException("Erro: É necessário vincular um ID de Orçamento para criar uma venda.");
            }

            return _vendaDao.Criar(objeto);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Venda> BuscaGeral() {
        try {

            return _vendaDao.BuscaGeral();

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Venda BuscaPorId(Integer id) {
        try {
            return _vendaDao.BuscaId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean AtualizarStatusPagamento(Integer id, Venda.StatusPagamento statusPagamento) {
        if (statusPagamento == null) {
            throw new RuntimeException("Objeto vazio. Preencha as informações.");
        }

        try {
            Venda vendaExistente = _vendaDao.BuscaId(id);
            if (vendaExistente == null) {
                throw new RuntimeException("Venda não encontrada para atualização.");
            }

            return _vendaDao.AtualizarStatusPagamento(id, statusPagamento);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}