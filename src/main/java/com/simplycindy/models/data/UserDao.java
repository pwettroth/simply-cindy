package com.simplycindy.models.data;

import com.simplycindy.models.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserDao extends CrudRepository<UserData, Long> {

    default UserData findByEmail(String email){
        Iterable<UserData> users = findAll();

        UserData actualUser = null;
        for (UserData user : users) {
            if (user.getEmail().equals(email)) {
                actualUser = user;
                break;
            }
        }
        return actualUser;
    }
}
