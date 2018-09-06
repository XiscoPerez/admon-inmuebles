package mx.com.admoninmuebles.dataloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import mx.com.admoninmuebles.persistence.model.AreaServicio;
import mx.com.admoninmuebles.persistence.model.Asentamiento;
import mx.com.admoninmuebles.persistence.model.EstatusTicket;
import mx.com.admoninmuebles.persistence.model.Privilegio;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.model.Zona;
import mx.com.admoninmuebles.persistence.repository.AreaServicioRepository;
import mx.com.admoninmuebles.persistence.repository.AsentamientoRepository;
import mx.com.admoninmuebles.persistence.repository.EstatusTicketRepository;
import mx.com.admoninmuebles.persistence.repository.PrivilegioRepository;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;
import mx.com.admoninmuebles.persistence.repository.ZonaRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PrivilegioRepository privilegioRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private AsentamientoRepository asentamientoRepository;

    @Autowired
    private AreaServicioRepository areaServicioRepository;

    @Autowired
    private EstatusTicketRepository estatusTicketRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        Privilegio tablero = createPrivilegioIfNotFound("TABLERO");
        Privilegio notificarPago = createPrivilegioIfNotFound("NOTIFICAR_PAGO");
        Privilegio historialPagos = createPrivilegioIfNotFound("HISTORIAL_PAGOS");
        Privilegio historialPagoInmuble = createPrivilegioIfNotFound("HISTORIAL_PAGOS_INMUEBLE");
        Privilegio verificarPago = createPrivilegioIfNotFound("VERIFICAR_PAGO");
        Privilegio abrirTicket = createPrivilegioIfNotFound("ABRIR_TICKET");
        Privilegio asignarTicket = createPrivilegioIfNotFound("ASIGNAR_TICKET");
        Privilegio aceptarTicket = createPrivilegioIfNotFound("ACEPTAR_TICKET");
        Privilegio atenderTicket = createPrivilegioIfNotFound("ATENDER_TICKET");
        Privilegio rechazarTicket = createPrivilegioIfNotFound("RECHAZAR_TICKET");
        Privilegio cerrarTicket = createPrivilegioIfNotFound("CERRAR_TICKET");
        Privilegio cancelarTicket = createPrivilegioIfNotFound("CANCELAR_TICKET");
        Privilegio listaSocios = createPrivilegioIfNotFound("LISTA_SOCIOS");
        Privilegio estadoFinancieroInmueble = createPrivilegioIfNotFound("ESTADO_FINANCIERO_INMUEBLE");
        Privilegio estadoFinancieroColonia = createPrivilegioIfNotFound("ESTADO FINANCIERO_COLONIA");
        Privilegio estadoFinancieroZona = createPrivilegioIfNotFound("ESTADO_FINANCIERO_ZONA");
        Privilegio gestionarColonia = createPrivilegioIfNotFound("GESTIONAR_COLONIA");
        Privilegio gestionarZona = createPrivilegioIfNotFound("GESTIONAR_ZONA");
        Privilegio gestionarBienesInmubeles = createPrivilegioIfNotFound("GESTIONAR_BIENES_INMUEBLES");
        Privilegio gestionarServicios = createPrivilegioIfNotFound("GESTIONAR_SERVICIOS");
        Privilegio gestionarPreguntas = createPrivilegioIfNotFound("GESTIONAR_PREGUNTAS");
        Privilegio gestionarSocioBi = createPrivilegioIfNotFound("GESTIONAR_SOCIO_BI");
        Privilegio gestionarRepBi = createPrivilegioIfNotFound("GESTIONAR_REP_BI");
        Privilegio gestionarAdminBi = createPrivilegioIfNotFound("GESTIONAR_ADMIN_BI");
        Privilegio gestionarAdminZona = createPrivilegioIfNotFound("GESTIONAR_ADMIN_ZONA");
        Privilegio gestionarProveedor = createPrivilegioIfNotFound("GESTIONAR_PROVEEDOR");
        Privilegio gestionarAdminCorp = createPrivilegioIfNotFound("GESTIONAR_ADMIN_CORP");
        Privilegio reportes = createPrivilegioIfNotFound("REPORTES");
        Privilegio reporteMorosos = createPrivilegioIfNotFound("REPORTE_MOROSOS");

        List<Privilegio> privilegiosProveedor = new ArrayList<>();
        privilegiosProveedor.add(aceptarTicket);
        privilegiosProveedor.add(atenderTicket);
        privilegiosProveedor.add(rechazarTicket);
        Rol proveedor = createRolIfNotFound("ROLE_PROVEEDOR", privilegiosProveedor);

        List<Privilegio> privilegiosSocioBi = new ArrayList<>();
        privilegiosSocioBi.add(tablero);
        privilegiosSocioBi.add(notificarPago);
        privilegiosSocioBi.add(historialPagos);
        privilegiosSocioBi.add(abrirTicket);
        privilegiosSocioBi.add(cancelarTicket);
        Rol socioBi = createRolIfNotFound("ROLE_SOCIO_BI", privilegiosSocioBi);

        List<Privilegio> privilegiosRepBi = new ArrayList<>();
        privilegiosRepBi.add(historialPagoInmuble);
        privilegiosRepBi.add(estadoFinancieroInmueble);
        privilegiosRepBi.add(listaSocios);
        privilegiosRepBi.add(reporteMorosos);
        privilegiosSocioBi.addAll(privilegiosRepBi);
        Rol repBi = createRolIfNotFound("ROLE_REP_BI", privilegiosSocioBi);

        List<Privilegio> privilegiosAdminBi = new ArrayList<>();
        privilegiosAdminBi.addAll(privilegiosRepBi);
        privilegiosAdminBi.add(asignarTicket);
        privilegiosAdminBi.add(cerrarTicket);
        privilegiosAdminBi.add(gestionarSocioBi);
        privilegiosAdminBi.add(verificarPago);
        privilegiosAdminBi.add(historialPagos);
        privilegiosAdminBi.add(gestionarColonia);
        privilegiosAdminBi.add(gestionarBienesInmubeles);
        privilegiosAdminBi.add(gestionarServicios);
        privilegiosAdminBi.add(gestionarPreguntas);
        privilegiosAdminBi.add(gestionarSocioBi);
        privilegiosAdminBi.add(gestionarRepBi);
        privilegiosAdminBi.add(gestionarAdminBi);
        privilegiosAdminBi.add(gestionarProveedor);
        privilegiosAdminBi.add(estadoFinancieroColonia);
        Rol adminBi = createRolIfNotFound("ROLE_ADMIN_BI", privilegiosAdminBi);

        List<Privilegio> privilegiosAdminZona = new ArrayList<>();
        privilegiosAdminZona.addAll(privilegiosAdminBi);
        privilegiosAdminZona.add(estadoFinancieroZona);
        Rol adminZona = createRolIfNotFound("ROLE_ADMIN_ZONA", privilegiosAdminZona);

        List<Privilegio> privilegiosAdminCorp = new ArrayList<>();
        privilegiosAdminCorp.add(gestionarZona);
        privilegiosAdminCorp.addAll(privilegiosAdminZona);
        privilegiosAdminCorp.add(gestionarAdminZona);
        privilegiosAdminCorp.add(gestionarAdminCorp);
        privilegiosAdminCorp.add(reportes);
        Rol adminCorp = createRolIfNotFound("ROLE_ADMIN_CORP", privilegiosAdminCorp);

        Usuario usuarioProveedor = createUsuarioIfNotFound("proveedor", "Proveedor", "", "", "proveedor", new ArrayList<>(Arrays.asList(proveedor)));
        Usuario usuarioSocioBi = createUsuarioIfNotFound("socio_bi", "Socio", "Bi", "Inmueble", "socio_bi", new ArrayList<>(Arrays.asList(socioBi)));
        Usuario usuarioRepBi = createUsuarioIfNotFound("rep_bi", "Representante", "Bien", "Inmubele", "rep_bi", new ArrayList<>(Arrays.asList(repBi)));
        Usuario usuarioAdminBi = createUsuarioIfNotFound("admin_bi", "Administrador", "Bien", "Inmueble", "admin_bi", new ArrayList<>(Arrays.asList(adminBi)));
        Usuario usuarioAdminZona = createUsuarioIfNotFound("admin_zona", "Administrador", "Zona", "", "admin_zona", new ArrayList<>(Arrays.asList(adminZona)));
        Usuario usuarioAdminCorp = createUsuarioIfNotFound("admin_corp", "Administrador", "Corporativo", "", "admin_corp", new ArrayList<>(Arrays.asList(adminCorp)));

        Zona zona = createZonaIfNotFound("zona1", "Zona 1", usuarioAdminZona);
        updateAsentamientoIfFound(1L, zona);

        createAreaServicioIfNotFound(1L, "Jardineria");
        createEstatusTicketIfNotFound(1L, "Creado");

        alreadySetup = true;
    }

    @Transactional
    public final Privilegio createPrivilegioIfNotFound(final String nombre) {
        Optional<Privilegio> optPrivilegio = privilegioRepository.findByNombre(nombre);
        Privilegio privilegio = optPrivilegio.orElse(new Privilegio());
        if (!optPrivilegio.isPresent()) {
            privilegio.setNombre(nombre);
            privilegio = privilegioRepository.save(privilegio);
        }
        return privilegio;
    }

    @Transactional
    public final Rol createRolIfNotFound(final String nombre, final Collection<Privilegio> privilegios) {
        Optional<Rol> optRol = rolRepository.findByNombre(nombre);
        Rol rol = optRol.orElse(new Rol());
        if (!optRol.isPresent()) {
            rol.setNombre(nombre);
            rol.setPrivilegios(privilegios);
            rol = rolRepository.save(rol);
        }

        return rol;
    }

    @Transactional
    public final Usuario createUsuarioIfNotFound(final String username, final String firstNombre, final String apellidoPatarno, final String apellidoMaterno, final String contrasenia,
            final Collection<Rol> roles) {
        Optional<Usuario> optUsuario = usuarioRepository.findByUsername(username);
        Usuario usuario = optUsuario.orElse(new Usuario());
        if (!optUsuario.isPresent()) {
            usuario.setUsername(username);
            usuario.setNombre(firstNombre);
            usuario.setApellidoPaterno(apellidoPatarno);
            usuario.setApellidoMaterno(apellidoMaterno);

            usuario.setContrasenia(passwordEncoder.encode(contrasenia));
            usuario.setRoles(roles);
            usuario = usuarioRepository.save(usuario);
        }
        return usuario;
    }

    @Transactional
    public final Zona createZonaIfNotFound(final String codigo, final String nombre, final Usuario adminZona) {
        Optional<Zona> optZona = zonaRepository.findById(codigo);
        Zona zona = optZona.orElse(new Zona());
        if (!optZona.isPresent()) {
            zona.setCodigo(codigo);
            zona.setNombre(nombre);
            zona.setAdminZona(adminZona);
            zona = zonaRepository.save(zona);
        }
        return zona;
    }

    @Transactional
    public final Asentamiento updateAsentamientoIfFound(final Long id, final Zona zona) {
        Optional<Asentamiento> optAsentamiento = asentamientoRepository.findById(id);
        Asentamiento asentamiento = null;
        if (optAsentamiento.isPresent()) {
            asentamiento = optAsentamiento.get();
            asentamiento.setZona(zona);
            asentamientoRepository.save(asentamiento);
        }
        return asentamiento;
    }

    @Transactional
    public final AreaServicio createAreaServicioIfNotFound(final Long id, final String nombre) {
        Optional<AreaServicio> optAreaServicio = areaServicioRepository.findById(id);
        AreaServicio areaServicio = optAreaServicio.orElse(new AreaServicio());
        if (!optAreaServicio.isPresent()) {
            areaServicio.setNombre(nombre);
            areaServicio = areaServicioRepository.save(areaServicio);
        }
        return areaServicio;
    }

    @Transactional
    public final EstatusTicket createEstatusTicketIfNotFound(final Long id, final String nombre) {
        Optional<EstatusTicket> optEstatusTicket = estatusTicketRepository.findById(id);
        EstatusTicket estatusTicket = optEstatusTicket.orElse(new EstatusTicket());
        if (!optEstatusTicket.isPresent()) {
            estatusTicket.setNombre(nombre);
            estatusTicket = estatusTicketRepository.save(estatusTicket);
        }
        return estatusTicket;
    }
}