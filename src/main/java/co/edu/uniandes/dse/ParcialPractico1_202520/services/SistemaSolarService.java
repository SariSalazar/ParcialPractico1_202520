package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SistemaSolarService {

    @Autowired
    private SistemaSolarRepository sistemaSolarRepository;

    @Transactional
    public SistemaSolar createSistemaSolar(SistemaSolar sistemaSolar) throws IllegalOperationException {
        log.info("Empieza el proceso de creación del sistema solar");

        if (sistemaSolar.getNombre() == null || sistemaSolar.getNombre().length() > 31) {
            throw new IllegalOperationException("Nombre de sistema solar inválido. Debe tener menos de 31 caracteres");
        }

        if (sistemaSolar.getRatio_fuerza() <= 0.2 || sistemaSolar.getRatio_fuerza() >= 0.6) {
            throw new IllegalOperationException("Ratio de fuerza inválido. Debe estar entre 0.2 y 0.6");
        }

        if (sistemaSolar.getNum_stormtroopers() < 1000) {
            throw new IllegalOperationException("Número de stormtroopers inválido. Debe ser mayor a 1000 unidades");
        }

        log.info("Finished process to create a solar system");
        return sistemaSolarRepository.save(sistemaSolar);
    }
}

// REGLAS DE NEGOCIO: SISTEMA SOLAR SERVICE
// 1. El nombre del sistema solar no puede ser nulo y debe tener menos de 31 caracteres.
// 2. El ratio de fuerza del sistema solar debe ser mayor o igual a 0.2 y menor o igual a 0.6.
// 3. El número de stormtroopers en el sistema solar debe ser mayor a 1000 unidades.
// 4. Un planeta solo puede ser asociado a un sistema solar si el nuevo ratio de fuerza (número de stormtroopers sobre la población total del sistema solar) es mayor o igual al ratio de fuerza del sistema solar.
// 5. La población total del sistema solar se calcula sumando la población de todos los planetas asociados a él.
