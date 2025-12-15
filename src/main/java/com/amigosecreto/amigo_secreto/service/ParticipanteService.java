package com.amigosecreto.amigo_secreto.service;

import com.amigosecreto.amigo_secreto.model.EventStatus;
import com.amigosecreto.amigo_secreto.model.Evento;
import com.amigosecreto.amigo_secreto.model.Participante;
import com.amigosecreto.amigo_secreto.model.Sorteio;
import com.amigosecreto.amigo_secreto.repository.EventoRepository;
import com.amigosecreto.amigo_secreto.repository.ParticipanteRepository;
import com.amigosecreto.amigo_secreto.repository.SorteioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class ParticipanteService {
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final SorteioRepository sorteioRepository;

    public ParticipanteService(EventoRepository eventoRepository,
                               ParticipanteRepository participanteRepository,
                               SorteioRepository sorteioRepository) {
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
        this.sorteioRepository = sorteioRepository;
    }


    }


}
