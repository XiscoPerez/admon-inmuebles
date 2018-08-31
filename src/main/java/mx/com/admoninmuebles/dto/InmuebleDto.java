package mx.com.admoninmuebles.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class InmuebleDto {
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    private String nombre;
    @NotNull
    @Min(value = 1)
    @Max(value = 31)
    private Integer diaCuotaOrdinaria;
    @NotNull
    @Digits(integer = 5, fraction = 2)
    private BigDecimal montoCuotaOrdinaria;
    @NotNull
    private MultipartFile imagen;
    private String imagenUrl;
    private Long adminBiId;
    private String direccionCalle;
    private String direccionNumeroExterior;
    private String direccionNumeroInterior;
    private String direccionEntreCalles;
    private String direccionReferencias;
    private Long direccionAsentamientoId;
    private String datosAdicionalesNombreRepresentante;
    private String datosAdicionalesRazonSocial;
    private String datosAdicionalesRfc;
    private String datosAdicionalesTelefono;
    private String datosAdicionalesCorreo;
    private String datosAdicionalesNumeroCuenta;

}
