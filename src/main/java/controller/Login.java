/*
 * Esta classe é responsável pela validação do usuário ao entrar no sistema.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Airton da Rocha Bernardoni
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String hash;

    public Login() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void validacao(AjaxBehaviorEvent event) {
        System.out.println(hash);
    }

}
