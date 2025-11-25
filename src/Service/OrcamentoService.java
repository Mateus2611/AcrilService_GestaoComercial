package Service;

import DAO.MySQL.ClienteDAO;
import DAO.MySQL.OrcamentoDAO;
import DAO.MySQL.OrcamentoProdutoDAO;
import DAO.MySQL.ProdutoDAO;
import Model.Cliente;
import Model.Orcamento;
import Model.OrcamentoProduto;
import Model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoService {

    private final OrcamentoDAO _orcamentoDao;
    private final ClienteDAO _clienteDao;
    private final OrcamentoProdutoDAO _orcamentoProdutoDao;
    private final ProdutoDAO _produtoDao;

    public OrcamentoService(OrcamentoDAO orcamentoDao, ClienteDAO clienteDao,
                            OrcamentoProdutoDAO orcamentoProdutoDao, ProdutoDAO produtoDao) {
        _orcamentoDao = orcamentoDao;
        _clienteDao = clienteDao;
        _orcamentoProdutoDao = orcamentoProdutoDao;
        _produtoDao = produtoDao;
    }

    public Orcamento Criar(Orcamento objeto, Integer validade) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações");

        try {
            return _orcamentoDao.Criar(objeto, validade);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Orcamento> BuscaGeral() {
        try {
            List<Orcamento> orcamentos = _orcamentoDao.BuscaGeral();

            for (Orcamento orcamento : orcamentos) {

                try {
                    Cliente cliente = _clienteDao.BuscaPorId(orcamento.getIdCliente());
                    if (cliente != null) {
                        orcamento.setNomeCliente(cliente.getNome());
                    }
                } catch (RuntimeException e) {
                    orcamento.setNomeCliente("Nenhum cliente encontrado");
                }

                List<OrcamentoProduto> produtos = _orcamentoProdutoDao.BuscaOrcamentoId(orcamento.getId());
                List<String> nomeProdutos = new ArrayList<>();
                BigDecimal valorTotal = BigDecimal.ZERO;

                for (OrcamentoProduto produto : produtos) {
                    try {
                        Produto p = _produtoDao.BuscaPorId(produto.getIdProduto());
                        if (p != null) {
                            nomeProdutos.add(p.getNome());

                            BigDecimal valor = p.getValor().multiply(new BigDecimal(produto.getQuantidade()));
                            valorTotal = valorTotal.add(valor);
                        }
                    } catch (RuntimeException e) {
                        nomeProdutos.add("Nenhum produto encontrado");
                    }
                }
                orcamento.setNomeProdutos(nomeProdutos);

                if (orcamento.getDesconto() != null) {
                    valorTotal = valorTotal.subtract(orcamento.getDesconto());
                }

                if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                    valorTotal = BigDecimal.ZERO;
                    orcamento.setDesconto(BigDecimal.ZERO);
                }

                orcamento.setValor(valorTotal);
            }
            return orcamentos;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento BuscaPorId(Integer id) {
        try {
            Orcamento orcamento = _orcamentoDao.BuscaId(id);

            if (orcamento != null) {

                try {
                    Cliente cliente = _clienteDao.BuscaPorId(orcamento.getIdCliente());
                    if (cliente != null) {
                        orcamento.setNomeCliente(cliente.getNome());
                    }
                } catch (RuntimeException e) {
                    orcamento.setNomeCliente("Nenhum cliente encontrado");
                }

                List<OrcamentoProduto> produtos = _orcamentoProdutoDao.BuscaOrcamentoId(orcamento.getId());
                List<String> nomeProdutos = new ArrayList<>();
                BigDecimal valorTotal = BigDecimal.ZERO;

                for (OrcamentoProduto produto : produtos) {
                    try {
                        Produto p = _produtoDao.BuscaPorId(produto.getIdProduto());
                        if (p != null) {
                            nomeProdutos.add(p.getNome());

                            BigDecimal valor = p.getValor().multiply(new BigDecimal(produto.getQuantidade()));
                            valorTotal = valorTotal.add(valor);
                        }
                    } catch (RuntimeException e) {
                        nomeProdutos.add("Nenhum produto encontrado");
                    }
                }
                orcamento.setNomeProdutos(nomeProdutos);

                if (orcamento.getDesconto() != null) {
                    valorTotal = valorTotal.subtract(orcamento.getDesconto());
                }

                if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                    valorTotal = BigDecimal.ZERO;
                    orcamento.setDesconto(BigDecimal.ZERO);
                }

                orcamento.setValor(valorTotal);
            }

            return orcamento;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Orcamento Atualizar(Integer id, Orcamento objeto) {
        if (objeto == null)
            throw new RuntimeException("Objeto vazio. Preencha as informações.");

        try {
            _orcamentoDao.BuscaId(id);

            return _orcamentoDao.AtualizarStatus(id, objeto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
