package com.daka.restservice.dto;

public class Mensaje {
	
	private String mensaje;
    private String status;

    public Mensaje(String mensaje, String status) {

        this.mensaje = mensaje;
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
