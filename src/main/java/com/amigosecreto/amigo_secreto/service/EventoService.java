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

    // Eventos
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

    // Participantes
    @Transactional
    public Participante adicionarParticipante(Long eventoId, String nome, String cpf) {
        Evento evento = buscarEvento(eventoId);

        if (evento.getStatus() != EventStatus.ABERTO) {
            throw new RuntimeException("Evento já está fechado para novos participantes");
        }

        Participante p = new Participante();
        p.setNome(nome);
        p.setCpf(cpf);
        p.setEvento(evento);
        return participanteRepository.save(p);
    }

    public List<Participante> listarParticipantes(Long eventoId) {
        return participanteRepository.findByEventoId(eventoId);
    }

    // Sorteio individual, mas sorteando o evento t odo se ainda não existir
    @Transactional
    public Sorteio sortearParaParticipante(Long eventoId, Long participanteId) {
        Evento evento = buscarEvento(eventoId);

        if (evento.getStatus() != EventStatus.FECHADO) {
            throw new RuntimeException("Evento precisa estar FECHADO para realizar o sorteio");
        }

        // Verifica se já existe sorteio para o participante
        Optional<Sorteio> existente =
                sorteioRepository.findByEventoIdAndDoadorId(eventoId, participanteId);
        if (existente.isPresent()) {
            return existente.get();
        }

        // Se NÃO existe sorteio para o evento ainda, cria um para todos
        List<Sorteio> sorteiosEvento = sorteioRepository.findByEventoId(eventoId);
        if (sorteiosEvento.isEmpty()) {
            gerarSorteioCompleto(eventoId);
        }

        // Agora deve existir
        return sorteioRepository.findByEventoIdAndDoadorId(eventoId, participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado no evento ou erro no sorteio"));
    }

    // Algoritmo do sorteio: cria um embaralhamento em que ninguém tira a si mesmo
    private void gerarSorteioCompleto(Long eventoId) {
        List<Participante> participantes = participanteRepository.findByEventoId(eventoId);

        if (participantes.size() < 2) {
            throw new RuntimeException("É necessário pelo menos 2 participantes para o sorteio");
        }

        List<Participante> destino = new ArrayList<>(participantes);

        Random random = new Random();
        boolean valido = false;

        // Reembaralha enquanto alguém tirou a si mesmo
        while (!valido) {
            Collections.shuffle(destino, random);
            valido = true;
            for (int i = 0; i < participantes.size(); i++) {
                if (participantes.get(i).getId().equals(destino.get(i).getId())) {
                    valido = false;
                    break;
                }
            }
        }

        // Salva os sorteios no banco
        for (int i = 0; i < participantes.size(); i++) {
            Participante doador = participantes.get(i);
            Participante recebedor = destino.get(i);

            Sorteio s = new Sorteio();
            s.setEvento(doador.getEvento());
            s.setDoador(doador);
            s.setRecebedor(recebedor);
            sorteioRepository.save(s);
        }
    }
}
