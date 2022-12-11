package org.suai.courceWork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suai.courceWork.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findFirstByName(String name);

    User findUserByEmail(String email);


}
