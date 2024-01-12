package com.generation.blogpessoal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")//corns
public class PostagemController {

	@Autowired //injecao de dependencia altomatica
	private final PostagemRepository postagemRepository;
	
	public PostagemController(PostagemRepository postagemRepository) {
        this.postagemRepository = postagemRepository;
    }
	
	@GetMapping("/list")
    public ResponseEntity<List<Postagem>> getAll()  {
		return ResponseEntity.ok(postagemRepository.findAll());
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Postagem> getById(@PathVariable Long id)  {
		return postagemRepository.findById(id)
				.map(response -> ResponseEntity.ok(response))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
	
	@GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

	@PostMapping("/cria")
    public ResponseEntity<Postagem> criaPostagem(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(postagemRepository.save(postagem));
    }
	
	@PutMapping("/atualiza/{id}")
	public ResponseEntity<Postagem> atualizaPostagem(@PathVariable Long id, @Valid @RequestBody Postagem postagem) {
	    return postagemRepository.findById(id)
	            .map(postagemExistente -> {
	                postagemExistente.setTexto(postagem.getTexto());
	                postagemExistente.setTitulo(postagem.getTitulo());

	                return ResponseEntity.status(HttpStatus.OK)
	                        .body(postagemRepository.save(postagemExistente));
	            })
	            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/exclui/{id}")
	public ResponseEntity<Object> excluiPostagem(@PathVariable Long id) {
	    if (postagemRepository.existsById(id)) {
	        postagemRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } else {
	        String mensagem = "Postagem com ID " + id + " não encontrada.";
	        Map<String, String> response = new HashMap<>();
	        response.put("mensagem", mensagem);
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	}
}
