package sistemadegestion.demo.repository;

import sistemadegestion.demo.entity.Guia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {

    List<Guia> findByTransportista(String transportista);
    List<Guia> findByFecha(String fecha);

    List<Guia> findByTransportistaAndFecha(String transportista, String fecha);
}