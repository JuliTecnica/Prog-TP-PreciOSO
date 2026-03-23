package com.utn.ProgIII.model.Credential;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Los tipos de usuario existentes en el sistema
 */
@Schema(defaultValue = "EMPLOYEE")
public enum Role {
    ADMIN,
    MANAGER,
    EMPLOYEE,
    CUSTOMER
}
