package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ZonaDto extends BaseDto{
	
	private static final long serialVersionUID = 1369319540497747789L;

	private Long idZona;
	
    @NotNull
    @Size(min = 4, max = 10)
    private String codigo;

    @NotNull
    @Size(min = 6, max = 100)
    private String nombre;

}
