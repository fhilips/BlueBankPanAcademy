package br.com.pan.bluebank.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pan.bluebank.dtos.ClienteDTO;
import br.com.pan.bluebank.dtos.response.MessageResponse;
import br.com.pan.bluebank.dtos.response.MessageResponseImpl;
import br.com.pan.bluebank.models.Cliente;
import br.com.pan.bluebank.services.ClienteService;
import br.com.pan.bluebank.services.EmailNotificationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "v1/clientes")
public class ClienteController implements MessageResponse {
	
	@Autowired
	private ClienteService service;

	@Autowired
	private EmailNotificationService notification;
	
	@ApiOperation(value = "Retorna um cliente a partir do id informado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna o cliente"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping(path = "/{id}", produces="application/json")
	public ResponseEntity<Cliente> findById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.findById(id));
	}

	@ApiOperation(value = "Retorna uma lista de clientes")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna a lista de clientes"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@GetMapping(produces="application/json")
	public ResponseEntity<List<Cliente>> findAll() {
		return ResponseEntity.ok(this.service.findAll());
	}
	
	@ApiOperation(value = "Salva um novo cliente")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Salva o cliente"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PostMapping(consumes = "application/json")
	public ResponseEntity<MessageResponseImpl> create(@RequestBody ClienteDTO dto){
		Cliente newCliente = this.service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCliente.getId()).toUri();
		return ResponseEntity.created(uri).body(createMessageResponse("Cliente criado com sucesso!"));
	}

	@ApiOperation(value = "Atualiza um cliente a partir do id informado")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Atualiza o cliente"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 500, message = "Foi gerada uma exceção"),
	})
	@PutMapping(value = "/{id}",consumes="application/json", produces="application/json")
		public ResponseEntity<MessageResponseImpl> update(@PathVariable Long id,
		@RequestBody ClienteDTO dto) {
		this.service.update(id, dto);
		return ResponseEntity.ok(createMessageResponse("Cliente atualizado com sucesso!"));
	}
	
	@GetMapping("/sendnotification")
	public String publishMessageToTopic(){	
		 notification.publishMessageToTopic();
		 return "Notification send successfully !!";
	}
}
