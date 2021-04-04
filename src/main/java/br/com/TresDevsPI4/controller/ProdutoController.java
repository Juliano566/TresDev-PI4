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
import java.util.Optional;

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



import br.com.TresDevsPI4.model.Produto;
import br.com.TresDevsPI4.repositories.ProdutoRepository;

/*
 *
 * @author nails
 */

@Controller
public class ProdutoController {

	// auterar para caminho absoluto real da maquina
	private static String caminhoImagens = "C:/FACULDADE/SENAC_QUARTO SEMESTRE/PROJETO INTEGRADOR/TresDev-PI4/src/main/resources/static/image/";

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/administrativo")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/administrativo/login");
		
		return mv;
	}

	@GetMapping("/administrativo/cadastro")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/cadastro");
		mv.addObject("produto", produto);
		return mv;
	}
	

	@GetMapping("/administrativo/lista")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/lista");
		mv.addObject("listaProdutos", produtoRepository.findAll());
		return mv;
	}

	@GetMapping("/administrativo/editar/{id}")
	public ModelAndView editar(@PathVariable("id") int id) {
		Optional<Produto> produto = produtoRepository.findById((int) id);
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
	public ModelAndView salvarI(Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo) {

		if (result.hasErrors()) {
			return cadastrar(produto);

		}

		produtoRepository.save(produto);

		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				produto.setFoto1(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				produtoRepository.save(produto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		produtoRepository.save(produto);
		return cadastrar(new Produto());
	}

	
	
	@GetMapping("/administrativo/mostrarImagens/{imagem}")
	@ResponseBody
	public byte[] retornarImagem (@PathVariable("imagem") String imagem) throws IOException {
		//System.out.println(imagem);
		File imagemarquivo = new File(caminhoImagens+imagem);
		if(imagem!= null ||imagem.trim().length()>0) {
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
	
	
	
	
	
	
	
	
}
