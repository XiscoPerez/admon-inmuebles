package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PreguntaFrecuenteDto  extends BaseDto{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Long idPreguntaFrecuente;

    @NotNull
    @Size(min = 1, max = 500)
    private String pregunta;

    @NotNull
    @Size(min = 1, max = 2000)
    private String respuesta;

}
