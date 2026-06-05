package sistemadegestion.demo.controller;

import sistemadegestion.demo.entity.Guia;
import sistemadegestion.demo.service.GuiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guias")
public class GuiaController {

    @Autowired
    private GuiaService service;

    @PostMapping
    public Guia crear(@RequestBody Guia guia) {
        return service.guardar(guia);
    }

    @GetMapping
    public List<Guia> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Optional<Guia> buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
    
    @PutMapping("/{id}")
    public Guia actualizar(@PathVariable Long id,
                       @RequestBody Guia guia){

    return service.actualizar(id, guia);
    }

    @GetMapping("/transportista/{nombre}")
    public List<Guia> buscarTransportista(
        @PathVariable String nombre){

    return service.buscarPorTransportista(nombre);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Guia> buscarFecha(
        @PathVariable String fecha){

    return service.buscarPorFecha(fecha);
    }

    @GetMapping("/buscar")
    public List<Guia> buscarPorTransportistaYFecha(
        @RequestParam String transportista,
        @RequestParam String fecha) {

    return service.buscarPorTransportistaYFecha(
            transportista,
            fecha);
    }
}
