package common;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;


public class BasePage {
	
	private BasePage() {
		throw new IllegalStateException("Erro para iniciar a classe General.");
	}
	
	
	/**
	 * Cria os diretórios informados através de um caminho definido em uma string.
	 * Se os diretórios já existirem, nenhuma ação será realizada. <br>
	 * Exemplo: <br>
	 * path = "D:/PDI/template-automacao-web/uploads" <br>
	 * 
	 * @param path
	 */
	public static void createFolders(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
		} catch (Exception e) {
			Logger.getLogger(BasePage.class.getName()).log(Level.WARNING,
					"Erro para utilizar createFolders(String path)", e);
			Thread.currentThread().interrupt();
		}
	}
	
	
	/**
	 * Utiliza a biblioteca Apache Commons para realizar um download de arquivo
	 * diretamente através de uma URL. <br>
	 * O arquivo baixado será armazenado na pasta /downloads, no diretório do
	 * próprio projeto. <br>
	 * Caso esta pasta não exista, ela será criada ao executar esta função. <br>
	 * 
	 * @param url
	 * @param fileName
	 */
	public static void downloadFile(String url, String fileName) {
		try {
			createFolders(StaticResources.DOWNLOADS_DIR);
			FileUtils.copyURLToFile(new URL(url), new File(StaticResources.DOWNLOADS_DIR + "/" + fileName));
		} catch (MalformedURLException e) {
			Logger.getLogger(BasePage.class.getName()).log(Level.WARNING, "Erro para utilizar downloadFile(String url, String fileName)", e);
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			Logger.getLogger(BasePage.class.getName()).log(Level.WARNING, "Erro para utilizar downloadFile(String url, String fileName)", e);
			Thread.currentThread().interrupt();
		}
	}
	
	
	/**
	 * Através de recursos da classe java.net, retorna o status code ao tentar
	 * realizar uma conexão com a URL informada. <br>
	 * É realizado um método GET sobre a URL e o status code é retornado como
	 * inteiro. <br>
	 * Considere utilizar este método para validar o acesso a uma página antes de
	 * abri-la ou para validar a integridade de recursos da página de teste
	 * (imagens, arquivos de som, etc.). <br>
	 * 
	 * @param targetUrl
	 * @return
	 */
	public static int getStatusCodeFromURL(String targetUrl) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
			conn.setRequestMethod("GET");
			return conn.getResponseCode();
		} catch (Exception e) {
			Logger.getLogger(BasePage.class.getName()).log(Level.WARNING, "Erro para utilizar getStatusCodeFromURL(String targetUrl)", e);
			Thread.currentThread().interrupt();
			return 0;
		}
	}
	
	
}
