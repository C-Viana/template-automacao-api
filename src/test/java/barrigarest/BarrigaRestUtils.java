package barrigarest;

import static io.restassured.RestAssured.given;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.FilterableRequestSpecification;

public class BarrigaRestUtils {
    
    public static String getTimeSuffix() {
        return DateTimeFormatter.ofPattern("ddMMyyy-HHmmss").format(LocalDateTime.now(ZoneId.of("GMT-3")));
    }

    public static String getDateAsString(Integer daysDif, Integer monthsDif, Integer yearsDif) {
        LocalDate date = LocalDate.now();

        if (daysDif != null)
            date = date.plusDays(daysDif);
        if (monthsDif != null)
            date = date.plusMonths(monthsDif);
        if (yearsDif != null)
            date = date.plusYears(yearsDif);

        return DateTimeFormatter.ofPattern("dd/MM/YYYY").format(date);
    }
    
    public static void deleteHeader(String headerName) {
    	FilterableRequestSpecification filter = (FilterableRequestSpecification)RestAssured.requestSpecification;
		filter.removeHeader("Authorization");
    }
    
    public static void setAuthorizationTokenStatic() {
    	RestAssured.requestSpecification.header("Authorization", "JWT " + generateToken());
    }

    public static String generateToken() {
        Map<String, String> userData = new HashMap<String, String>();
        userData.put("email", "cv.test@test.com.br");
        userData.put("senha", "123456");

        return given()
                .body(userData)
                .when()
                .post("/signin")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

    public static List<Conta> getAccounts() {
        JsonPath json = given()
                .when()
                .get("/contas")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        return json.getList("$", Conta.class);
    }

    public static Conta getAccountByName(String nomeDaConta) {
        JsonPath json = given()
                .when()
                .get("/contas")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        return json.getObject("find {it.nome=\"" + nomeDaConta + "\"}", Conta.class);
    }

    public static List<Movimentacao> getTransactions() {
        JsonPath json = given()
                .when()
                .get("/transacoes")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        return json.getList("$", Movimentacao.class);
    }

    public static Movimentacao getTransaction(String descricaoDaMovimentacao) {
        JsonPath json = given()
                .when()
                .get("/transacoes")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        return json.getObject("find {it.descricao=\"" + descricaoDaMovimentacao + "\"}", Movimentacao.class);
    }

    public static Conta createAccount() {
        JsonPath json = null;
        List<Conta> contas = getAccounts();

        if (contas.size() == 0) {
            json = given()
                    .body("{\"nome\" : \"Conta de Teste " + getTimeSuffix() + "\"}")
                    .when()
                    .post("/contas")
                    .then()
                    .statusCode(201)
                    .extract().jsonPath();
            return json.getObject("$", Conta.class);
        }
        return contas.get(0);
    }

    public static void deleteAccount(Long accountId) {
        given()
                .when()
                .delete("/contas/" + accountId)
                .then()
                .statusCode(204);
    }

    public static Movimentacao createTransaction(Conta conta) {
        JsonPath json = null;

        Movimentacao movimentacao = new Movimentacao(
                "Pagamento de Teste",
                "Seu Barriga",
                "DESP",
                getDateAsString(-20, null, null),
                getDateAsString(-8, null, null),
                300.00f,
                true,
                conta.getId());

        List<Movimentacao> movs = getTransactions();

        if (movs.size() == 0 || !movs.stream().anyMatch(e -> e.getConta_id() == conta.getId())) {
            json = given()
                    .body(movimentacao)
                    .when()
                    .post("/transacoes")
                    .then()
                    .statusCode(201)
                    .extract().jsonPath();
            return json.getObject("$", Movimentacao.class);
        }
        return movs.stream().filter(e -> e.getConta_id() == conta.getId()).findFirst().get();
    }

    public static void deleteTransaction(long transactionId) {
        given()
                .when()
                .delete("/transacoes/" + transactionId)
                .then()
                .statusCode(204);
    }

}
