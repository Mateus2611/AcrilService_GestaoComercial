package Service;

import DAO.MySQL.EnderecoDAO;
import Model.Endereco;
import Service.Interface.IOperacoesGenericasService;

public class EnderecoService implements IOperacoesGenericasService<Endereco, Integer> {

    private final EnderecoDAO _enderecoDao;

    public EnderecoService(EnderecoDAO enderecoDao) {
        _enderecoDao = enderecoDao;
    }

    @Override
    public Endereco Criar(Endereco objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        return _enderecoDao.Criar(objeto);
    }

    @Override
    public Endereco BuscaGeral() {
        return null;
    }

    @Override
    public Endereco Atualizar(Integer integer, Endereco objeto) {
        return null;
    }

    @Override
    public Endereco Excluir(Integer integer) {
        return null;
    }
}
