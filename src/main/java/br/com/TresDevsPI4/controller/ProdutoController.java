/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.TresDevsPI4.controller;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.TresDevsPI4.model.ItensCompra;
import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.model.Imagem;
import br.com.TresDevsPI4.repositories.CategoriaRepository;
import br.com.TresDevsPI4.repositories.ImagemRepository;
import br.com.TresDevsPI4.repositories.ProdutoRepository;

/*
 *
 * @author nails
 */

@Controller
public class ProdutoController {

	private List<Imagem> listaImagem = new ArrayList<Imagem>();
	private Produto produto2 = new Produto();

	// auterar para caminho absoluto real da maquina
	// private static String caminhoImagens =
	// "C:/Users/renan.smaciel/workspace-spring-tool-suite-4-4.9.0.RELEASE";
	private static String caminhoImagens = "C:/Users/julia/workspace-spring-tool-suite-4-4.9.0.RELEASE";
	//private static String caminhoImagens = "C:/FACULDADE/SENAC_QUARTO SEMESTRE/PROJETO INTEGRADOR/TresDev-PI4/src/main/resources/static/image/";
	//// private static String caminhoImagens =
	// "C:/Users/diego/workspace-spring-tool-suite-4-4.9.0.RELEASE";

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ImagemRepository imagemRepository;

	@GetMapping("/administrativo")
	public ModelAndView login(HttpSession session) {
		if (!session.equals(null)) {
			ModelAndView mv = new ModelAndView("/administrativo/login");
			return mv;
		}
		ModelAndView mv = new ModelAndView("/administrativo2");
		return mv;
	}

