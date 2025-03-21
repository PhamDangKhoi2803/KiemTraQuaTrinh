package vn.iotstar.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.backend.entity.User;

//Pham Ngoc Hoa 22110330
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    User findUsersByEmail(String email);

    User findUsersByUsername(String username);
}
