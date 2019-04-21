package br.com.controlevendas.api.v1.cliente;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteDao extends JpaRepository<Cliente, Integer>{

}
