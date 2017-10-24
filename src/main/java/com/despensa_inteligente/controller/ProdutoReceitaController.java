package com.despensa_inteligente.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.despensa_inteligente.model.ProdutoReceita;
import com.despensa_inteligente.repository.ProdutoReceitaRepository;

@SuppressWarnings("serial")
@RestController
@RequestMapping("/produtosReceitas")
public class ProdutoReceitaController implements Serializable {
	
	@Autowired private ProdutoReceitaRepository repository;
	     
    @GetMapping("/{id}")
	public ResponseEntity<ProdutoReceita> buscar(@PathVariable("id") Long id) {
    	verifyIfProdutoExists(id);
		ProdutoReceita produtoReceita = repository.findOne(id);
		return new ResponseEntity<ProdutoReceita>(produtoReceita,HttpStatus.OK);
	}
    
//    @GetMapping
//    public ResponseEntity<List<?>> buscaProdutoReceitaPorNome(@RequestParam("nome") Optional<String> nome) {
//    	List<?> produtoReceita = (nome.isPresent()) ? (List<?>) repository.findByNomeIgnoreCaseContaining(nome) : (List<?>) repository.findAll();
//    	if(produtoReceita == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//        return new ResponseEntity<List<?>>(produtoReceita,HttpStatus.OK);
//    }
    
    @GetMapping
    public ResponseEntity<?> buscaProdutoReceita() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ProdutoReceita produtoReceita) {
    	repository.save(produtoReceita);
    	produtoReceita.setId(null);
    	repository.save(produtoReceita);
    	if(true)
    		throw new RuntimeErrorException(null, "Test");
    	repository.save(produtoReceita);
    	return new ResponseEntity<>(repository.save(produtoReceita), HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProdutoReceita produtoReceita) {
    	verifyIfProdutoExists(produtoReceita.getId());
    	return new ResponseEntity<>(repository.save(produtoReceita), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long  id) {
    	verifyIfProdutoExists(id);
    	  repository.delete(id);
    	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    private void verifyIfProdutoExists(Long id) {
    	if(repository.findOne(id) == null)
    		throw new ResourceNotFoundException("Produto não encontrado para o ID "+id);
    }
	
}