package Service;

import DAO.MySQL.OrcamentoDAO;
import Model.Orcamento;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class OrcamentoService {

    private final OrcamentoDAO _orcamentoDao;

    public OrcamentoService(OrcamentoDAO orcamentoDao) {
        _orcamentoDao = orcamentoDao;
    }

    public Orcamento Criar(Orcamento objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _orcamentoDao.Criar(objeto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Orcamento> BuscaGeral() {
        try {
            return _orcamentoDao.BuscaGeral();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento BuscaPorId(Integer id) {
        try {
            return _orcamentoDao.BuscaId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento Atualizar(Integer id, Orcamento objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            _orcamentoDao.BuscaId(id);

            return _orcamentoDao.AtualizarStatus(id, objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
