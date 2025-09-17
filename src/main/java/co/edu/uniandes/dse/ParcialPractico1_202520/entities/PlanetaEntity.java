package co.edu.uniandes.dse.ParcialPractico1_202520.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PlanetaEntity extends BaseEntity {
    private String nombre;
    private double poblacion; //Use double en lugar de int porque la población la estoy calculando en millones
    private double diametro; //Diametro del planeta en KM

    @PodamExclude
    @ManyToOne
    private SistemaSolar sistemaSolar;
}
