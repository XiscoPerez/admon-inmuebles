package mx.com.admoninmuebles.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import mx.com.admoninmuebles.persistence.model.Direccion;

@Data
public class SucursalDto  extends BaseDto{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
