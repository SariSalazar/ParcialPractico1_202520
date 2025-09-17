package co.edu.uniandes.dse.ParcialPractico1_202520.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;

/**
 * Interface that persists a planet.
 *
 * @author ISIS2603
 */
@Repository
public interface PlanetaRepository extends JpaRepository<PlanetaEntity, Long> {

    /**
     * Encontrar un planeta por su nombre
     * @param nombre Nombre del planeta a buscar
     * @return El planeta encontrado con ese nombre
     */
    PlanetaEntity findByNombre(String nombre);
}
