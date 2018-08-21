package mx.com.admoninmuebles.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AvisoOportunoDto extends BaseDto{
   
	private static final long serialVersionUID = 1L;

	private Long idAvisoOportuno;

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
