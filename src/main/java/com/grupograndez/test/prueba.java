package com.grupograndez.test;

import com.grupograndez.bean.Consulta_Ruc;
import java.awt.*;
import static java.lang.Thread.sleep;

public class prueba {

    public static void main(String[] args) {

        try {
            Consulta_Ruc bajando = new Consulta_Ruc();
            bajando.setRuctxt("20100122368");
            System.out.println("Consultando informacion de la SUNAT...");
            bajando.DownloadInformationTaxId();
            System.out.println("Obteniendo informacion en la Sunat ....");
            bajando.datosDao();
            sleep(400);
            System.out.println("------------------------------------------------");
            bajando.LecturaSunat();

        } catch (HeadlessException ex) {
            System.out.println("Error HeadLessException : " + ex);
        } catch (InterruptedException ex) {
            System.out.println("Error InterruptedException : " + ex);
        } catch (Exception ex) {
            System.out.println("Error Exception : " + ex);
        }
    }
}
