package jdev.mentoria.lojaVirtual.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/* CRIAR AUTENTICAÇÃO E RETORNAR A AUTENTICAÇÃO JWT */
@Service
@Component
public class JWTTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 95999000;

    private static final String SECRET = "senha-secreta";

    private static final String TOKEN_PREFIXO = "Bearer";

    private static final String HEADER_STRING = "Authorization";
}
