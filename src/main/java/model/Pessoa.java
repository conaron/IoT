/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ton
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Pessoa {

    @Id
    @GeneratedValue(generator = "pk_pessoa")
    @GenericGenerator(name = "pk_pessoa", strategy = "increment")
    private int id;
    @Column(nullable = false)
    private String cpf;
    @Column
    private String nome;
    @Column
    private String fone;
    @Column
    private String uf;
    @Column
    private String cidade;
    @Column
    private String logradouro;
    @Column
    private int numero;

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        if ((cidade == null) || (cidade.equals(""))) {
            cidade = "Porto Alegre";
        }
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
