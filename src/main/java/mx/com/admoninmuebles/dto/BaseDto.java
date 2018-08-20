package mx.com.admoninmuebles.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseDto implements Serializable  {
	
    private static final long serialVersionUID = 1L;

    private Long creadoPor;

    private Date fechaCreacion;

    private Long modificadoPor;

    private Date fechaModificacion;

}
