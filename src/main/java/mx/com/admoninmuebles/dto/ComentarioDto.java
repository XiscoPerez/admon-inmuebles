package mx.com.admoninmuebles.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ComentarioDto {
	
	private Long idComentario;

    @NotNull
    @Size(min = 6, max = 100)
    private String nombre;

}
