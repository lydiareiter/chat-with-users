package at.htl;

import at.htl.control.UserRepo;
import at.htl.enity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/start-websocket/{username}/{password}")
@ApplicationScoped
public class StartWebSocket {

    @Inject
    UserRepo userRepo;

    boolean login = false;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("password") String password) {
        userRepo.findAll().stream().filter(user -> user.getUsername() == username).forEach(user -> {
            if (user.getPassword() == password) {
                System.out.println("onOpen> " + username);
                login = true;
                return;
            }
            System.out.println("[error]: Falsches Password");
            login = false;
        });
        userRepo.persist(new User(username, password));
        System.out.println("neuer User angelegt");
        login = true;
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        System.out.println("onClose> " + name);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        System.out.println("onError> " + name + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        if (login){
            System.out.println("onMessage> " + name + ": " + message);
        }
    }
}
