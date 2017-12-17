/*
 * Esta classe é responsável pela validação do usuário ao entrar no sistema.
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

    public void validacao(ActionEvent event) {
        FacesMessage message = null;

        try {
            hash = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get("hash");
        } catch (Exception e) {
            hash = "";
        }

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", "Airton: " + hash);

        FacesContext.getCurrentInstance().addMessage(null, message);

    }

}
