package co.edu.uniandes.dse.parcialprueba.services;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {
    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity");
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
    }

    private void insertData() {
        EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidadEntity);

        MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medicoEntity);

    }

    @Test
    void  testAddEspecialidad() throws IllegalOperationException, EntityNotFoundException{
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(newEntity);

        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);

        EspecialidadEntity result= medicoEspecialidadService.addEspecialidad(newEntity.getId(), especialidad.getId());
        assertNotNull(result);
    }

    @Test
    void  testAddEspecialidadNoMedico() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, ()->{
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);
        medicoEspecialidadService.addEspecialidad(0L, especialidad.getId());
        });
    }

    @Test
    void  testAddEspecialidadNoEspecialista() throws EntityNotFoundException{
        assertThrows(EntityNotFoundException.class, ()->{
        MedicoEntity medico = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medico);
        medicoEspecialidadService.addEspecialidad(medico.getId(), 0L);
        });
    }

}
