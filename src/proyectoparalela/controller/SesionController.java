package proyectoparalela.controller;

import proyectoparalela.dao.SesionDAO;
import proyectoparalela.threads.SesionThread;
import proyectoparalela.utils.Observable;

public class SesionController {
    private final SesionDAO dao;
    private final SesionThread sesionThread;
    private final Observable<String> notifier;

    public SesionController(SesionDAO dao, SesionThread sesionThread, Observable<String> notifier) {
        this.dao = dao;
        this.sesionThread = sesionThread;
        this.notifier = notifier;
    }

    public void login(String usuario, String token) {
        sesionThread.login(usuario, token);
        notifier.publish("Login de usuario: " + usuario);
    }

    public void touch(String usuario) { sesionThread.touch(usuario); }
}












