package com.tutorialjwtsecurity.jwt.security.security.service;

import com.tutorialjwtsecurity.jwt.security.dto.Mensaje;
import com.tutorialjwtsecurity.jwt.security.exceptions.CustomException;
import com.tutorialjwtsecurity.jwt.security.security.dto.JwtDto;
import com.tutorialjwtsecurity.jwt.security.security.dto.LoginUsuario;
import com.tutorialjwtsecurity.jwt.security.security.dto.NuevoUsuario;
import com.tutorialjwtsecurity.jwt.security.security.entity.Rol;
import com.tutorialjwtsecurity.jwt.security.security.entity.Usuario;
import com.tutorialjwtsecurity.jwt.security.security.enums.RolNombre;
import com.tutorialjwtsecurity.jwt.security.security.jwt.JwtProvider;
import com.tutorialjwtsecurity.jwt.security.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> getByNombreUsuarioOrEmail(String nombreOrEmail){
        return usuarioRepository.findByNombreUsuarioOrEmail(nombreOrEmail, nombreOrEmail);
    }

    public Optional<Usuario> getByTokenPassword(String tokenPassword){
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public JwtDto login(LoginUsuario loginUsuario){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        return new JwtDto(jwt);
    }

    public JwtDto refresh(JwtDto jwtDto) throws ParseException {
        String token = jwtProvider.refreshToken(jwtDto);
        return new JwtDto(token);
    }

    public Mensaje save(NuevoUsuario nuevoUsuario){
        if(usuarioRepository.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese nombre de usuario ya existe");
        if(usuarioRepository.existsByEmail(nuevoUsuario.getEmail()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "ese email de usuario ya existe");
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
        return new Mensaje(usuario.getNombreUsuario() + " ha sido creado");
    }
}

