package Service;

import Model.Venda;
import Service.Interface.IOperacoesGenericasService;

import java.util.List;

public class VendaService implements IOperacoesGenericasService<Venda, Integer> {

    @Override
    public Venda Criar(Venda objeto) {
        return null;
    }

    @Override
    public List<Venda> BuscaGeral() {
        return null;
    }

    @Override
    public Venda Atualizar(Integer integer, Venda objeto) {
        return null;
    }

    @Override
    public void Excluir(Integer integer) {}
}
