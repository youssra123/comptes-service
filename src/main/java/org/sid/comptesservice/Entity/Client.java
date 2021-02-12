package org.sid.comptesservice.Entity;


import lombok.Data;

@Data
public class Client {
    private Long id;
    private String code;
    private String nom;
    private String email;
}
