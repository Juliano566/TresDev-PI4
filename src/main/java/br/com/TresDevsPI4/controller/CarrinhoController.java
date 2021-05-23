package br.com.TresDevsPI4.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.TresDevsPI4.model.Cliente;
import br.com.TresDevsPI4.model.Compra;
import br.com.TresDevsPI4.model.Endereco;
import br.com.TresDevsPI4.model.Funcionario;
import br.com.TresDevsPI4.model.ItensCompra;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.CompraRepositorio;
import br.com.TresDevsPI4.repositories.EnderecoRepository;
import br.com.TresDevsPI4.repositories.ItensCompraRepositorio;
import br.com.TresDevsPI4.repositories.ProdutoRepository;
import br.com.TresDevsPI4.services.Util;

@Controller
public class CarrinhoController {

	private static String caminhoImagens = "C:/Users/julia/workspace-spring-tool-suite-4-4.9.0.RELEASE";
	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
	private Cliente cliente;

	@Autowired
	private ItensCompraRepositorio repositorioItensCompra;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoRepository repositorioProduto;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private CompraRepositorio compraRepository;

	private Compra compra = new Compra();

	Integer id_endereco;

	Integer pagamento;

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

	@GetMapping("/loja/checkout-endereco/{id}")
	public ModelAndView endereco(Produto produto, @PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/CheckoutEndereco");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("listaEndereco", enderecoRepository.buscarEndereco(id));
		return mv;
	}

	@GetMapping("/loja/checkout-endereco2/{id}")
	public ModelAndView endereco2(Produto produto, @PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/CheckoutEndereco2");
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("listaEndereco", enderecoRepository.buscarEndereco(id));
		return mv;
	}

	@GetMapping("/loja/formaDePagamento/{id}")
	public ModelAndView pagamento2(Produto produto, @PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/CheckoutFormaPagamento");
		id_endereco = id;
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@GetMapping("/loja/cartao/{id}")
	public ModelAndView cartao(Produto produto, @PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/NovoCartao");
		mv.addObject("pagamento", id);
		return mv;
	}

	@GetMapping("/loja/resumo/{id}")
	public ModelAndView pagamento(Produto produto, @PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/resumoVenda");
		pagamento = id;
		compra.setFrete(compra.getValorTotal() * 0.01);
		mv.addObject("produto", produto);
		calcularTotal();
		mv.addObject("compra", compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("pagamento", pagamento);
		mv.addObject("listaEndereco", enderecoRepository.buscarEndereco2(id_endereco));
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
			System.out.println("teste2");
			System.out.println(itensCompra.indexOf(compra.getValorTotal()));

		}
		System.out.println("teste3");
		System.out.println(itensCompra.toString());
		return "redirect:/loja/carrinho";

	}

	@GetMapping("/mostrarImagens/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		// System.out.println(imagem);
		File imagemarquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemarquivo.toPath());
		}
		return null;
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

	@GetMapping("/finalizarVenda/{id}")
	public ModelAndView salvar(@PathVariable("id") int id) {
		ModelAndView mv = new ModelAndView("/loja/vendaFinalizada");
		compra.setId_cliente(id);
		compra.setEndereco(id_endereco);
		

		switch (pagamento) {
		case 1: {
			compra.setFormaPagamento("Cartão de credito");
			break;
		}
		case 2: {
			compra.setFormaPagamento("Cartão de debito");
			break;
		}
		case 3: {
			compra.setFormaPagamento("Pix");
			break;
		}
		case 4: {
			compra.setFormaPagamento("boleto");
			break;
		}
		}

		Integer con = 0;
		for (ItensCompra it : itensCompra) {
			con += it.getQuantidade();
		}
		compra.setQuantidade(con);
		compra.setStatus("Aguardando Pagamento");
		compraRepository.save(compra);
		Integer idCompra = compraRepository.buscarIdCompra();
		for (ItensCompra it : itensCompra) {
			it.setId_compra(idCompra);
		}
		for (ItensCompra it : itensCompra) {
			repositorioItensCompra.save(it);
		}
		itensCompra.clear();
		compra.setId(null);
		mv.addObject("numeroPedido", idCompra);
		return mv;
	}

}
