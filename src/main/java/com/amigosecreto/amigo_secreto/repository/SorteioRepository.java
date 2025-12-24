package com.amigosecreto.amigo_secreto.repository;

import com.amigosecreto.amigo_secreto.model.Participante;
import com.amigosecreto.amigo_secreto.model.Sorteio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SorteioRepository extends JpaRepository<Sorteio, Long> {

}
