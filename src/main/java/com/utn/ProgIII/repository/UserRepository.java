package com.utn.ProgIII.repository;

import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.User.QUser;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;
import java.util.Optional;

/**
 * Clase que se encarga de interactuar con el ORM y el servicio de usuarios
 */
public interface UserRepository extends JpaRepository<User,Long>, QuerydslPredicateExecutor<User> {

    boolean existsByDni(String dni);
    boolean existsByCredentialRole(Role credentialRole);
    User findByCredential(Credential credential);
    Optional<User> findByCredential_Username(String credentialUsername);

}
