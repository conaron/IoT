/*
 * Esta classe é responsável pela validação do usuário ao entrar no sistema.
 */
package controller;

import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Airton da Rocha Bernardoni
 */
@Named(value = "login")

public class Login {

    private String hash;

    public Login() {
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void validacao() {
        hash = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hash");
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean logado = false;

        if (hash != null && hash.equals("admin")) {
            logado = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", hash);
        } else {
            logado = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("logado", logado);

    }

}
