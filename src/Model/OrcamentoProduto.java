package Model;

public class OrcamentoProduto {

    public OrcamentoProduto() {
    }

    public OrcamentoProduto(Integer idOrcamento, Integer idProduto, Integer quantidade) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
        Quantidade = quantidade;
    }

    private Integer IdOrcamento;
    private Integer IdProduto;
    public Integer Quantidade;


    public int getIdOrcamento() {
        return IdOrcamento;
    }

    public void setIdOrcamento(int idOrcamento) {
        IdOrcamento = idOrcamento;
    }

    public int getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(int idProduto) {
        IdProduto = idProduto;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.Quantidade = quantidade;
    }
}
