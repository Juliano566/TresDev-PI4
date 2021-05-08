package br.com.TresDevsPI4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.model.ItensCompra;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.EnderecoRepository;
import br.com.TresDevsPI4.repositories.ItensCompraRepositorio;
import br.com.TresDevsPI4.repositories.ProdutoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class CarrinhoController {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();

	@Autowired
	private ItensCompraRepositorio repositorioItensCompra;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoRepository repositorioProduto;

	private Compra compra = new Compra();

	private void calcularTotal() {
		compra.setValorTotal(0.);
		for (ItensCompra it : itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	@GetMapping("/loja/carrinho")
	public ModelAndView detalhes(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/carrinho");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@GetMapping("/loja/checkout-endereco")
	public ModelAndView endereco(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/CheckoutEndereco");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/loja/checkout-formapg")
	public ModelAndView pagamento(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/CheckoutFormaPagamento");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/loja/checkout-cartao")
	public ModelAndView addCartao(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/novoCartao");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("/loja/checkout-parcelamento")
	public ModelAndView addParcelamento(Produto produto) {
		ModelAndView mv = new ModelAndView("/loja/checkoutParcelamento");
		mv.addObject("produto", produto);
		return mv;
	}

	@GetMapping("c")
	public ModelAndView ChamarCarrinho() {
		ModelAndView mv = new ModelAndView("loja/carrinho");
		mv.addObject("listaItens", itensCompra);

		return mv;
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("Loja/carrinho");
		Optional<Produto> prod = repositorioProduto.findById(id);
		Produto produto = prod.get();

		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				break;
			}
		}
		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setProduto(produto);
			item.setValorUnitario(produto.getPreco());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));
			itensCompra.add(item);

		}

		return "redirect:/loja/carrinho";
	}

	// adiciona item carrinho + -
	@GetMapping("/alterarQuantidade/{id}/{acao}/{quantidade}")
	public String alterarQuantidade(@PathVariable Integer id, @PathVariable Integer acao,
			@PathVariable Integer quantidade) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				if (acao.equals(1)) {
					if (it.getQuantidade() < quantidade) {
						it.setQuantidade(it.getQuantidade() + 1);
						it.setValorTotal(0.);
						it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					}
				} else if (acao == 0) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
					if (it.getQuantidade() == 0) {
						removerProdutoCarrinho(id);
					}
				}

				break;

			}

		}
		return "redirect:/loja/carrinho";

	}

	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Integer id) {
		ModelAndView mv = new ModelAndView("/loja/carrinho");
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				itensCompra.remove(it);
				break;
			}
		}
		mv.addObject("listaItens", itensCompra);
		return "redirect:/loja/carrinho";

	}
}
