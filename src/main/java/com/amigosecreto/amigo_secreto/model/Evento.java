package com.amigosecreto.amigo_secreto.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.ABERTO;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participante> participantes = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public EventStatus getStatus() { return status; }

    public void setStatus(EventStatus status) { this.status = status; }

    public List<Participante> getParticipantes() { return participantes; }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }
}