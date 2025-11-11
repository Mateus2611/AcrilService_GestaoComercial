package Service;

import DAO.MySQL.ProdutoDAO;
import Model.Produto;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class ProdutoService implements IOperacoesGenericasService<Produto, Integer> {

    private final ProdutoDAO _produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        _produtoDAO = produtoDAO;
    }

    @Override
    public Produto Criar(Produto objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _produtoDAO.Criar(objeto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Produto> BuscaGeral() {
        try {
            return _produtoDAO.BuscaGeral();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Produto Atualizar(Integer id, Produto objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            return _produtoDAO.Atualizar(id, objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void Excluir(Integer id) {
        try {
            _produtoDAO.BuscaPorId(id);

            _produtoDAO.Excluir(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
