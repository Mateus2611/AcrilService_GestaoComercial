package Service;

import DAO.MySQL.OrcamentoProdutoDAO;
import Model.OrcamentoProduto;

import java.util.List;


public class OrcamentoProdutoService {

    private final OrcamentoProdutoDAO _orcamentoProdutoDao;

    public OrcamentoProdutoService(OrcamentoProdutoDAO orcamentoProdutoDao) {
        _orcamentoProdutoDao = orcamentoProdutoDao;
    }

    public OrcamentoProduto Criar(OrcamentoProduto objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _orcamentoProdutoDao.AdicionarProduto(objeto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<OrcamentoProduto> BuscaOrcamentoId(Integer idORcamento) {
        try {
            return _orcamentoProdutoDao.BuscaOrcamentoId(idORcamento);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public OrcamentoProduto AtualizarQuantidade(Integer id, OrcamentoProduto objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            return _orcamentoProdutoDao.AtualizarQuantidade(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void Excluir(Integer id, OrcamentoProduto objeto) {
        try {
            _orcamentoProdutoDao.RemoverItem(objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
