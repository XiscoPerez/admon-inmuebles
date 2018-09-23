package mx.com.admoninmuebles.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import mx.com.admoninmuebles.validation.ComparacionFechas;

@Data
@ComparacionFechas
public class NotificacionDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 1000)
    private String descripcion;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @NotNull
    private Date fechaExposicionInicial;

    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @NotNull
    private Date fechaExposicionFinal;
    
    @NotNull
    private Long inmuebleId;
    
    @DateTimeFormat(pattern="dd-MM-yyyy") 
    private Date fechaModificacion;
    

}
