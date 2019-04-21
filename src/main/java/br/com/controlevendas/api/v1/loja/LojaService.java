package br.com.controlevendas.api.v1.loja;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.services.MessagesService;

@Service
@Transactional
public class LojaService {
	
	public static final String NAO_ENCONTRADO = "nao.encontrado";
	
	@Autowired
	LojaDao lojaDao;
	
	@Autowired
	MessagesService messagesService;
	
	public Page<Loja> getLojas(Pageable pageable){
		return lojaDao.findAll(pageable);
	}
	
	public Loja getLoja(Integer id) {
		Optional<Loja> loja = lojaDao.findById(id);
		
		if(!loja.isPresent()) {
			throw new RuntimeException(messagesService.get(NAO_ENCONTRADO));
		}
		
		return loja.get();
	}
	
	public Loja salvarLoja(Loja loja) {
		return lojaDao.save(loja);
	}
	
	public Loja alterarLoja(Loja loja) {
		Optional<Loja> l = lojaDao.findById(loja.getId());

		if(!l.isPresent()) {
			throw new RuntimeException(messagesService.get(NAO_ENCONTRADO));
		}
		
		return salvarLoja(loja);
	}
	
	public void excluiLoja(Integer id) {
		Optional<Loja> loja = lojaDao.findById(id);

		if(!loja.isPresent()) {
			throw new RuntimeException(messagesService.get(NAO_ENCONTRADO));
		}
		
		lojaDao.delete(loja.get());
	}

}
