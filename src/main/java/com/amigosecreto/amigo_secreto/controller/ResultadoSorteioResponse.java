package com.amigosecreto.amigo_secreto.controller;

public class ResultadoSorteioResponse {
    private Long participanteId;
    private String participanteNome;
    private  String amigoNome;

    public  ResultadoSorteioResponse(Long participanteId, String participanteNome, String amigoNome) {
        this.participanteId = participanteId;
        this.participanteNome = participanteNome;
        this.amigoNome = amigoNome;
    }

    public Long getParticipanteId() { return participanteId; }
    public String getParticipanteNome() { return participanteNome; }
    public String getAmigoNome() { return amigoNome; }

}
