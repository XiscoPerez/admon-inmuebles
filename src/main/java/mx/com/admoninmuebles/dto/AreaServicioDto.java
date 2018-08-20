package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AreaServicioDto extends BaseDto{

	private static final long serialVersionUID = 1L;
	
    private Long idAreaServicio;
    
	@NotNull
    @Size(min = 5, max = 50)
    private String nombre;

}
