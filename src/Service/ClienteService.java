package Service;

import Model.Cliente;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class ClienteService implements IOperacoesGenericasService<Cliente, Integer> {


    @Override
    public Cliente Criar(Cliente objeto) {
        return null;
    }

    @Override
    public List<Cliente> BuscaGeral() {
        return null;
    }

    @Override
    public Cliente Atualizar(Integer integer, Cliente objeto) {
        return null;
    }

    @Override
    public void Excluir(Integer id) {    }
}
