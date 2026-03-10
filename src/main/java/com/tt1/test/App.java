package com.tt1.test;

import java.time.LocalDate;

/** Clase principal que arranca el programa. */
public class App {
    /**
     * Método principal.
     * @param args Argumentos de consola.
     */
    public static void main(String[] args) {
        DBStub db = new DBStub();
        Repositorio repo = new Repositorio(db);
        MailerStub mailer = new MailerStub();
        Servicio servicio = new Servicio(repo, mailer);

        servicio.anadirEmail("profesor@unirioja.es");
        servicio.crearTarea("Entregar Práctica 3", LocalDate.now().plusDays(2));

        System.out.println("Tareas pendientes: " + servicio.consultarPendientes().size());
    }
}