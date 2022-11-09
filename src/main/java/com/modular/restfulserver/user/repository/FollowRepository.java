package com.modular.restfulserver.user.repository;

import com.modular.restfulserver.user.model.Follow;
import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

  Long countByFollowerId(User user);

  Long countByFollowingId(User user);

}
