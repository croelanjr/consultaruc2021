package com.grupograndez.bean;

import com.grupograndez.dao.DataSunatDao;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Consulta_Ruc {

    private final String url1 = "https://e-consultaruc.sunat.gob.pe/cl-ti-itmrconsruc/jcrS00Alias";
    private final String archivo = System.getProperty("user.home") + "/certificado/datos.txt"; //Para Linux o MAC en el user
    //private final String archivo = "/home/tomcat/certificado/datos.txt"; // Para Linux en el root
    private String ructxt;
    private DataSunatDao datasunat;
    private List<DataSunatDao> dsd;

    public String getRuctxt() {
        return ructxt;
    }

    public void setRuctxt(String ructxt) {
        this.ructxt = ructxt;
    }

    public DataSunatDao getDatasunat() {
        return datasunat;
    }

    public void setDatasunat(DataSunatDao datasunat) {
        this.datasunat = datasunat;
    }

    public List<DataSunatDao> getDsd() {
        return dsd;
    }

    public void setDsd(List<DataSunatDao> dsd) {
        this.dsd = dsd;
    }

    public void DownloadInformationTaxId() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Utilitarios\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.get(url1);
            WebElement txtRuc = driver.findElement(By.id("txtRuc"));
            txtRuc.sendKeys(this.getRuctxt());
            WebElement idAceptar = driver.findElement(By.id("btnAceptar"));
            idAceptar.click();
            wait.pollingEvery(Duration.ofMillis(1500));
            WebElement elemento = wait.until(presenceOfElementLocated(By.className("list-group")));
            FileWriter datos = new FileWriter(archivo);
            datos.write(elemento.getText());
            datos.close();
            System.out.println("La informacion ha sido guardado en un archivo datos.txt");
            driver.quit();
    }
    
    public void datosDao() throws IOException{
        DataSunatDao ds = new DataSunatDao();
        this.dsd = new ArrayList<>();
        Integer cadena = Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(1).length();
        ds.setRuc_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(1).substring(0, 11));
        ds.setNombre_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(1).substring(14, cadena));
        ds.setTipo_contribuyente_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(3));
        ds.setNombre_comercial_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(5));
        ds.setFecha_inscripcion_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(7));
        ds.setFecha_inicio_actividades_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(9));
        ds.setEstado_contribuyente_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(11));
        ds.setCondicion_contribuyente_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(13));
        ds.setDireccion_fiscal_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(15));
        ds.setSistema_emision_comprobante_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(17));
        ds.setActividades_comercio_exterior_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(19));
        ds.setSistema_contabilidad_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(21));
        ds.setActividades_economica_cliente(Files.readAllLines(Paths.get(archivo), Charset.defaultCharset()).get(23));
        this.dsd.add(ds);
    }

    public void LecturaSunat() {
        for (DataSunatDao informacion : this.dsd) {
            System.out.println("**************************************************************************");
            System.out.println("RUC : " + informacion.getRuc_cliente());
            System.out.println("EMPRESA : " + informacion.getNombre_cliente());
            System.out.println("DIRECCION COMPLETO : " + informacion.getDireccion_fiscal_cliente());
            System.out.println("ESTADO DE CONTRIBUYENTE : " + informacion.getEstado_contribuyente_cliente());
            System.out.println("CONDICION DE CONTRIBUYENTE : " + informacion.getCondicion_contribuyente_cliente());
            System.out.println("FECHA DE INSCRIPCION : " + informacion.getFecha_inscripcion_cliente());
            System.out.println("FECHA DE INICIO DE ACTIVIDADES : " + informacion.getFecha_inicio_actividades_cliente());
            System.out.println("SISTEMA DE EMISION DE COMPROBANTE : " + informacion.getSistema_emision_comprobante_cliente());
            System.out.println("ACTIVIDAD DE COMERCIO EXTERIOR : " + informacion.getActividades_comercio_exterior_cliente());
            System.out.println("SISTEMA DE CONTABILIDAD : " + informacion.getSistema_contabilidad_cliente());
            System.out.println("ACTIVIDAD ECONOMICA : " + informacion.getActividades_economica_cliente());
            System.out.println("************************************************************************");
        }
    }
}
