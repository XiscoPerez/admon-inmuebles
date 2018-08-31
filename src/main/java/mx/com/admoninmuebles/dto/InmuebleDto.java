package mx.com.admoninmuebles.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class InmuebleDto {
    private Long id;
    @NotNull
    @Size(min = 6, max = 100)
    private String nombre;
    private Integer diaCuotaOrdinaria;
    private BigDecimal montoCuotaOrdinaria;
    @NotNull
    private MultipartFile imagen;
    private String imagenUrl;
    private Long adminBiId;
    private String direccionCalle;
    private String direccionNumeroExterior;
    private String direccionNumeroInterior;
    private Long direccionAsentamientoId;
    private String datosAdicionalesNombreRepresentante;
    private String datosAdicionalesRazonSocial;
    private String datosAdicionalesRfc;
    private String datosAdicionalesTelefono;
    private String datosAdicionalesCorreo;
    private String datosAdicionalesNumeroCuenta;

}
