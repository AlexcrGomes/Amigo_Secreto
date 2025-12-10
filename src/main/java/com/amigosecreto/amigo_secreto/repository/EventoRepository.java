package com.amigosecreto.amigo_secreto.repository;

import com.amigosecreto.amigo_secreto.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
