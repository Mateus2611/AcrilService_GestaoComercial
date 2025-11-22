package Service.Interface;

import java.util.List;

public interface IOperacoesGenericasService<T, ID> {

    public T Criar(T objeto);

    public List<T> BuscaGeral();

    public T Atualizar (ID id, T objeto);

    public void Excluir (ID id);
}
