package br.com.pan.bluebank.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pan.bluebank.dto.ContaDTO;
import br.com.pan.bluebank.model.Conta;
import br.com.pan.bluebank.services.ContaService;

@RestController
@RequestMapping(path = "v1/contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@GetMapping
	public ResponseEntity<List<Conta>> findAll(){
		List<Conta> listaContas = contaService.findAll();
		return ResponseEntity.ok(listaContas);
		
	}
	
	@GetMapping(value = "/ativas")
	public ResponseEntity<List<Conta>> findAllAtivas(){
		List<Conta> listaContas = contaService.findAllAtivas();
		return ResponseEntity.ok(listaContas);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Conta> findById(@PathVariable Long id){
		Conta conta = contaService.findById(id);
		return ResponseEntity.ok(conta);
	}
	
	@PostMapping
	public ResponseEntity<Conta> create(@RequestBody ContaDTO dto){
		Conta newConta = contaService.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newConta.getId()).toUri();
		return ResponseEntity.created(uri).build();
		}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<Conta> alterarStatus(@PathVariable Long id, 
			@RequestParam String status){
		Conta contaInativada = contaService.alterarStatus(id, status);
		return ResponseEntity.ok(contaInativada);
	}
	
}