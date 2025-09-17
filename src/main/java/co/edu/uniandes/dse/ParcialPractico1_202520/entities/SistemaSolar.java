package co.edu.uniandes.dse.ParcialPractico1_202520.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Data
@Entity
public class SistemaSolar extends BaseEntity {

    private String nombre;
    @Enumerated(EnumType.STRING)
    private String region_galaxia; //CORE, MID_RIM, OUTER_RIM, COLONIES, WILD_SPACE
    private double ratio_fuerza; // Numero de Stormtroopers sobre el total del sistema solar
    private int num_stormtroopers;
}
