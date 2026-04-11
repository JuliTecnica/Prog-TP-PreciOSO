package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.security.JwtUtil;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import com.utn.ProgIII.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Servicio que permite a un usuario entrar al sistema
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailService;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserDetailServiceImpl userDetailService,
                           JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Maneja el ingreso al sistema
     * @param loginRequestDTO DTO con las credenciales para ingresar
     * @return un JWT (Json Web Token)
     */
    @Override
        public String login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        ));

        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequestDTO.username());
        return jwtUtil.generateToken(userDetails);

    }

    /**
     * Verifica si el usuario logueado es un empleado
     * @return <code>boolean</code> true si lo es, false de otro modo
     */
    public boolean isEmployee() {

        return hasRole("ROLE_EMPLOYEE") && (getRoleCount() == 2);
    }

    /**
     * Verifica si el usuario logueado tiene cierto rol
     * @param roleName El nombre del rol (ej.: ROLE_MANAGER)
     * @return un booleano true si lo tiene
     */
    public boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    /**
     * Devuelve la cantidad de roles que un usuario tiene
     */
    @Override
    public Long getRoleCount() {
        return (long) SecurityContextHolder.getContext().getAuthentication().getAuthorities().size();
    }


}
