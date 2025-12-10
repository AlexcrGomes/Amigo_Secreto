package com.amigosecreto.amigo_secreto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @JsonIgnore
    private Evento evento;


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public Evento getEvento() { return evento; }

    public void setEvento(Evento evento) { this.evento = evento; }
}
