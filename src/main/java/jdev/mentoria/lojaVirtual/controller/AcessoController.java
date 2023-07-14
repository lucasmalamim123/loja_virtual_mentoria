package jdev.mentoria.lojaVirtual.controller;

import jdev.mentoria.lojaVirtual.models.Acesso;
import jdev.mentoria.lojaVirtual.repository.AcessoRepository;
import jdev.mentoria.lojaVirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody /* retorno da api */
    @PostMapping(value = "**/salvarAcesso") /* mapenado url para receber um JSON */
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso){ /* recebe o JSON e converte para objeto */
        Acesso acessoSalvo = acessoService.save(acesso);
        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody /* retorno da api */
    @PostMapping(value = "**/deleteAcesso") /* mapenado url para receber um JSON */
    public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso){ /* recebe o JSON e converte para objeto */
        acessoRepository.deleteById(acesso.getId());
        return new ResponseEntity<>("ACESSO REMOVIDO COM SUCESSO", HttpStatus.OK);
    }
}
