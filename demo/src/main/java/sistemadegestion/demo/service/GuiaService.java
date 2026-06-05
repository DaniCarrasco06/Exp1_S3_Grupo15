package sistemadegestion.demo.service;

import sistemadegestion.demo.entity.Guia;
import sistemadegestion.demo.repository.GuiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuiaService {

    @Autowired
    private GuiaRepository repository;

    public Guia guardar(Guia guia) {
        return repository.save(guia);
    }

    public List<Guia> listar() {
        return repository.findAll();
    }

    public Optional<Guia> buscar(Long id) {
        return repository.findById(id);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
   public List<Guia> buscarPorTransportista(String transportista){
    return repository.findByTransportista(transportista);
}

public List<Guia> buscarPorFecha(String fecha){
    return repository.findByFecha(fecha);
}

public Guia actualizar(Long id, Guia nuevaGuia){

    Guia guia = repository.findById(id).orElse(null);

    if(guia != null){

        guia.setNumeroGuia(nuevaGuia.getNumeroGuia());
        guia.setTransportista(nuevaGuia.getTransportista());
        guia.setFecha(nuevaGuia.getFecha());
        guia.setRutaArchivo(nuevaGuia.getRutaArchivo());

        return repository.save(guia);
    }

    return null;
    }
    
    public List<Guia> buscarPorTransportistaYFecha(
        String transportista,
        String fecha) {

    return repository.findByTransportistaAndFecha(
            transportista,
            fecha);
}
}

