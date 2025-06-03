package org.example.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "jogos")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "animal_id")
    private int animalId;
    private Date dia;
    private String usuario;
    private double valorAposta;

}