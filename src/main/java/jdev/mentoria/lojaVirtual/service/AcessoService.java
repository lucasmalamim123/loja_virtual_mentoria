package jdev.mentoria.lojaVirtual.service;

import jdev.mentoria.lojaVirtual.models.Acesso;
import jdev.mentoria.lojaVirtual.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso save(Acesso acesso){
        /*qualquer tipo de validação antes de salvar*/
        return acessoRepository.save(acesso);
    }
}
