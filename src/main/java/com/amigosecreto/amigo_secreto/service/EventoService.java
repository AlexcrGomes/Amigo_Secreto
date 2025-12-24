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

import java.util.*;

@Service
public class EventoService {
    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final SorteioRepository sorteioRepository;

    public EventoService(EventoRepository eventoRepository,
                         ParticipanteRepository participanteRepository,
                         SorteioRepository sorteioRepository) {
        this.eventoRepository = eventoRepository;
        this.participanteRepository = participanteRepository;
        this.sorteioRepository = sorteioRepository;
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento buscarEvento(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }

    @Transactional
    public Evento criarEvento(String nome) {
        Evento e = new Evento();
        e.setNome(nome);
        e.setStatus(EventStatus.ABERTO);
        return eventoRepository.save(e);
    }

    @Transactional
    public Evento fecharEvento(Long id) {
        Evento e = buscarEvento(id);
        e.setStatus(EventStatus.FECHADO);
        return eventoRepository.save(e);
    }

    @Transactional
    public Evento sortearEvento(Long eventoId) {
        Evento evento = buscarEvento(eventoId);

        List<Participante> participantes = participanteRepository.findByParticipantesId(eventoId);

        if (participantes.size() < 2) {
            throw new RuntimeException("É necessário pelo menos 2 participantes para o sorteio");
        }

        List<Participante> recebedores = new ArrayList<>(participantes);

        Random random = new Random();
        boolean valido = false;

        while (!valido) {
            Collections.shuffle(recebedores, random);
            valido = true;
            for (int i = 0; i < participantes.size(); i++) {
                if (participantes.get(i).getId().equals(recebedores.get(i).getId())) {
                    valido = false;
                    break;
                }
            }
        }

        for (int i = 0; i < participantes.size(); i++) {
            Participante doador = participantes.get(i);
            Participante recebedor = recebedores.get(i);

            Sorteio s = new Sorteio();
            s.setEvento(evento);
            s.setDoador(doador);
            s.setRecebedor(recebedor);
            sorteioRepository.save(s);
        }
    }

    @Transactional
    public Boolean existeCpfCadastrado(String cpf) {
        return participanteRepository.existsByCpf(cpf);
    }

    @Transactional
    public Participante criarParticipante(String nome, String cpf) {

        if (existeCpfCadastrado(cpf)) {
            throw new RuntimeException("CPF já cadastrado");
        }

        Participante p = new Participante();
        p.setNome(nome);
        p.setCpf(cpf);
        return participanteRepository.save(p);
    }

    @Transactional
    public Participante adicionarParticipanteAoEvento(Long eventoId, String cpf) {
        Evento evento = buscarEvento(eventoId);

        if (existeCpfCadastrado(cpf)) {

            if (evento.getStatus() != EventStatus.ABERTO) {
                throw new RuntimeException("Evento não esta aberto para novos participantes");
            }

            Participante p = new Participante();
            p.setCpf(cpf);
            p.getEventos().add(evento);
            return participanteRepository.save(p);
        }
        throw new RuntimeException("Participante não existe");

    }

    @Transactional
    public List<Participante> listarParticipantes(Long eventoId) {
        Evento evento = buscarEvento(eventoId);

        if (evento.getParticipantes().isEmpty()) {
            throw new RuntimeException("Evento não possue participantes");
        }
        return evento.getParticipantes();
    }


}
