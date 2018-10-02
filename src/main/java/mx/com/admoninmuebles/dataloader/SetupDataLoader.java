package mx.com.admoninmuebles.dataloader;

import java.math.BigDecimal;
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

import mx.com.admoninmuebles.constant.EstatusTicketConst;
import mx.com.admoninmuebles.constant.PrivilegioConst;
import mx.com.admoninmuebles.constant.RolConst;
import mx.com.admoninmuebles.persistence.model.AreaServicio;
import mx.com.admoninmuebles.persistence.model.Asentamiento;
import mx.com.admoninmuebles.persistence.model.DatosAdicionales;
import mx.com.admoninmuebles.persistence.model.Direccion;
import mx.com.admoninmuebles.persistence.model.Inmueble;
import mx.com.admoninmuebles.persistence.model.Municipio;
import mx.com.admoninmuebles.persistence.model.Privilegio;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Ticket;
import mx.com.admoninmuebles.persistence.model.TipoAsentamiento;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.model.Zona;
import mx.com.admoninmuebles.persistence.repository.AreaServicioRepository;
import mx.com.admoninmuebles.persistence.repository.AsentamientoRepository;
import mx.com.admoninmuebles.persistence.repository.DatosAdicionalesRepository;
import mx.com.admoninmuebles.persistence.repository.DireccionRepository;
import mx.com.admoninmuebles.persistence.repository.InmuebleRepository;
import mx.com.admoninmuebles.persistence.repository.PrivilegioRepository;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.TicketRepository;
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
    private DireccionRepository direccionRepository;

    @Autowired
    private DatosAdicionalesRepository datosAdicionalesRepository;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private AsentamientoRepository asentamientoRepository;

    @Autowired
    private AreaServicioRepository areaServicioRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        Privilegio tablero = createPrivilegioIfNotFound(PrivilegioConst.TABLERO);
        Privilegio notificarPago = createPrivilegioIfNotFound(PrivilegioConst.NOTIFICAR_PAGO);
        Privilegio historialPagos = createPrivilegioIfNotFound(PrivilegioConst.HISTORIAL_PAGOS);
        Privilegio historialPagoInmuble = createPrivilegioIfNotFound(PrivilegioConst.HISTORIAL_PAGOS_INMUEBLE);
        Privilegio verificarPago = createPrivilegioIfNotFound(PrivilegioConst.VERIFICAR_PAGO);
        Privilegio abrirTicket = createPrivilegioIfNotFound(PrivilegioConst.ABRIR_TICKET);
        Privilegio verTicket = createPrivilegioIfNotFound(PrivilegioConst.VER_TICKET);
        Privilegio asignarTicket = createPrivilegioIfNotFound(PrivilegioConst.ASIGNAR_TICKET);
        Privilegio aceptarTicket = createPrivilegioIfNotFound(PrivilegioConst.ACEPTAR_TICKET);
        Privilegio atenderTicket = createPrivilegioIfNotFound(PrivilegioConst.ATENDER_TICKET);
        Privilegio rechazarTicket = createPrivilegioIfNotFound(PrivilegioConst.RECHAZAR_TICKET);
        Privilegio cerrarTicket = createPrivilegioIfNotFound(PrivilegioConst.CERRAR_TICKET);
        Privilegio cancelarTicket = createPrivilegioIfNotFound(PrivilegioConst.CANCELAR_TICKET);
        Privilegio listaSocios = createPrivilegioIfNotFound(PrivilegioConst.LISTA_SOCIOS);
        Privilegio estadoFinancieroInmueble = createPrivilegioIfNotFound(PrivilegioConst.ESTADO_FINANCIERO_INMUEBLE);
        Privilegio estadoFinancieroColonia = createPrivilegioIfNotFound(PrivilegioConst.ESTADO_FINANCIERO_COLONIA);
        Privilegio estadoFinancieroZona = createPrivilegioIfNotFound(PrivilegioConst.ESTADO_FINANCIERO_ZONA);
        Privilegio gestionarColonia = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_COLONIA);
        Privilegio gestionarZona = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_ZONA);
        Privilegio gestionarBienesInmubeles = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_INMUEBLES);
        Privilegio gestionarServicios = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_SERVICIOS);
        Privilegio gestionarPreguntas = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_PREGUNTAS);
        Privilegio gestionarSocioBi = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_SOCIO_BI);
        Privilegio gestionarRepBi = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_REP_BI);
        Privilegio gestionarAdminBi = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_ADMIN_BI);
        Privilegio gestionarAdminZona = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_ADMIN_ZONA);
        Privilegio gestionarProveedor = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_PROVEEDOR);
        Privilegio gestionarAdminCorp = createPrivilegioIfNotFound(PrivilegioConst.GESTIONAR_ADMIN_CORP);
        Privilegio reportes = createPrivilegioIfNotFound(PrivilegioConst.REPORTES);
        Privilegio reporteMorosos = createPrivilegioIfNotFound(PrivilegioConst.REPORTE_MOROSOS);

        List<Privilegio> privilegiosProveedor = new ArrayList<>();
        privilegiosProveedor.add(aceptarTicket);
        privilegiosProveedor.add(verTicket);
        privilegiosProveedor.add(atenderTicket);
        privilegiosProveedor.add(rechazarTicket);
        Rol proveedor = createRolIfNotFound(RolConst.ROLE_PROVEEDOR, privilegiosProveedor);

        List<Privilegio> privilegiosSocioBi = new ArrayList<>();
        privilegiosSocioBi.add(tablero);
        privilegiosSocioBi.add(notificarPago);
        privilegiosSocioBi.add(historialPagos);
        privilegiosSocioBi.add(verTicket);
        privilegiosSocioBi.add(abrirTicket);
        privilegiosSocioBi.add(cancelarTicket);
        Rol socioBi = createRolIfNotFound(RolConst.ROLE_SOCIO_BI, privilegiosSocioBi);

        List<Privilegio> privilegiosRepBi = new ArrayList<>();
        privilegiosRepBi.add(historialPagoInmuble);
        privilegiosRepBi.add(estadoFinancieroInmueble);
        privilegiosRepBi.add(listaSocios);
        privilegiosRepBi.add(reporteMorosos);
        privilegiosSocioBi.addAll(privilegiosRepBi);
        Rol repBi = createRolIfNotFound(RolConst.ROLE_REP_BI, privilegiosSocioBi);

        List<Privilegio> privilegiosAdminBi = new ArrayList<>();
        privilegiosAdminBi.addAll(privilegiosRepBi);
        privilegiosAdminBi.add(asignarTicket);
        privilegiosAdminBi.add(verTicket);
        privilegiosAdminBi.add(cerrarTicket);
        privilegiosAdminBi.add(cancelarTicket);
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
        Rol adminBi = createRolIfNotFound(RolConst.ROLE_ADMIN_BI, privilegiosAdminBi);

        List<Privilegio> privilegiosAdminZona = new ArrayList<>();
        privilegiosAdminZona.addAll(privilegiosAdminBi);
        privilegiosAdminZona.add(estadoFinancieroZona);
        Rol adminZona = createRolIfNotFound(RolConst.ROLE_ADMIN_ZONA, privilegiosAdminZona);

        List<Privilegio> privilegiosAdminCorp = new ArrayList<>();
        privilegiosAdminCorp.addAll(privilegiosAdminZona);
        privilegiosAdminCorp.add(gestionarZona);
        privilegiosAdminCorp.add(gestionarAdminZona);
        privilegiosAdminCorp.add(gestionarAdminCorp);
        privilegiosAdminCorp.add(reportes);
        Rol adminCorp = createRolIfNotFound(RolConst.ROLE_ADMIN_CORP, privilegiosAdminCorp);

        Usuario usuarioProveedorJardineria = createUsuarioIfNotFound("proveedor_jardineria", "Proveedor", "Jardineria", "", "proveedor", new ArrayList<>(Arrays.asList(proveedor)));
        Usuario usuarioProveedorLimpieza = createUsuarioIfNotFound("proveedor_limpieza", "Proveedor", "Limpieza", "", "proveedor", new ArrayList<>(Arrays.asList(proveedor)));
        Usuario usuarioProveedorConstruccion = createUsuarioIfNotFound("proveedor_construccion", "Proveedor", "Construccion", "", "proveedor", new ArrayList<>(Arrays.asList(proveedor)));
        Usuario usuarioSocioBi = createUsuarioIfNotFound("socio_bi", "Socio", "Bi", "Inmueble", "socio_bi", new ArrayList<>(Arrays.asList(socioBi)));
        Usuario usuarioRepBi = createUsuarioIfNotFound("rep_bi", "Representante", "Bien", "Inmubele", "rep_bi", new ArrayList<>(Arrays.asList(repBi)));
        Usuario usuarioAdminBi = createUsuarioIfNotFound("admin_bi", "Administrador", "Bien", "Inmueble", "admin_bi", new ArrayList<>(Arrays.asList(adminBi)));
        Usuario usuarioAdminZona = createUsuarioIfNotFound("admin_zona", "Administrador", "Zona", "", "admin_zona", new ArrayList<>(Arrays.asList(adminZona)));
        createUsuarioIfNotFound("admin_corp", "Administrador", "Corporativo", "", "admin_corp", new ArrayList<>(Arrays.asList(adminCorp)));

        Zona zona = createZonaIfNotFound("zona1", "Zona 1", usuarioAdminZona);
        Zona zona2 = createZonaIfNotFound("zona2", "CDMX", usuarioAdminZona);
        Zona zona3 = createZonaIfNotFound("zona3", "Aguascalientes", usuarioAdminZona);
        Zona zona4 = createZonaIfNotFound("zona4", "Querétaro", usuarioAdminZona);
        Zona zona5 = createZonaIfNotFound("zona5", "Cancún", usuarioAdminZona);
        Asentamiento asentamiento = updateAsentamientoIfFound(1L, zona);

        createInmuebleIfNotFound(1L, "Inmueble", asentamiento, usuarioAdminBi, usuarioSocioBi);

        AreaServicio areaServicioJardineria = createAreaServicioIfNotFound(1L, "Jardineria", usuarioProveedorJardineria);
        createAreaServicioIfNotFound(2L, "Limpieza", usuarioProveedorLimpieza);
        createAreaServicioIfNotFound(3L, "Construcción", usuarioProveedorConstruccion);
        createTicketIfNotFound(1L, "Podar cesped", "Quiero que poden el ceped de mi casa.", areaServicioJardineria, usuarioSocioBi, usuarioProveedorJardineria, EstatusTicketConst.ASIGNADO);

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
        }else {
        	Municipio municpio = new Municipio();
        	municpio.setId(9010l);
        	TipoAsentamiento tipoAsentamiento = new TipoAsentamiento();
        	tipoAsentamiento.setId(9l);
        	asentamiento = new Asentamiento();
        	asentamiento.setCodigoPostal("01000");
        	asentamiento.setNombre("San Ángel");
        	asentamiento.setMunicipio(municpio);
        	asentamiento.setTipoAsentamiento(tipoAsentamiento);
        	asentamiento.setZona(zona);
        	asentamientoRepository.save(asentamiento);
        }
        return asentamiento;
    }

    @Transactional
    public final Inmueble createInmuebleIfNotFound(final Long id, final String nombre, final Asentamiento asentamiento, final Usuario adminBi, final Usuario socio) {
        Optional<Inmueble> optInmueble = inmuebleRepository.findById(id);
        Inmueble inmueble = optInmueble.orElse(new Inmueble());
        if (!optInmueble.isPresent()) {
            inmueble.setNombre(nombre);
            inmueble.setDiaCuotaOrdinaria(11);
            inmueble.setMontoCuotaOrdinaria(new BigDecimal("11.11"));
            inmueble.setAdminBi(adminBi);
            inmueble.setImagenUrl("/files/inmueble.jpg");

            Direccion direccion = new Direccion();
            direccion.setAsentamiento(asentamiento);
            direccion.setCalle("calle");
            direccion.setEntreCalles("entreCalles");
            direccion.setNumeroExterior("numeroExterior");
            direccion.setNumeroInterior("numeroInterior");
            direccion.setReferencias("referencias");
            inmueble.setDireccion(direccionRepository.save(direccion));

            DatosAdicionales datosAdicionales = new DatosAdicionales();
            datosAdicionales.setCorreo("correo");
            datosAdicionales.setNombreRepresentante("nombreRepresentante");
            datosAdicionales.setNumeroCuenta("numeroCuenta");
            datosAdicionales.setRazonSocial("razonSocial");
            datosAdicionales.setRfc("rfc");
            datosAdicionales.setTelefono("telefono");
            inmueble.setDatosAdicionales(datosAdicionalesRepository.save(datosAdicionales));

            inmueble.addSocio(socio);

            inmueble = inmuebleRepository.save(inmueble);
        }
        return inmueble;
    }

    @Transactional
    public final AreaServicio createAreaServicioIfNotFound(final Long id, final String nombre, final Usuario proveedor) {
        Optional<AreaServicio> optAreaServicio = areaServicioRepository.findById(id);
        AreaServicio areaServicio = optAreaServicio.orElse(new AreaServicio());
        if (!optAreaServicio.isPresent()) {
            areaServicio.setNombre(nombre);
            areaServicio.addProveedor(proveedor);
            areaServicio = areaServicioRepository.save(areaServicio);
            usuarioRepository.save(proveedor);
        }

        return areaServicio;
    }

    @Transactional
    public final Ticket createTicketIfNotFound(final Long id, final String titulo, final String descripcion, final AreaServicio areaServicio, final Usuario usuarioCreador,
            final Usuario usuarioAsignado, String estatus) {
        Optional<Ticket> optTicket = ticketRepository.findById(id);
        Ticket ticket = optTicket.orElse(new Ticket());
        if (!optTicket.isPresent()) {
            ticket.setTitulo(titulo);
            ticket.setDescripcion(descripcion);
            ticket.setEstatus(estatus);
            ticket.setAreaServicio(areaServicio);
            ticket.setUsuarioCreador(usuarioCreador);
            ticket.setUsuarioAsignado(usuarioAsignado);
            ticket = ticketRepository.save(ticket);
        }
        return ticket;
    }
}