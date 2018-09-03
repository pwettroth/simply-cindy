package com.simplycindy.models.data;

import com.simplycindy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    default User findByEmail(String email){
        Iterable<User> users = findAll();

        User actualUser = null;
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                actualUser = user;
                break;
            }
        }
        return actualUser;
    }
}
