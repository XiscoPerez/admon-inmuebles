package mx.com.admoninmuebles.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.constant.RolConstant;
import mx.com.admoninmuebles.dto.ProveedorDto;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.model.Telefono;
import mx.com.admoninmuebles.persistence.model.Usuario;
import mx.com.admoninmuebles.persistence.repository.DatosAdicionalesRepository;
import mx.com.admoninmuebles.persistence.repository.DireccionRepository;
import mx.com.admoninmuebles.persistence.repository.RolRepository;
import mx.com.admoninmuebles.persistence.repository.TelefonoRepository;
import mx.com.admoninmuebles.persistence.repository.UsuarioRepository;

@Service
public class ProveedorServiceImpl implements ProveedorService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private DireccionRepository direccionRepository;
	
	@Autowired
	private TelefonoRepository telefonoRepository;

	@Autowired
	private DatosAdicionalesRepository datosAdicionalesRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Collection<ProveedorDto> getProveedores() {
		Optional<Rol> rolOpt = rolRepository.findByNombre(RolConstant.ROLE_PROVEEDOR);
		Collection<Usuario> proveedores = rolOpt.get().getUsuarios();
		return StreamSupport.stream(proveedores.spliterator(), false)
				 .map(proveedor -> modelMapper.map(proveedor, ProveedorDto.class))
				 .collect(Collectors.toList());
	}

	@Override
	public ProveedorDto buscarProveedorPorId(Long idProveedor) {
		 Optional<Usuario> proveedor = usuarioRepository.findById(idProveedor);
	     return modelMapper.map(proveedor.get(), ProveedorDto.class);
	}

	@Override
	public ProveedorDto guardar(ProveedorDto proveedorDto) {
		Usuario proveedor = modelMapper.map(proveedorDto, Usuario.class);
		
		List<Telefono> telefonos = new ArrayList<>();
		for(Telefono telefono: proveedor.getTelefonos()) {
			telefonos.add(telefonoRepository.save(telefono));
		}
		
		proveedor.setTelefonos(telefonos);
		proveedor.setDireccion(direccionRepository.save(proveedor.getDireccion()));
		proveedor.setDatosAdicionales(datosAdicionalesRepository.save(proveedor.getDatosAdicionales()));
		return modelMapper.map(usuarioRepository.save(proveedor), ProveedorDto.class);
	}

	@Override
	public ProveedorDto editar(ProveedorDto proveedorDto) {
		return null;
	}

	@Override
	public void eliminar(Long idProveedor) {
		usuarioRepository.deleteById(idProveedor);

	}
	
    @Override
    public boolean exist(final Long id) {
        return usuarioRepository.existsById(id);
    }

}
