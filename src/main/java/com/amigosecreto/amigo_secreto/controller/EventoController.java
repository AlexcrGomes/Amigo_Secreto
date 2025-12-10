package com.amigosecreto.amigo_secreto.controller;

import com.amigosecreto.amigo_secreto.model.Evento;
import com.amigosecreto.amigo_secreto.service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin // se quiser chamar de outro domínio
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoService.listarEventos();
    }

    @GetMapping("/{id}")
    public Evento buscar(@PathVariable Long id) {
        return eventoService.buscarEvento(id);
    }

    @PostMapping
    public Evento criar(@RequestBody CriarEventoRequest request) {
        return eventoService.criarEvento(request.getNome());
    }

    @PostMapping("/{id}/fechar")
    public Evento fechar(@PathVariable Long id) {
        return eventoService.fecharEvento(id);
    }
}
