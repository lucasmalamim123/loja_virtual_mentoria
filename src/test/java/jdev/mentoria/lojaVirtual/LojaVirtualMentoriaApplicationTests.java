package jdev.mentoria.lojaVirtual;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdev.mentoria.lojaVirtual.controller.AcessoController;
import jdev.mentoria.lojaVirtual.models.Acesso;
import jdev.mentoria.lojaVirtual.repository.AcessoRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
class LojaVirtualMentoriaApplicationTests extends TestCase {
	@Autowired
	public AcessoController acessoController;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private WebApplicationContext wac;

	/* teste end-point de salvar (mock) */
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
	public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELETE");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.post("/deleteAcesso")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());

		Assertions.assertEquals("ACESSO REMOVIDO COM SUCESSO", retornoApi.andReturn().getResponse().getContentAsString());
		Assertions.assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}

	@Test
	public void testRestApiDeletePorId() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELETE");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da API: " + retornoApi.andReturn().getResponse().getContentAsString());

		Assertions.assertEquals("ACESSO REMOVIDO PELO ID COM SUCESSO", retornoApi.andReturn().getResponse().getContentAsString());
		Assertions.assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	}

	@Test
	public void testRestApiObterAcessoId() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_OBTER_ID");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		Assertions.assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

		Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);

		Assertions.assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());

		Assertions.assertEquals(acesso.getId(), acessoRetorno.getId());
	}

	@Test
	public void testRestApiObterAcessoDescricao() throws JsonProcessingException, Exception {

		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_OBTER_POR_DESCRICAO");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();


		ResultActions retornoApi = mockMvc
				.perform(MockMvcRequestBuilders.get("/buscarPorDesc/POR_DESCRICAO")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		Assertions.assertEquals(200, retornoApi.andReturn().getResponse().getStatus());

		List<Acesso> retornoApiList = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {}
		);

		Assertions.assertEquals(1, retornoApiList.size());

		Assertions.assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
		acessoRepository.delete(acesso);
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
