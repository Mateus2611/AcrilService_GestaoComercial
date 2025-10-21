package Service.Interface;

public interface IOperacoesGenericasService<T, ID> {

    public T Create(T element);

    public T SearchAll();

    public T Update (ID id, T element);

    public T Delete (ID id);
}
