package br.com.marketing.propaganda;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.controlevendas.api.v1.cliente.Cliente;
import br.com.controlevendas.api.v1.loja.Loja;
import br.com.controlevendas.api.v1.produto.Produto;

@Entity
@Table(name = "propaganda")
public class Propaganda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Cliente cliente;
	
	@ManyToOne
	private Loja loja;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataAtualizacao;

	@ManyToOne
	private Produto produto;

	private Boolean ativo;
	
	private StatusPropaganda statusPropaganda;
	
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public StatusPropaganda getStatusPropaganda() {
		return statusPropaganda;
	}

	public void setStatusPropaganda(StatusPropaganda statusPropaganda) {
		this.statusPropaganda = statusPropaganda;
	}

}


enum StatusPropaganda {
	CRIADA, CANCELADA, VALIDA, APOSENTADA;
}
