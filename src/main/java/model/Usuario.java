package model;

import javax.persistence.*;

/**
 * @author Ton
 */
@Entity
abstract class Usuario extends Pessoa {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    public Usuario() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
