package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class MedicoEntity extends BaseEntity {
    private String nombre;
    private String apellido;
    private String registroMedico;

    @PodamExclude
    @ManyToMany
    private List<EspecialidadEntity> especialidades = new ArrayList<>();

}
