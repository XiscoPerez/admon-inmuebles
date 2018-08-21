package mx.com.admoninmuebles.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.Data;

@Data
public class NotificacionDto extends BaseDto{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNotificacion;

    @NotNull
    @Size(min = 1, max = 100)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 1000)
    private String descripcion;

    @NotNull
    private Date fechaExposicionInicial;

    @NotNull
    private Date fechaExposicionFinal;

}
