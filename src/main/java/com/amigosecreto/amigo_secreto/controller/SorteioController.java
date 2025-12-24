package com.amigosecreto.amigo_secreto.controller;

import com.amigosecreto.amigo_secreto.model.Evento;
import com.amigosecreto.amigo_secreto.model.Sorteio;
import com.amigosecreto.amigo_secreto.service.EventoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos/{eventoId}")
public class SorteioController {

    private final EventoService eventoService;

    public SorteioController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/sortear")
    public ResultadoSorteioResponse sortear(@PathVariable Long eventoId) {
        Evento evento = eventoService.buscarEvento(eventoId);

        Sorteio sorteio = eventoService.sortearEvento(evento.getId());

        return new ResultadoSorteioResponse(
                sorteio.getDoador().getId(),
                sorteio.getDoador().getNome(),
                sorteio.getRecebedor().getNome()
        );
    }
}
