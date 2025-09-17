package co.edu.uniandes.dse.ParcialPractico1_202520.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;

/**
 * Interface that persists a solar system.
 *
 * @author ISIS2603
 */
@Repository
public interface SistemaSolarRepository extends JpaRepository<SistemaSolar, Long> {

    /**
     * Encontrar un sistema solar por su nombre
     * @param nombre El nombre del sistema solar a buscar
     * @return El sistema solar encontrado con ese nombre
     */
    SistemaSolar findByNombre(String nombre);
}
