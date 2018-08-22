package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SucursalDto {
    private Long idSucursal;

    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;

    private Long idDireccion;

    @NotNull
    @Size(min = 1, max = 1000)
    private String referencias;

    @NotNull
    @Size(min = 1, max = 50)
    private String correo;

    @NotNull
    @Size(min = 1, max = 50)
    private String telefono;

}
