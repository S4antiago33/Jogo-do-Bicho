package org.example.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "animais")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
}
