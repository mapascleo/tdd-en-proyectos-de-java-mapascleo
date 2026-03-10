package com.tt1.test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/** Lógica de negocio del sistema de tareas. */
public class Servicio {
    private final IRepositorio repositorio;
    private final IMailer mailer;

    /**
     * Constructor del servicio.
     * @param repositorio Repositorio de datos.
     * @param mailer Servicio de correos.
     */
    public Servicio(IRepositorio repositorio, IMailer mailer) {
        this.repositorio = repositorio;
        this.mailer = mailer;
    }

    /**
     * Crea y guarda una tarea nueva.
     * @param nombre Título de la tarea.
     * @param fechaLimite Vencimiento.
     * @return Objeto creado.
     */
    public ToDo crearTarea(String nombre, LocalDate fechaLimite) {
        ToDo nuevaTarea = new ToDo(nombre, "", fechaLimite);
        repositorio.save(nuevaTarea);
        return nuevaTarea;
    }

    /**
     * Registra un email.
     * @param email El email a registrar.
     */
    public void anadirEmail(String email) {
        repositorio.addEmail(email);
    }

    /**
     * Marca una tarea como terminada.
     * @param idTarea ID de la tarea a marcar.
     */
    public void marcarComoCompletada(int idTarea) {
        ToDo tarea = repositorio.findById(idTarea);
        if (tarea != null) {
            tarea.setCompletada(true);
            repositorio.update(idTarea, tarea);
        }
    }

    /**
     * Busca las tareas sin terminar.
     * @return Lista de tareas pendientes.
     */
    public List<ToDo> consultarPendientes() {
        return repositorio.findAll().stream()
            .filter(t -> !t.isCompletada())
            .collect(Collectors.toList());
    }

    /** Envía correos si hay tareas fuera de plazo. */
    public void comprobarTareasCaducadas() {
        List<ToDo> caducadas = repositorio.findAll().stream()
            .filter(t -> !t.isCompletada() && t.getFechaLimite().isBefore(LocalDate.now()))
            .collect(Collectors.toList());

        if (!caducadas.isEmpty()) {
            List<String> emails = repositorio.getEmails();
            for (String email : emails) {
                mailer.enviarCorreo(email, "Alerta: Tienes " + caducadas.size() + " tareas caducadas.");
            }
        }
    }
}