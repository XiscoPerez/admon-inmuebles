package mx.com.admoninmuebles.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.admoninmuebles.dto.RolDto;
import mx.com.admoninmuebles.persistence.model.Rol;
import mx.com.admoninmuebles.persistence.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Rol save(final RolDto rolDto) {
        return rolRepository.save(modelMapper.map(rolDto, Rol.class));
    }

    @Override
    public Collection<RolDto> findAll() {
        return StreamSupport.stream(rolRepository.findAll().spliterator(), false).map(rol -> modelMapper.map(rol, RolDto.class)).collect(Collectors.toList());
    }

}
