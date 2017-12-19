package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Ton
 */
@Entity

public class Aluno extends Pessoa implements Serializable {

    @Column
    private String responsavel;

    @Column
    private String nivelParentesco;

    @Column
    private String email;

    @Column
    private String rfid;

    public Aluno() {
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getNivelParentesco() {
        return nivelParentesco;
    }

    public void setNivelParentesco(String nivelParentesco) {
        this.nivelParentesco = nivelParentesco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

}
