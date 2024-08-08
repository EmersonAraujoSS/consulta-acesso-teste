package br.com.avancard.application;

import br.com.avancard.enttity.Fila;
import br.com.avancard.repository.FilaRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class checkSpot {
    private FilaRepository filaRepository;

    public checkSpot(FilaRepository filaRepository) {
        this.filaRepository = filaRepository;
    }


    public void processarFila() throws InterruptedException, SQLException {
        List<Fila> filaList = filaRepository.findByStatus("PENDENTE");

        if (filaList.isEmpty()) {
            return;
        }

        // Fechar todas as instâncias do Edge antes de iniciar a aplicação
        try {
            Runtime.getRuntime().exec("taskkill /F /IM msedge.exe");
            TimeUnit.SECONDS.sleep(5); // Esperar um tempo para garantir que todos os processos sejam fechados
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("webdriver.edge.driver", "C:\\edge\\msedgedriver.exe");

        WebDriver driver = null;
        try {
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.get("https://govam.consiglog.com.br/Login.aspx");

            WebElement firstField = driver.findElement(By.id("txtLogin"));
            firstField.sendKeys("45603065249");

            WebElement loginButton1 = driver.findElement(By.id("Entrar"));
            loginButton1.click();

            WebElement secondField = driver.findElement(By.id("txtSenha"));
            secondField.sendKeys("MN6unB4R@18");

            WebElement loginButton2 = driver.findElement(By.id("Entrar"));
            loginButton2.click();

            TimeUnit.SECONDS.sleep(3);

            WebElement convenioButton = driver.findElement(By.id("gvOrgao_imgEntrar_0"));
            convenioButton.click();

            for (Fila item : filaList) {
                processarConsulta(driver, item);
            }

            // Realiza o logout
            WebElement logoutButton = driver.findElement(By.id("btnDesconectar"));
            logoutButton.click();

            WebElement confirmLogout = driver.findElement(By.id("ucModalPopupDesconectar1_btnOKPopup"));
            confirmLogout.click();

            TimeUnit.SECONDS.sleep(5);

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private void processarConsulta(WebDriver driver, Fila item) throws InterruptedException, SQLException {
        try {
            driver.get("https://govam.consiglog.com.br/Margem/ConsultaMargem.aspx");

            WebElement cpfField = driver.findElement(By.id("body_cpfTextBox"));
            cpfField.sendKeys(item.getCpf());

            WebElement matriculaField = driver.findElement(By.id("body_matriculaTextBox"));
            matriculaField.sendKeys(item.getMatricula());

            WebElement psqButton = driver.findElement(By.id("body_pesquisarButton"));
            psqButton.click();


            // Verificar se o CPF/Matrícula não foi encontrado
            if (driver.getPageSource().contains("CPF/Matrícula não encontrado")) {
                WebElement okButton = driver.findElement(By.id("body_ucAjaxModalPopup1_btnConfirmarPopup"));
                okButton.click();

                // Atualiza o status do item para "NAO LOCALIZADO"
                item.setStatus("NAO LOCALIZADO");
                filaRepository.save(item);

                TimeUnit.SECONDS.sleep(3); // Aguarda antes de continuar
            } else {

                //Processar a consulta e extrair os dados necessários
                String mst = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[2]")).getText().trim();
                String msr = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[3]")).getText().trim();
                String msd = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[4]")).getText().trim();
                String mct = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[2]")).getText().trim();
                String mcr = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[3]")).getText().trim();
                String mcd = driver.findElement(By.xpath("//tr[@id='body_rptMargens_headerservico_2']/td[4]")).getText().trim();
                String situacaoTextBox = driver.findElement(By.id("body_txtSituacao")).getAttribute("value").trim();

                // Atualiza os dados no objeto Fila
                item.setMst(mst);
                item.setMsr(msr);
                item.setMsd(msd);
                item.setMct(mct);
                item.setMcr(mcr);
                item.setMcd(mcd);
                item.setSituacaoTextBox(situacaoTextBox);

                item.setStatus("PROCESSADO");
                filaRepository.save(item);

                TimeUnit.SECONDS.sleep(2);
            }

        } catch (Exception e) {
            // Em caso de qualquer exceção, atualiza o status do item para "ERRO"
            item.setStatus("ERRO");
            filaRepository.save(item);
            TimeUnit.SECONDS.sleep(5);
        }
    }
}
