package com.amigosecreto.amigo_secreto.controller;

import com.amigosecreto.amigo_secreto.model.Evento;
import com.amigosecreto.amigo_secreto.service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = { RequestMethod.GET, RequestMethod.POST }
)
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/lista")
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

    @PostMapping("/{id}/sortear")
    public Evento sortear(@PathVariable Long id) {
        return eventoService.sortearEvento(id);
    }
}