	@GetMapping("/administrativo/cadastro")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produto/cadastro");
		mv.addObject("listaImagem", listaImagem);
		mv.addObject("produto", produto);
		mv.addObject("listaCategoria",categoriaRepository.buscarCategorias());
		return mv;
	}

	@GetMapping("/administrativo/lista")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/produto/lista");
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		Optional<Produto> produto = produtoRepository.findById((int) id);
		listaImagem = imagemRepository.buscarImagem(id);
		produto2 = produto.get();
		return cadastrar(produto.get());
	}

	@PostMapping("/administrativo/salvar")
	public ModelAndView salvar(Produto produto, BindingResult result) {
		if (result.hasErrors()) {
			return cadastrar(produto);

		}
		produtoRepository.save(produto);
		return cadastrar(new Produto());
	}

	@PostMapping("/administrativo/salvar/imagens")
	public ModelAndView salvarI(Produto produto, BindingResult result) {

		if (result.hasErrors()) {
			return cadastrar( produto);

		}
		int cont = 0;
		for (Imagem it : listaImagem) {
			if (cont == 0) {
				produto.setFoto1(it.getCaminho());
			}
			if (cont == 1) {
				produto.setFoto2(it.getCaminho());
			}
			if (cont == 2) {
				produto.setFoto3(it.getCaminho());
			}
			if (cont == 3) {
				produto.setFoto4(it.getCaminho());
			}
			cont++;
		}
		produtoRepository.save(produto);
		if (produto.getId() == null) {
			Integer id = produtoRepository.buscarIdProduto();
			imagemRepository.apagarImagem(id);
			for (Imagem it : listaImagem) {
				it.setIdProduto(id);
			}
		} else {
			
			for (Imagem it : listaImagem) {
				it.setIdProduto(produto.getId());
			}
		}

		for (Imagem it : listaImagem) {
			imagemRepository.save(it);
		}
		listaImagem.clear();
		/*
		 * try { if (!arquivo.isEmpty()) { byte[] bytes = arquivo.getBytes(); Path
		 * caminho = Paths .get(caminhoImagens + String.valueOf(produto.getId()) +
		 * arquivo.getOriginalFilename()); Files.write(caminho, bytes);
		 * 
		 * produto.setFoto1(String.valueOf(produto.getId()) +
		 * arquivo.getOriginalFilename()); produtoRepository.save(produto); }
		 * 
		 * if (!file2.isEmpty()) { byte[] bytes = file2.getBytes(); Path caminho = Paths
		 * .get(caminhoImagens + String.valueOf(produto.getId()) +
		 * file2.getOriginalFilename()); Files.write(caminho, bytes);
		 * 
		 * produto.setFoto2(String.valueOf(produto.getId()) +
		 * file2.getOriginalFilename()); produtoRepository.save(produto); }
		 * 
		 * if (!file3.isEmpty()) { byte[] bytes = file3.getBytes(); Path caminho = Paths
		 * .get(caminhoImagens + String.valueOf(produto.getId()) +
		 * file3.getOriginalFilename()); Files.write(caminho, bytes);
		 * 
		 * produto.setFoto3(String.valueOf(produto.getId()) +
		 * file3.getOriginalFilename()); produtoRepository.save(produto); }
		 * 
		 * if (!file4.isEmpty()) { byte[] bytes = file4.getBytes(); Path caminho = Paths
		 * .get(caminhoImagens + String.valueOf(produto.getId()) +
		 * file4.getOriginalFilename()); Files.write(caminho, bytes);
		 * 
		 * produto.setFoto4(String.valueOf(produto.getId()) +
		 * file4.getOriginalFilename()); produtoRepository.save(produto); }
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */
		return cadastrar(new Produto());
	}

	@GetMapping("/administrativo/mostrarImagens/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		// System.out.println(imagem);
		File imagemarquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {
			return Files.readAllBytes(imagemarquivo.toPath());
		}

		return null;
	}

	@GetMapping("/administrativo/inativar/{id}")
	public ModelAndView inativar(@PathVariable("id") int id) {
		Optional<Produto> produto = produtoRepository.findById((int) id);

		if (produto.get().getStatus() == false) {
			produto.get().setStatus(true);
			produtoRepository.save(produto.get());
			return listar();
		}

		else

			produto.get().setStatus(false);

		produtoRepository.save(produto.get());
		return listar();

	}

	@GetMapping("/administrativo/adicionar/{id}")
	public ModelAndView adicionarUm(@PathVariable("id") int id) {
		Optional<Produto> produto = produtoRepository.findById((int) id);

		produto.get().setQuantidade(produto.get().getQuantidade() + 1);

		produtoRepository.save(produto.get());
		return listarEstoque();

	}

	@GetMapping("/administrativo/remover/{id}")
	public ModelAndView removerUm(@PathVariable("id") int id) {
		Optional<Produto> produto = produtoRepository.findById((int) id);

		if (produto.get().getQuantidade() > 0) {
			if (produto.get().getQuantidade() == 1) {
				produto.get().setStatus(false);
			}
		produto.get().setQuantidade(produto.get().getQuantidade() - 1);
		produtoRepository.save(produto.get());
		}
		return listarEstoque();

	}

	@GetMapping("/administrativo/estoque")
	public ModelAndView listarEstoque() {
		ModelAndView mv = new ModelAndView("/administrativo/produto/listaEstoquista");
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;
	}

	@PostMapping("/adicionarImagem")
	public ModelAndView adicionarImagem(@RequestParam("file") MultipartFile arquivo, Produto produto) {
		Imagem imagem = new Imagem();

		try {
			byte[] bytes = arquivo.getBytes();
			Path caminho = Paths.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
			Files.write(caminho, bytes);

			imagem.setCaminho(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}
		listaImagem.add(imagem);

		ModelAndView mv = new ModelAndView("/administrativo/produto/cadastro");
		mv.addObject("produto", produto2);
		mv.addObject("listaImagem", listaImagem);
		mv.addObject("listaCategoria",categoriaRepository.findAll());
		return mv;
	}

	@GetMapping("/removerImagem/{caminho}")
	public ModelAndView removerProdutoCarrinho(@PathVariable String caminho, Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produto/cadastro");

		for (Imagem it : listaImagem) {
			if (it.getCaminho().equals(caminho)) {
				listaImagem.remove(it);
				imagemRepository.deleteById(it.getId());
				break;
			}
		}

		mv.addObject("produto", produto2);
		mv.addObject("listaImagem", listaImagem);
		mv.addObject("listaCategoria",categoriaRepository.buscarCategorias());
		return mv;

	}

}
