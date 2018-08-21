package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EstatusTicketDto  extends BaseDto{
	
	private static final long serialVersionUID = 1L;

	private Long idEstatusTicket;
	
    @NotNull
    @Size(min = 6, max = 100)
    private String nombre;

}
