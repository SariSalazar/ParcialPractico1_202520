package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PlanetaService.class)
public class PlanetaServiceTest {

    @Autowired
    private PlanetaService planetaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        // Clear data if needed
    }

    @Test
    void testCreatePlaneta() throws IllegalOperationException {
        PlanetaEntity newEntity = factory.manufacturePojo(PlanetaEntity.class);
        newEntity.setNombre("Tatooine I");
        newEntity.setPoblacion(100.0);

        PlanetaEntity result = planetaService.createPlaneta(newEntity);
        assertNotNull(result);

        PlanetaEntity entity = entityManager.find(PlanetaEntity.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
    }

    @Test
    void testCreatePlanetaWithDuplicateName() throws IllegalOperationException {
        PlanetaEntity existingEntity = factory.manufacturePojo(PlanetaEntity.class);
        existingEntity.setNombre("Hoth I");
        existingEntity.setPoblacion(1.0);
        entityManager.persist(existingEntity);
        entityManager.flush();

        PlanetaEntity newEntity = factory.manufacturePojo(PlanetaEntity.class);
        newEntity.setNombre("Hoth I"); // Duplicate name

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            planetaService.createPlaneta(newEntity);
        });

        assertEquals("Ya existe un planeta con ese nombre.", exception.getMessage());
    }
}