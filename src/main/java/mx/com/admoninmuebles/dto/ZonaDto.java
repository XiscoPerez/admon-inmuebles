package mx.com.admoninmuebles.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ZonaDto {
    private Long id;

    private Long creadoPorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date fechaCreacion;

    @NotNull
    @Size(min = 4, max = 10)
    private String codigo;

    @NotNull
    @Size(min = 6, max = 100)
    private String nombre;

}
