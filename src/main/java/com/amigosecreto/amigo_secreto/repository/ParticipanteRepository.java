package com.amigosecreto.amigo_secreto.repository;

import com.amigosecreto.amigo_secreto.model.Evento;
import com.amigosecreto.amigo_secreto.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante,Long> {

    boolean existsByCpf(String cpf);

    List<Participante> findByParticipantesId(Long eventoId);

}
