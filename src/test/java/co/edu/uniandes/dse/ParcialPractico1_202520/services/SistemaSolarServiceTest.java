package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(SistemaSolarService.class)
public class SistemaSolarServiceTest {

    @Autowired
    private SistemaSolarService sistemaSolarService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        // Clear data if needed
    }

    @Test
    void testCreateSistemaSolar() throws IllegalOperationException {
        SistemaSolar newEntity = factory.manufacturePojo(SistemaSolar.class);
        newEntity.setNombre("Tatoo System");
        newEntity.setRatio_fuerza(0.5);
        newEntity.setNum_stormtroopers(2000);

        SistemaSolar result = sistemaSolarService.createSistemaSolar(newEntity);
        assertNotNull(result);

        SistemaSolar entity = entityManager.find(SistemaSolar.class, result.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getRatio_fuerza(), entity.getRatio_fuerza());
    }

    @Test
    void testCreateSistemaSolarWithInvalidRatio() {
        SistemaSolar newEntity = factory.manufacturePojo(SistemaSolar.class);
        newEntity.setRatio_fuerza(0.8); // Invalid ratio

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            sistemaSolarService.createSistemaSolar(newEntity);
        });

        assertEquals("Ratio de fuerza inválido. Debe estar entre 0.2 y 0.6", exception.getMessage());
    }
}
