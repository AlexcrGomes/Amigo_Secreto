package com.amigosecreto.amigo_secreto.model;

import jakarta.persistence.*;

@Entity
public class Sorteio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Participante doador;    // quem sorteia

    @ManyToOne
    @JoinColumn(name = "recebedor_id")
    private Participante recebedor; // amigo secreto


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Evento getEvento() { return evento; }

    public void setEvento(Evento evento) { this.evento = evento; }

    public Participante getDoador() { return doador; }

    public void setDoador(Participante doador) { this.doador = doador; }

    public Participante getRecebedor() { return recebedor; }

    public void setRecebedor(Participante recebedor) { this.recebedor = recebedor; }
}
