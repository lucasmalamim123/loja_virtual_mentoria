package jdev.mentoria.lojaVirtual.controller;

import jdev.mentoria.lojaVirtual.models.Acesso;
import jdev.mentoria.lojaVirtual.repository.AcessoRepository;
import jdev.mentoria.lojaVirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* deletando por id */
    @ResponseBody /* retorno da api */
    @DeleteMapping(value = "**/deleteAcessoPorId/{id}") /* mapenado url para receber um JSON */
    public ResponseEntity<?> deleteAcessoPorId(@PathVariable("id") Long id){ /* recebe o JSON e converte para objeto */
        acessoRepository.deleteById(id);
        return new ResponseEntity<>("ACESSO REMOVIDO PELO ID COM SUCESSO", HttpStatus.OK);
    }

    @ResponseBody /* retorno da api */
    @GetMapping(value = "**/obterAcesso/{id}") /* mapenado url para receber um JSON */
    public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id){ /* recebe o JSON e converte para objeto */
        Acesso acesso = acessoRepository.findById(id).get();
        return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
    }

    @ResponseBody /* retorno da api */
    @GetMapping(value = "**/buscarPorDesc/{desc}") /* mapenado url para receber um JSON */
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc){ /* recebe o JSON e converte para objeto */
        List<Acesso> acesso = acessoRepository.buscarAcessoDescricao(desc);
        return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
    }
}
