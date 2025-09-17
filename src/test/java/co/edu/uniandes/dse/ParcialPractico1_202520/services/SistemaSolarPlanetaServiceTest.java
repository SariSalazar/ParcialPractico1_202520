package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(SistemaSolarPlanetaService.class)
public class SistemaSolarPlanetaServiceTest {

    @Autowired
    private SistemaSolarPlanetaService sistemaSolarPlanetaService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private SistemaSolar sistemaSolar = new SistemaSolar();
    private List<PlanetaEntity> planetaList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PlanetaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SistemaSolar").executeUpdate();
    }

    private void insertData() {
        sistemaSolar = factory.manufacturePojo(SistemaSolar.class);
        sistemaSolar.setRatio_fuerza(0.5);
        sistemaSolar.setNum_stormtroopers(2000);
        entityManager.persist(sistemaSolar);

        for (int i = 0; i < 3; i++) {
            PlanetaEntity entity = factory.manufacturePojo(PlanetaEntity.class);
            entityManager.persist(entity);
            planetaList.add(entity);
        }
    }

    @Test
    void testAddPlaneta() throws EntityNotFoundException, IllegalOperationException {
        PlanetaEntity newPlaneta = factory.manufacturePojo(PlanetaEntity.class);
        newPlaneta.setPoblacion(1000); // Ajustar la población para cumplir con el ratio
        entityManager.persist(newPlaneta);

        PlanetaEntity result = sistemaSolarPlanetaService.addPlaneta(sistemaSolar.getId(), newPlaneta.getId());

        assertNotNull(result);
        assertEquals(sistemaSolar.getId(), result.getSistemaSolar().getId());
    }

    @Test
    void testAddPlanetaInvalidSistemaSolar() {
        PlanetaEntity planeta = planetaList.get(0);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            sistemaSolarPlanetaService.addPlaneta(0L, planeta.getId());
        });
        assertEquals("No se encontró el sistema solar con el id dado", exception.getMessage());
    }

    @Test
    void testAddPlanetaInvalidPlaneta() {
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            sistemaSolarPlanetaService.addPlaneta(sistemaSolar.getId(), 0L);
        });
        assertEquals("No se encontró el planeta con el id dado", exception.getMessage());
    }

    @Test
    void testAddPlanetaInvalidRatio() {
        PlanetaEntity newPlaneta = factory.manufacturePojo(PlanetaEntity.class);
        newPlaneta.setPoblacion(5000);
        entityManager.persist(newPlaneta);

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            sistemaSolarPlanetaService.addPlaneta(sistemaSolar.getId(), newPlaneta.getId());
        });

        assertEquals("El ratio actual no es suficiente para agregar el planeta al sistema solar", exception.getMessage());
    }
}