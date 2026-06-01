package com.utn.ProgIII.security;

import com.utn.ProgIII.exceptions.CredentialNotFoundException;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;
import com.utn.ProgIII.repository.CredentialRepository;
import com.utn.ProgIII.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clase encargada de Validar y crear el usuario en el contexto de Spring Security.
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final UserRepository userRepository;

    public UserDetailServiceImpl(CredentialRepository credentialRepository, UserRepository userRepository){
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credential credential = credentialRepository.findByUsername(username).orElseThrow(
                () -> new CredentialNotFoundException("Por favor, revise sus datos e intente de nuevo")
        );

        User user = userRepository.findByCredential(credential);

        if(user.getStatus() == UserStatus.DISABLED)
        {
            throw new CredentialNotFoundException("El usuario esta desactivado, contacte a su administrador");
        }


        GrantedAuthority authorities = new SimpleGrantedAuthority(credential.getRole().name());


        return org.springframework.security.core.userdetails.User.builder()
                .username(credential.getUsername())
                .password(credential.getPassword())
                .roles(credential.getRole().name())
                .build();
    }


}
