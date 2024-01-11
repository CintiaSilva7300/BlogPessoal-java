package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")//corns
public class PostagemController {

	@Autowired //injecao de dependencia altomatica
	private final PostagemRepository postagemRepository;
	
	public PostagemController(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }
	
	@GetMapping("/lista")
    public ResponseEntity<List<Postagem>> getAll()  {
		return ResponseEntity.ok(postagemRepository.findAll());
    }
	
	@GetMapping("/lista2")
    public ResponseEntity<List<Postagem>> getAll2()  {
		return ResponseEntity.badRequest().build();
    }
	
}
