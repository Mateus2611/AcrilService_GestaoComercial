package DAO.Interfaces;

public interface IOperacoesGenericasDAO<ID, T> {

    public T Criar(T objeto);

    public T BuscaGeral();

    public T Atualizar (ID id, T objeto);

    public T Excluir (ID id);
}