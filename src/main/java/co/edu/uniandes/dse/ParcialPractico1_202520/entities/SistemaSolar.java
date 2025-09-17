package co.edu.uniandes.dse.ParcialPractico1_202520.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class SistemaSolar extends BaseEntity {

    private String nombre;
    @Enumerated(EnumType.STRING)
    private RegionType region_galaxia;
    private double ratio_fuerza; // Numero de Stormtroopers sobre el total del sistema solar
    private int num_stormtroopers;

    @PodamExclude
    @OneToMany(mappedBy = "sistemaSolar", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PlanetaEntity> planetas = new ArrayList<>();
}
