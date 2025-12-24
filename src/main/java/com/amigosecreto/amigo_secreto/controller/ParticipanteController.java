package com.amigosecreto.amigo_secreto.controller;

import com.amigosecreto.amigo_secreto.model.Participante;
import com.amigosecreto.amigo_secreto.service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/{eventoId}/participantes")
public class ParticipanteController {

    private final EventoService eventoService;

    public ParticipanteController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Participante> listar(@PathVariable Long eventoId) {
        return eventoService.listarParticipantes(eventoId);
    }

    @PostMapping
    public Participante adicionar(@PathVariable Long eventoId,
                                  @RequestBody CriarParticipanteRequest request) {
        return eventoService.adicionarParticipanteAoEvento(eventoId, request.getCpf());
    }
}
