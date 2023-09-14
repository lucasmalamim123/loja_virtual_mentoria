package jdev.mentoria.lojaVirtual.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/* CRIAR AUTENTICAÇÃO E RETORNAR A AUTENTICAÇÃO JWT */
@Service
@Component
public class JWTTokenAutenticacaoService {

    private static final long EXPIRATION_TIME = 95999000;

    private static final String SECRET = "senha-secreta";

    private static final String TOKEN_PREFIXO = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /* gerar o token e dar a resposta ao cliente */
    public void addAuthentication(HttpServletResponse response, String userName) throws Exception{

        /* montagem do token */

        String JWT = Jwts.builder(). /* chama o gerador de token */
                setSubject(userName) /* add user */
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* tempo de expiração */
                .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /* chave compactada e criptografada */

        String token = TOKEN_PREFIXO + " " + JWT;

        /* da a resposta para a tela e para o cliente, outra API, Navegador, Aplicativo etc ... */
        response.addHeader(HEADER_STRING, token);

        /* usado para ver no postman para teste */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }
}
