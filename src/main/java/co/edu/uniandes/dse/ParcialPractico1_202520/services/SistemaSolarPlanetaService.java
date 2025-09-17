package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SistemaSolarPlanetaService {

    @Autowired
    private SistemaSolarRepository sistemaSolarRepository;

    @Autowired
    private PlanetaRepository planetaRepository;

    /**
     * Asociar un planeta existente a un sistema solar
     *
     * @param sistemaSolarId ID del Sistema Solar
     * @param planetaId      ID del Planeta
     * @return Planeta asociado al sistema solar
     * @throws EntityNotFoundException   El planeta o el sistema solar no fue encontrado
     * @throws IllegalOperationException Si las reglas de negocio no se cumplen
     */
    @Transactional
    public PlanetaEntity addPlaneta(Long sistemaSolarId, Long planetaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Se empieza el proceso para asociar el planeta de id = {} con el sistema solar de id = {}", planetaId, sistemaSolarId);

        Optional<SistemaSolar> sistemaSolarOpt = sistemaSolarRepository.findById(sistemaSolarId);
        if (sistemaSolarOpt.isEmpty())
            throw new EntityNotFoundException("No se encontró el sistema solar con el id dado");

        Optional<PlanetaEntity> planetaOpt = planetaRepository.findById(planetaId);
        if (planetaOpt.isEmpty())
            throw new EntityNotFoundException("No se encontró el planeta con el id dado");

        SistemaSolar sistemaSolarEntity = sistemaSolarOpt.get();    // Obtener las entidades 
        PlanetaEntity planetaEntity = planetaOpt.get();            

        double totalPoblacionActual = sistemaSolarEntity.getPlanetas().stream()     //Obtener la población total actual del sistema solar
                .mapToDouble(PlanetaEntity::getPoblacion)
                .sum();

        double nuevaPoblacionTotal = totalPoblacionActual + planetaEntity.getPoblacion();

        if (nuevaPoblacionTotal > 0) {
            double nuevoRatio = sistemaSolarEntity.getNum_stormtroopers() / nuevaPoblacionTotal;
            if (nuevoRatio < sistemaSolarEntity.getRatio_fuerza()) {
                throw new IllegalOperationException("El ratio actual no es suficiente para agregar el planeta al sistema solar");
            }
        }

        planetaEntity.setSistemaSolar(sistemaSolarEntity);
        log.info("Proceso finalizado para asociar el planeta de id = {} con el sistema solar de id = {}", planetaId, sistemaSolarId);
        return planetaEntity;
    }

    /**
     * Lista de todos los planetas de un sistema solar
     * @param sistemaSolarId ID del sistema solar
     * @return Lista de planetas del sistema solar
     * @throws EntityNotFoundException No se encontró el sistema solar
     */


    @Transactional
    public List<PlanetaEntity> getPlanetas(Long sistemaSolarId) throws EntityNotFoundException {
        log.info("Empieza el proceso para obtener los planetas del sistema solar de id = {}", sistemaSolarId);
        Optional<SistemaSolar> sistemaSolarEntity = sistemaSolarRepository.findById(sistemaSolarId);
        if (sistemaSolarEntity.isEmpty())
            throw new EntityNotFoundException("No se encontró el sistema solar con el id dado");

        return sistemaSolarEntity.get().getPlanetas();
    }
}

//REGLAS DE NEGOCIO: SISTEMA SOLAR PLANETA SERVICE
// 1. Un planeta solo puede ser asociado a un sistema solar si el nuevo ratio de fuerza (número de stormtroopers sobre la población total del sistema solar) es mayor o igual al ratio de fuerza del sistema solar.
// 2. La población total del sistema solar se calcula sumando la población de todos los planetas asociados a él.
// 3. Si la población total del sistema solar es 0, cualquier planeta puede ser asociado a él.
// 4. Si el planeta ya está asociado a un sistema solar, no se puede asociar a otro sistema solar 
