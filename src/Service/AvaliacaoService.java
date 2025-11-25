package Service;

import DAO.MySQL.AvaliacaoDAO;
import Model.Avaliacao;
import Model.Venda;

import java.util.List;

public class AvaliacaoService {

    private final AvaliacaoDAO _avaliacaoDao;
    private final VendaService _vendaService;

    public AvaliacaoService(AvaliacaoDAO avaliacaoDao, VendaService vendaService) {
        _avaliacaoDao = avaliacaoDao;
        _vendaService = vendaService;
    }

    public Avaliacao Criar(Avaliacao objeto, Integer idVenda) {
        if (objeto == null) {
            throw new RuntimeException("Objeto Avaliação vazio. Preencha as informações.");
        }

        try {
            // Verifica se o ID foi passado via parâmetro ou se já consta no objeto
            if (idVenda != null) {
                objeto.setIdVenda(idVenda);
            }

            if (objeto.getIdVenda() != null) {
                // Validação usando o VendaService para garantir que a venda existe
                Venda venda = _vendaService.BuscaPorId(objeto.getIdVenda());

                if (venda == null) {
                    throw new RuntimeException("Erro: A Venda informada não existe.");
                }

            } else {
                throw new RuntimeException("Erro: É necessário vincular um ID de Venda para criar uma avaliação.");
            }

            return _avaliacaoDao.Criar(objeto);

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Avaliacao> BuscaGeral() {
        try {
            return _avaliacaoDao.BuscaGeral();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Avaliacao BuscaPorId(Integer id) {
        try {
            Avaliacao avaliacao = _avaliacaoDao.BuscaId(id);
            if (avaliacao == null) {
                throw new RuntimeException("Avaliação não encontrada.");
            }
            return avaliacao;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // Método útil para buscar a avaliação através do ID da Venda
    public Avaliacao BuscaPorIdVenda(Integer idVenda) {
        try {
            return _avaliacaoDao.buscaPorIdVenda(idVenda);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Avaliacao Atualizar(Integer id, Avaliacao objeto) {
        if (objeto == null) {
            throw new RuntimeException("Objeto vazio. Preencha as informações.");
        }

        try {
            Avaliacao avaliacaoExistente = _avaliacaoDao.BuscaId(id);

            if (avaliacaoExistente == null) {
                throw new RuntimeException("Avaliação não encontrada para atualização.");
            }

            // Garante que o ID do objeto a ser atualizado é o mesmo do parâmetro
            objeto.setId(id);

            return _avaliacaoDao.Atualizar(id, objeto);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void Excluir(Integer id) {
        try {
            Avaliacao avaliacaoExistente = _avaliacaoDao.BuscaId(id);

            if (avaliacaoExistente == null) {
                throw new RuntimeException("Avaliação não encontrada para exclusão.");
            }

            _avaliacaoDao.Excluir(id);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}