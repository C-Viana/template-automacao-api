package common;

import java.time.Duration;

import io.restassured.http.ContentType;

public interface StaticResources {

    /**
     * DETERMINA O TIPO DE EXECUÇÃO DOS TESTES.
     * SE FOR FALSE, OS TESTES SERÃO EXECUTADOS DE MANEIRA INDEPENDENTE E CADA UM
     * GERARÁ UMA ÚNICA EVIDÊNCIA COMO RESULTADO.
     * SE TRUE, OS TESTES SERÃO EXECUTADOS COMO SUITE E SERÁ GERADA UMA ÚNICA
     * EVIDÊNCIA PARA TODOS OS TESTES.
     */
    public static boolean suite_test = false;

    /**
     * TEMPO PADRÃO DE ESPERA PARA AS FUNÇÕES WAIT IMPLEMENTADAS NESTE PROJETO.
     */
    final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(5);

    /**
     * DOMÍNIO DO ENDEREÇO PARA TESTES COM O SERVIÇO [barrigarest].
     */
    final String BARRIGAREST_BASE_URL = "http://barrigarest.wcaquino.me";
    final int BARRIGAREST_PORT = (BARRIGAREST_BASE_URL.startsWith("https")) ? 443 : 80;
    final String BARRIGAREST_BASE_PATH = "";
    final ContentType BARRIGAREST_CONTENT_TYPE = ContentType.JSON;
    
    /**
     * LOCAL DE DESTINO PARA TESTES DE DOWNLOADS DE ARQUIVOS.
     */
    public static final String DOWNLOADS_DIR = System.getProperty("user.dir") + "\\downloads";

}
