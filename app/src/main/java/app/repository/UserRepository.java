package app.repository;

import app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String > {
    User findByUserId(String id);

    User findByUserTelephone(String id);
}
