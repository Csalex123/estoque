package br.com.ifpe.estoque.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.ifpe.estoque.model.CategoriaProduto;
import br.com.ifpe.estoque.model.CategoriaProdutoDao;
import br.com.ifpe.estoque.model.Produto;
import br.com.ifpe.estoque.model.ProdutoDao;
import br.com.ifpe.estoque.util.Util;

@Controller
public class ProdutoController {

	@RequestMapping("/produto/add")
	public String adicionarProduto(Model model) {
			
		// Código para popular o combo de categoria de produto
		CategoriaProdutoDao dao = new CategoriaProdutoDao();
		List<CategoriaProduto> listaCategoriaProduto = dao.listar(null);
		model.addAttribute("listaCategoriaProduto", listaCategoriaProduto);
		
		return "produto/incluirProduto";
	}

	@RequestMapping("/produto/edit")
	public String edit(@RequestParam("id") Integer id, Model model) {
		ProdutoDao dao = new ProdutoDao();
		Produto produto = dao.buscarPorId(id);
		model.addAttribute("produto", produto);
		return "produto/alterarProduto";
	}

	@RequestMapping("/produto/lista")
	public String listarProduto(Model model) {
		ProdutoDao dao = new ProdutoDao();
		List<Produto> listaProduto = dao.listar(null);
		model.addAttribute("listaProduto", listaProduto);
		return "produto/listaProduto";
	}

	@RequestMapping("/produto/save")
	public String save(Produto produto, @RequestParam("file") MultipartFile imagem) {

		if (Util.fazerUploadImagem(imagem)) {
			produto.setImagem(Util.obterMomentoAtual() + " - " + imagem.getOriginalFilename());
		}

		ProdutoDao dao = new ProdutoDao();
		dao.salvar(produto);
		return "produto/incluirProdutoSucesso";
	}

	@RequestMapping("/produto/filter")
	public String filtrarProduto(Produto produto, Model model) {
		ProdutoDao dao = new ProdutoDao();
		List<Produto> listaProduto = dao.listar(produto);
		model.addAttribute("listaProduto", listaProduto);
		return "produto/listaProduto";
	}
}
