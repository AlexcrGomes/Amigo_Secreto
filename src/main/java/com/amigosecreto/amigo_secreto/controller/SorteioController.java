package com.amigosecreto.amigo_secreto.controller;

import com.amigosecreto.amigo_secreto.model.Sorteio;
import com.amigosecreto.amigo_secreto.service.EventoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventos/{eventoId}/sorteio")
public class SorteioController {

    private final EventoService eventoService;

    public SorteioController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping("/{participanteId}")
    public ResultadoSorteioResponse sortear(@PathVariable Long eventoId,
                                            @PathVariable Long participanteId) {

        Sorteio sorteio = eventoService.sortearParaParticipante(eventoId, participanteId);

        return new ResultadoSorteioResponse(
                sorteio.getDoador().getId(),
                sorteio.getDoador().getNome(),
                sorteio.getRecebedor().getNome()
        );
    }
}
