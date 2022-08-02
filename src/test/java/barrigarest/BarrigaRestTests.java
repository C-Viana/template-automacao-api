package barrigarest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import io.restassured.path.json.JsonPath;

@DisplayName("Testes de Acesso, Contas e Movimentação da aplicação \"Seu Barriga\"")
public class BarrigaRestTests extends _BarrigaRestHooks {
	
	@Test
	@DisplayName("Não deve acessar sem token")
	public void naoDeveAcessarSemToken() {
		deleteHeader("Authorization");

		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401);
		
		setAuthorizationTokenStatic();
	}

	@Test
	@DisplayName("Deve incluir conta com sucesso")
	public void deveIncluirContaComSucesso() {
		given()
			.body("{\"nome\" : \"Conta de Teste "+ getTimeSuffix() +"\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201);
	}

	@Test
	@DisplayName("Não deve incluir conta com nome repetido")
	public void naoDeveIncluirContaComNomeRepetido() {
		Conta contaCadastrada = createAccount();
		given()
			.body("{\"nome\" : \""+ contaCadastrada.getNome() + "\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
				
	}

	@Test
	@DisplayName("Deve alterar conta com sucesso")
	public void deveAlterarContaComSucesso() {
		Conta contaCadastrada = createAccount();
		given()
			.body("{\"nome\" : \"Conta Alterada por API " + getTimeSuffix() + "\"}")
		.when()
			.put("/contas/" + contaCadastrada.getId())
		.then()
			.statusCode(200);
	}

	@Test
	@DisplayName("Deve inserir movimentação com sucesso")
	public void deveInserirMovimentacaoComSucesso() {
		Conta contaCadastrada = createAccount();
		Movimentacao movimentacao = new Movimentacao(
				"Pagamento do aluguel",
				"Seu Barriga",
				"DESP",
				getDateAsString(-10, null, null),
				getDateAsString(-2, null, null),
				1200.00f,
				true,
				contaCadastrada.getId());

		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201);
	}

	@Test
	@DisplayName("Deve validar campos obrigatórios na movimentação")
	public void deveValidarCamposObrigatoriosNaMovimentacao() {
		JsonPath json = 
		given()
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.extract().jsonPath()
			;
			
		MatcherAssert.assertThat(json.getList("$"), hasSize(8));
		MatcherAssert.assertThat(json.getList("msg", String.class), hasItems(
				"Data da Movimentação é obrigatório",
				"Data do pagamento é obrigatório",
				"Descrição é obrigatório",
				"Interessado é obrigatório",
				"Valor é obrigatório",
				"Valor deve ser um número",
				"Conta é obrigatório",
				"Situação é obrigatório"));
	}

	@Test
	@DisplayName("Não deve cadastrar movimentação futura")
	public void naoDeveCadastrarMovimentacaoFutura() {
		Conta contaCadastrada = createAccount();
		Movimentacao movimentacao = new Movimentacao(
				"Pagamento programado para acabamento de cômodo",
				"Azulejista",
				"DESP",
				getDateAsString(5, null, null),
				"04/07/2022",
				3500.00f,
				true,
				contaCadastrada.getId());

		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"));
	}

	@Test
	@DisplayName("Não deve remover conta com movimentação")
	public void naoDeveRemoverContaComMovimentacao() {
		Conta contaCadastrada = createAccount();
		Movimentacao movCadastrada = createTransaction(contaCadastrada);

		given()
		.when()
			.delete("/contas/" + contaCadastrada.getId())
		.then()
			.statusCode(500)
			.body("name", is("error"))
			.body("detail", endsWith("is still referenced from table \"transacoes\"."))
			;

		deleteTransaction(movCadastrada.getId());
	}

	@Test
	@DisplayName("Deve calcular saldo das contas")
	public void deveCalcularSaldoDasContas() {
		JsonPath json = 
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.extract().jsonPath();

		List<Double> lista = json.getList("saldo", Double.class);
		double total = 0.0;
		for (Double double1 : lista) {
			total += double1;
		}
		MatcherAssert.assertThat(total, is((lista.size()==0)?0.0:not(0.0)));
	}

	@Test
	@DisplayName("Deve remover movimentação")
	public void deveRemoverMovimentacao() {
		Movimentacao mov = createTransaction(createAccount());
		given()
		.when()
			.delete("/transacoes/" + mov.getId())
		.then()
			.statusCode(204)
			;
	}

}
