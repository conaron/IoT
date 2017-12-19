package model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Ton
 */
@Entity
@ManagedBean

public class Adm extends Usuario implements Serializable {

    @Column
    private String cargo;

    public Adm() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
