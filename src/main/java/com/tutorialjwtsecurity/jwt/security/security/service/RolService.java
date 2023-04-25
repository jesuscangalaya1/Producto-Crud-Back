package com.tutorialjwtsecurity.jwt.security.security.service;

import com.tutorialjwtsecurity.jwt.security.security.entity.Rol;
import com.tutorialjwtsecurity.jwt.security.security.enums.RolNombre;
import com.tutorialjwtsecurity.jwt.security.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol) {
        rolRepository.save(rol);
    }
}
