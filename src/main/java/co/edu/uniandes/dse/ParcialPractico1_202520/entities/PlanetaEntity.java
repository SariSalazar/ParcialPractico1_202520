package co.edu.uniandes.dse.ParcialPractico1_202520.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class PlanetaEntity extends BaseEntity {
    private String nombre;
    private double poblacion; //Use double en lugar de int porque la población la estoy calculando en millones
    private double diametro; //Diametro del planeta en KM
}
