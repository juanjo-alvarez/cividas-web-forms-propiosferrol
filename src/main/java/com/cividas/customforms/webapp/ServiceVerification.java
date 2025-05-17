package com.cividas.customforms.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servicio de verificación de titulares registrados en la CRCC
 */
public class ServiceVerification implements IServiceVerification {
    protected static final Logger log = LoggerFactory.getLogger(ServiceVerification.class);
    private static final String GET = "GET";
    private static final String VERIFICATION_PARAMETER = "?cif=";
    public String urlservicio;

    public ServiceVerification (String urlservicio){
        this.urlservicio = urlservicio;
    }

    public String getUrlservicio() {
        return urlservicio;
    }

    public void setUrlservicio(String urlservicio) {
        this.urlservicio = urlservicio;
    }

    /**
     * Llamada al servicio de verificación de titulares de la CRCC para comprobar si existe el documento de identificación
     * introducido.
     *
     * @param documentoidentificacion documento de identificación a comprobar por el servicio.
     * @return La respuesta de la solicitud al servicio, -1 si ha ocurrido un error.
     * @throws IOException Si ocurre un error de conexión o de lectura/escritura.
     */
    public int verificarTitular(String documentoidentificacion) throws IOException {

        URL address = new URL( urlservicio + VERIFICATION_PARAMETER + documentoidentificacion);
        HttpURLConnection conection = (HttpURLConnection) address.openConnection();
        conection.setRequestMethod(GET);
        int responseCode;
        try {
            responseCode = conection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while((inputLine =in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                if(response.toString().contains("<body>1</body>")) return 1;
                return 0;
            }
        } catch (IOException e){
            log.error("Se ha producido un error en la llamada del servicio verificacionTitular", e);
            return -1;
        }
        return -1;
    }
}
