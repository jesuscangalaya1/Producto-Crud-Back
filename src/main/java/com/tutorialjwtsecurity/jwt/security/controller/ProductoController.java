package com.tutorialjwtsecurity.jwt.security.controller;


import com.tutorialjwtsecurity.jwt.security.dto.Mensaje;
import com.tutorialjwtsecurity.jwt.security.dto.ProductoDto;
import com.tutorialjwtsecurity.jwt.security.entity.Producto;
import com.tutorialjwtsecurity.jwt.security.service.ProductoService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    //@ApiOperation("Muestra una lista de productos")
    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        return ResponseEntity.ok(productoService.list());
    }

    //@ApiIgnore
    @GetMapping("/detail/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(productoService.getOne(id));
    }

    //@ApiIgnore
    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Producto> getByNombre(@PathVariable("nombre") String nombre){
        return ResponseEntity.ok(productoService.getByNombre(nombre));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Mensaje> create(@Valid @RequestBody ProductoDto dto){
        return ResponseEntity.ok(productoService.save(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Mensaje> update(@PathVariable("id")int id, @Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Mensaje> delete(@PathVariable("id")int id){
        return ResponseEntity.ok(productoService.delete(id));
    }


}
