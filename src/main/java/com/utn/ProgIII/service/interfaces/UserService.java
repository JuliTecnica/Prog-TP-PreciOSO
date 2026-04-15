package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.CreateUserDTO;
import com.utn.ProgIII.dto.CreateUserNoAuthDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que dicta los comportamientos que debe incorporar el servicio de usuarios
 */
public interface UserService {
    UserWithCredentialDTO createUserNoAuth(CreateUserNoAuthDTO dto);
    UserWithCredentialDTO createUserWithCredential(CreateUserDTO dto);
    UserWithCredentialDTO getUserById(Long id);
    UserWithCredentialDTO updateUser(Long id, CreateUserDTO dto);
    void deleteOrRemoveUser(Long id, String method);

    Page<UserWithCredentialDTO> getUsersPage(Pageable pageable, String status, String role);
}
