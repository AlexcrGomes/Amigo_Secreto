package com.amigosecreto.amigo_secreto.repository;

import com.amigosecreto.amigo_secreto.model.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante,Long> {
    List<Participante> findByEventosId(Long eventoId);
}
