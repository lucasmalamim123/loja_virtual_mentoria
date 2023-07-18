package jdev.mentoria.lojaVirtual;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojaVirtual.controller.AcessoController;
import jdev.mentoria.lojaVirtual.models.Acesso;
import jdev.mentoria.lojaVirtual.repository.AcessoRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Locale;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
class LojaVirtualMentoriaApplicationTests extends TestCase {
	@Autowired
	public AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private WebApplicationContext wac;

	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_COMPRADOR");

		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/salvarAcesso")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());
		/* converter o retorno da api para um objeto de acesso */

		Acesso obejetoRetorno = objectMapper.
				readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		Assertions.assertEquals(acesso.getDescricao(), obejetoRetorno.getDescricao());
	}



	@Test
	public void testCadastraAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("lucas");

		Assertions.assertNull(acesso.getId());

		/* gravou no banco de dados */
		acesso = acessoController.salvarAcesso(acesso).getBody();

		assert acesso != null;
		Assertions.assertTrue(acesso.getId() > 0);

		Assertions.assertEquals("lucas", acesso.getDescricao());

		/* teste de carregamento */

		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();

		Assertions.assertEquals(acesso.getId(), acesso2.getId());

		/* teste de delete */

		acessoRepository.deleteById(acesso2.getId());

		acessoRepository.flush(); /* rodas sql de delete no banco de dados */

		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);

		Assertions.assertEquals(true, acesso3 == null);

		/* teste de query */

		acesso = new Acesso();

		acesso.setDescricao("ROLE_ALUNO");

		acesso = acessoController.salvarAcesso(acesso).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDescricao("ALUNO".trim().toUpperCase());

		assert acesso != null;
		acessoRepository.deleteById(acesso.getId());
	}

}
