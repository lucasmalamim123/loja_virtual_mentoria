package jdev.mentoria.lojaVirtual.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdev.mentoria.lojaVirtual.ApplicationContextLoad;
import jdev.mentoria.lojaVirtual.models.Usuario;
import jdev.mentoria.lojaVirtual.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

        liberacaoCors(response);

        /* usado para ver no postman para teste */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    /* Retorna o usuario validado com token ou caso nao seja valido retorna null */

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(HEADER_STRING);

        if (token != null) {

            String tokenLimpo = token.replace(TOKEN_PREFIXO, "").trim();

            /* faz a validação do token do usuario na requisicao e obtem o USER */

            String user = Jwts.parser().
                    setSigningKey(SECRET)
                    .parseClaimsJws(tokenLimpo)
                    .getBody().getSubject();

            if (user != null){

                Usuario usuario = ApplicationContextLoad
                        .getApplicationContext().
                        getBean(UsuarioRepository.class).findUserByLogin(user);

                if (usuario != null){
                    return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
                }

            }

        }
        liberacaoCors(response);
        return null;
    }

    /* fazendo liberação contra erro de cors no navegador */
    private void liberacaoCors(HttpServletResponse response){
        if(response.getHeader("Access-Control-Allow-Origin") == null){
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if(response.getHeader("Access-Control-Allow-Headers") == null){
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if(response.getHeader("Access-Control-Request-Headers") == null){
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if(response.getHeader("Access-Control-Allow-Methods") == null){
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
