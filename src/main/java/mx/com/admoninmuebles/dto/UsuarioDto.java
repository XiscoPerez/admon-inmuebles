package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UsuarioDto {
    @NotNull
    @Size(min = 6, max = 45)
    private String username;

    @NotNull
    @Size(min = 8, max = 60)
    private String password;

    private String confirmPassword;

    @NotNull
    @Size(min = 6, max = 45)
    private String email;

}
