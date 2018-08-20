package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ServicioDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	private Long idServicio;

    @NotNull
    @Size(min = 1, max = 100)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 2000)
    private String descripcion;

    @NotNull
    @Size(min = 1, max = 100)
    private String imagenUrl;

}
