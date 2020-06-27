package com.daniel.safetech.repositories;

import com.daniel.safetech.enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
