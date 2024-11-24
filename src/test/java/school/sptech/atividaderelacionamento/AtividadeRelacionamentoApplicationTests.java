package school.sptech.atividaderelacionamento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc // Habilita o MockMvc no contexto do teste
class AtividadeRelacionamentoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void somar_ValidInput_ShouldReturnFour() throws Exception {
		mockMvc.perform(get("/soma")
						.param("numero1", "2")
						.param("numero2", "2"))
				.andExpect(status().isOk()) // Verifica se o status HTTP é 200 (OK)
				.andExpect(content().string("4")); // Verifica se o conteúdo retornado é "4"
	}
}
