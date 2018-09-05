package mx.com.admoninmuebles.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TicketDto {
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 4000)
    @Column(length = 4000, columnDefinition = "text", nullable = false)
    private String descripcion;

    @NotNull
    private Long areaServicioId;
    private String areaServicioNombre;
    private Long estatusTicketId;
    private String estatusTicketNombre;
    private Long usuarioCreadorId;

}
