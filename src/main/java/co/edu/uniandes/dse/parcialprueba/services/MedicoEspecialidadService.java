package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadesRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {
    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    EspecialidadesRepository especialidadesRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException{
        log.info("Inicia proceso de asociarle una especialidad al medico con id = {}", medicoId);
        Optional <EspecialidadEntity> especialidadEntity = especialidadesRepository.findById(especialidadId);
        if (especialidadEntity.isEmpty())
            throw new EntityNotFoundException("No se encontro la especialidad");
        Optional <MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        if (medicoEntity.isEmpty())
            throw new EntityNotFoundException("No se encontro el medico");
        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Termina proceso de asociarle una especialidad al medico con id = {}", medicoId);
        return especialidadEntity.get();
    }

}
