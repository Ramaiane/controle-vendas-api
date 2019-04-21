package br.com.controlevendas.api.v1.produto;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoDao extends JpaRepository<Produto, Integer>{

}
