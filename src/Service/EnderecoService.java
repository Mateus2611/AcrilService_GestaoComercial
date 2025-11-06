package Service;

import DAO.MySQL.EnderecoDAO;
import Model.Endereco;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class EnderecoService implements IOperacoesGenericasService<Endereco, Integer> {

    private final EnderecoDAO _enderecoDao;

    public EnderecoService(EnderecoDAO enderecoDao) {
        _enderecoDao = enderecoDao;
    }

    @Override
    public Endereco Criar(Endereco objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _enderecoDao.Criar(objeto);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Endereco> BuscaGeral() {
        try {
            return _enderecoDao.BuscaGeral();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Endereco BuscaPorId(Integer id) {
        try {
            return _enderecoDao.BuscaPorId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Endereco Atualizar(Integer id, Endereco objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            _enderecoDao.BuscaPorId(id);

            return _enderecoDao.Atualizar(id, objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void Excluir(Integer id) {
        try {
            _enderecoDao.BuscaPorId(id);

            _enderecoDao.Excluir(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
