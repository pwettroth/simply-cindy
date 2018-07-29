package com.simplycindy.models.data;

import com.simplycindy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, String> {

    default User findByUsername(String username){
        Iterable<User> users = findAll();

        User actualUser = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                actualUser = user;
                break;
            }
        }
        return actualUser;
    }
}
