package com.example.produto.controller;

import com.example.produto.entity.Produto;
import com.example.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Listagem de todos os produtos
    @GetMapping
    public String listar(Model model) {
        List<Produto> produtos = produtoService.listarTodos();
        model.addAttribute("produtos", produtos);
        return "produtos/listar";
    }

    // Página para criar novo produto
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "produtos/formulario";
    }

    // Salvar novo produto
    @PostMapping
    public String salvar(@ModelAttribute Produto produto) {
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }

    // Visualizar um produto específico
    @GetMapping("/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            model.addAttribute("produto", produto.get());
            return "produtos/visualizar";
        }
        return "redirect:/produtos";
    }

    // Página para editar um produto
    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        Optional<Produto> produto = produtoService.buscarPorId(id);
        if (produto.isPresent()) {
            model.addAttribute("produto", produto.get());
            return "produtos/formulario";
        }
        return "redirect:/produtos";
    }

    // Atualizar um produto
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Produto produto) {
        produtoService.atualizar(id, produto);
        return "redirect:/produtos/" + id;
    }

    // Deletar um produto
    @GetMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return "redirect:/produtos";
    }

    // Página inicial (redirecionamento)
    @GetMapping("/")
    public String index() {
        return "redirect:/produtos";
    }
}

