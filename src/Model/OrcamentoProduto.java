package Model;

public class OrcamentoProduto {

    public OrcamentoProduto() {
    }

    public OrcamentoProduto(Integer idOrcamento, Integer idProduto, Integer quantidade) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
        Quantidade = quantidade;
    }

    public OrcamentoProduto(Integer idOrcamento, Integer idProduto) {
        IdOrcamento = idOrcamento;
        IdProduto = idProduto;
    }

    private Integer IdOrcamento;
    private Integer IdProduto;
    public Integer Quantidade;

    public Integer getIdOrcamento() {
        return IdOrcamento;
    }

    public void setIdOrcamento(Integer idOrcamento) {
        IdOrcamento = idOrcamento;
    }

    public Integer getIdProduto() {
        return IdProduto;
    }

    public void setIdProduto(Integer idProduto) {
        IdProduto = idProduto;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }

}
