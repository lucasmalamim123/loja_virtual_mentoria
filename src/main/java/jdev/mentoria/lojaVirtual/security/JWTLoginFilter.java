package jdev.mentoria.lojaVirtual.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojaVirtual.models.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    /* configurando o gerenciador de autenticacao */
    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        /* obriga a autenticar Url */
        super(new AntPathRequestMatcher(url));

        /* gerenciador de autenticacao */
        setAuthenticationManager(authenticationManager);
    }

    /* retorna usuario ao processar autenticação */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

        /* retorna o user com login e senha */
        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        try {
            new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
