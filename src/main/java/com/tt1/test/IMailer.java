package com.tt1.test;

/** Interfaz para el envío de correos. */
public interface IMailer {
    /**
     * Envía un correo.
     * @param destinatario Email destino.
     * @param mensaje Cuerpo del texto.
     * @return Éxito del envío.
     */
    boolean enviarCorreo(String destinatario, String mensaje);
}