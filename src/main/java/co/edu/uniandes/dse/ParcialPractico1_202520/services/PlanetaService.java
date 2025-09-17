package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlanetaService {

    @Autowired
    private PlanetaRepository planetaRepository;

    @Transactional
    public PlanetaEntity createPlaneta(PlanetaEntity planeta) throws IllegalOperationException {
        log.info("Empieza el proceso de creación del planeta");

        String nombre = planeta.getNombre();
        if (nombre == null || (!nombre.endsWith(" I") && !nombre.endsWith(" II") && !nombre.endsWith(" III"))) {
            throw new IllegalOperationException("Nombre de planeta inválido. Debe terminar en I, II o III.");
        }
        if (planetaRepository.findByNombre(nombre) != null) {
            throw new IllegalOperationException("Ya existe un planeta con ese nombre.");
        }

        if (planeta.getPoblacion() < 0) {
            throw new IllegalOperationException("Población inválida. Debe ser un número positivo mayor a 0.");
        }

        log.info("Planeta creado exitosamente");
        return planetaRepository.save(planeta);
    }
}

// REGLAS DE NEGOCIO: PLANETA SERVICE
// 1. El nombre del planeta no puede ser nulo y debe terminar en "I", "II" o "III".
// 2. No puede existir otro planeta con el mismo nombre.
// 3. La población del planeta debe ser un número positivo mayor a 0.
// 4. Un planeta no puede ser asociado a más de un sistema solar a la vez.
// 5. Un planeta solo puede ser eliminado si no está asociado a ningún sistema solar.
