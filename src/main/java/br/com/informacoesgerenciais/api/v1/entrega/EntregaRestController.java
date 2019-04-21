package br.com.informacoesgerenciais.api.v1.entrega;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.response.ServiceMessage;
import br.com.response.ServiceResponse;
import br.com.services.MessagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/entrega")
@Api(value = "Promocao")
public class EntregaRestController {


	@Autowired
	EntregaService entregaService;	
	
	@Autowired
	private MessagesService messages;
	
	public static final String SALVO_COM_SUCESSO = "salvo.sucesso";
	public static final String EXCLUIDO = "registro.excluido";
	
	@ApiOperation(value = "Detalha uma entrega pelo ID", notes = "Um ID válido deve ser informado", response = Entrega.class)
	@GetMapping("/{id}")
	public ResponseEntity<ServiceResponse<Entrega>> getEntrega(@PathVariable Integer id) {
		return ResponseEntity.ok(new ServiceResponse<>(entregaService.getEntrega(id)));
	}

	
	@GetMapping
	@ApiOperation(value = "Lista", response = Entrega.class)
	public ServiceResponse<Page<Entrega>> getEntregas(Pageable pageable) {
		return new ServiceResponse<>(entregaService.getEntregas(pageable));
	}
	

	@PostMapping
	@ApiOperation(value = "Cria uma entrega", response = Entrega.class)
	public ResponseEntity<ServiceResponse<Entrega>> criaEntrega(@RequestBody @Valid Entrega entrega) {
		entrega = entregaService.salvarEntrega(entrega);
		HttpHeaders headers = new HttpHeaders();
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(entrega.getId())
				.toUri();
		headers.setLocation(location);
		ServiceMessage message = new ServiceMessage(messages.get(SALVO_COM_SUCESSO));
		
		entrega.setDataCriacao(LocalDateTime.now());
		entrega.setStatusEntrega(StatusEntrega.CRIADA);
		
		return new ResponseEntity<>(new ServiceResponse<>(entrega, message), headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Altera uma entrega", response = Entrega.class)
	public ResponseEntity<ServiceResponse<Entrega>> updateEntrega(@PathVariable Integer id,
			@RequestBody @Valid Entrega entrega) {
		
		ServiceMessage message = new ServiceMessage(messages.get(SALVO_COM_SUCESSO));
		return new ResponseEntity<>(new ServiceResponse<>(entregaService.alterarEntrega(entrega), message), HttpStatus.OK);

	}
	
	@PutMapping("/atualizaStatus/{id}")
	@ApiOperation(value = "Altera uma entrega", response = Entrega.class)
	public ResponseEntity<ServiceResponse<Entrega>> updateStatusEntrega(@PathVariable Integer id,
			@RequestBody @Valid StatusEntrega status) {
		
		Entrega entrega = entregaService.getEntrega(id);
		entrega.setDataAtualizacao(LocalDateTime.now());
		entrega.setStatusEntrega(status);
		
		ServiceMessage message = new ServiceMessage(messages.get(SALVO_COM_SUCESSO));
		return new ResponseEntity<>(new ServiceResponse<>(entregaService.alterarEntrega(entrega), message), HttpStatus.OK);

	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Ativa/Desativa uma entrega", notes = "Um ID válido deve ser informado", response = Entrega.class)
	public ResponseEntity<ServiceResponse<Void>> desativaCargo(@PathVariable Integer id) {
		entregaService.excluiEntrega(id);
		ServiceMessage message = new ServiceMessage(messages.get(EXCLUIDO));
		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}
}
