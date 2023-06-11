package at.htl.control;

import at.htl.enity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepo implements PanacheRepository<User> {
}
