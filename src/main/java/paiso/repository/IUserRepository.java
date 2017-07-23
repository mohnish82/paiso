package paiso.repository;

import paiso.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<User, Long> {
    User getByEmail(String email);
    User getByEmailAndPassword(String email, String password);
}
