package mx.com.admoninmuebles.persistence.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "estados_correos")
@Data
@EqualsAndHashCode(callSuper = false)
public class EstadoCorreo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_correo")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String nombre;
    
    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String correoPrincipal;
    
    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String correoSecundario;

}
