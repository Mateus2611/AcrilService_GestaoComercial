package DAO.Interfaces;

import java.util.List;

public interface IOperacoesGenericasDAO<ID, T> {

    public T Criar(T objeto);

    public T Atualizar (ID id, T objeto);

    public void Excluir (ID id);

}
