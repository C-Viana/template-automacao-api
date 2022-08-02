package reporter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import common.BasePage;
import common.StaticResources;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;


public class ReportManager {
    
	private static String feature;
	private static Scenario scenario;
    private static ExtentReports reporter;
    private static ExtentTest test;
    private static String ROOT_PATH = "";
    private static String files_path = "";

	/**
	 * Método para recuperar o nome da feature a partir do arquivo.
     * <br>
     * Para que este método funcione é necessário que os arquivos .feature estejam dentro de uma pasta com o nome "features".
     * <br>
     * Utilize como parâmetro a o método io.cucumber.java.Scenario.getUri().toString().
     * <br>
     * @param scenarioClass
	 */
	public static void setFeature(String scenarioClass) {
		feature = scenarioClass.split("features/")[1].split("[.]")[0];
	}
    
	/**
	 * Retorna o nome da feature extraída do arquivo com a escrita Gherkin que
	 * estiver em execução. <br>
	 * 
	 * @return
	 */
	public static String getFeature() {
		if(feature.isBlank() || feature.isEmpty())
			setFeature(scenario.getUri().toString());
		return feature;
	}

	/**
	 * Retorna o objeto de cenário de teste em execução extraído do Cucumber. <br>
	 * 
	 * @return
	 */
	public static Scenario getScenario() {
		return scenario;
	}

	/**
	 * Retorna o resultado da execução do teste atual. <br>
	 * 
	 * @return
	 */
	public static Status getScenarioStatus() {
		return scenario.getStatus();
	}

	/**
	 * Armazena o objeto de cenário de teste em execução para utilização interna
	 * deste framework. 
	 * <br>
	 * Este método deve ser utilizada preferencialmente antes do
	 * teste iniciar, na classe _Hooks e nos métodos com a tag @Before. <br>
	 * 
	 * @param cucumberScenario
	 */
	public static void setScenario(Scenario cucumberScenario) {
		scenario = cucumberScenario;
	}
	
	/**
     * Define o caminho para armazenamento dos arquivos de reporte. 
     * <br>
     * A depender do tipo de execução definida, os diretórios criados para armazenamento será diferente. 
     * <br>
     * Para testes individuais o caminho será C:/Test_Results/[DATA_dd-MM-yyyy]/[CucumberFileFeatureName]/[CucumberScenarioName]
     * <br>
     * Para suite de testes será C:/Test_Results/[DATA_dd-MM-yyyy]/[NomeDoProjeto]_Suite_Run_[HORA_HH-mm]
     * <br>
     * @param featureName
     * @param scenarioName
     */
    public static void setResultPath(String featureName, String scenarioName) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format( Date.from(Instant.now()) );
        if(!StaticResources.suite_test) {
            ROOT_PATH = "C:/Test_Results/" + date + "/" + getFeature() + "/" + scenarioName.replaceAll("[\\/?\\*:<>]", "");
        }
        else
            if(reporter == null) {
            	String[] names = System.getProperty("user.dir").replace("\\", "_").split("_");
                ROOT_PATH = "C:/Test_Results/" + date + "/" + names[names.length-1] + "_Suite_Run_" + new SimpleDateFormat("HH-mm").format(Calendar.getInstance().getTime());
            }
    }
    
    /**
     * Iniciará a instância de reporte para o(s) teste(s) em execução.
     * <br>
     * Este método deve ser chamado logo após o método ReportManager.setResultPath().3
     * <br>
     */
    public static void startReport() {
        if( reporter != null ) return;
        reporter = new ExtentReports( ROOT_PATH + "/results.html", true );
    }
    
    /**
     * Cria uma instância de teste para compor o arquivo de reporte. 
     * <br>
     * Deve ser chamado logo após o método ReportManager.startReport().
     * <br>
     * @param testName
     */
    public static void startTest( String testName ) {
        test = reporter.startTest(testName);
        files_path = ROOT_PATH + "/responses";
        test.setStartedTime(Date.from(Instant.now()));
    }
    
    /**
     * Finaliza uma instância de teste para compor o arquivo de reporte.
     * <br>
     * Deve ser chamado após o último passo de execução do teste atual.
     * <br>
     */
    public static void endTest() {
        test.setEndedTime(Date.from(Instant.now()));
        reporter.endTest(test);
        test = null;
    }
    
    /**
     * Finaliza o reporte com os resultados do(s) teste(s) executados.
     * <br>
     * Deve ser chamado após a finalização de todos os testes e após o método ReportManager.endTest().
     * <br>
     */
    public static void endReport() {
        reporter.flush();
        reporter.close();
        reporter = null;
    }

    /**
     * Realiza um registro de execução de teste ao arquivo de reporte com o status atual do passo em execução informado através do recurso io.cucumber.java.Scenario.
     * <br>
     * Como parâmetro, pode ser informado um texto personalizado que sirva de explicação às validações realizadas.
     * <br>
     * Este método pode ser utilizado em qualquer ponto da execução do teste que se queira registrar uma evidência de teste.
     * <br>
     * @param stepLog
     */
    public static void setTestStep( String stepLog ) {
        LogStatus stat = ( 
        		LogStatus.valueOf(getScenario().getStatus().name().replace("ED", "")) != null ) 
        		? LogStatus.valueOf(getScenario().getStatus().name().replace("ED", "")) 
        				: LogStatus.valueOf("error");
        test.log( stat, stepLog );
    }
    
    /**
     * Realiza um registro de execução de teste ao arquivo de reporte com objetivo unicamente informativo. 
     * <br>
     * O io.cucumber.java.Scenario neste método será sempre LogStatus.INFO e não deve ser utilizado com caráter validativo.
     * <br>
     * Como parâmetro, pode ser informado um texto personalizado que sirva de explicação à imagem e às validações realizadas.
     * <br>
     * @param stepLog
     */
    public static void setTestInfo(String stepLog) {
        test.log( LogStatus.INFO, stepLog );
    }
    
    /**
     * Método que implementa a geração de um arquivo (xml, json, etc.) como evidência de teste.
     * <br>
     * @param fileContent
     * @param fileName
     * @param fileExtension
     * @return
     */
    @SuppressWarnings("unused")
	private static String getFile(String fileContent, String fileName, String fileExtension) {
        byte[] fileContentBytes = fileContent.getBytes();
        File file = new File( files_path + "/" + fileName + "." + fileExtension );
        BasePage.createFolders(files_path);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContentBytes);
        }
        catch(Exception e) {
            Logger.getLogger(ReportManager.class.getName()).log(Level.WARNING, "Erro ao gerar o arquivo de evidência de teste", e);
        }
        return file.getPath();
    }
    
}
