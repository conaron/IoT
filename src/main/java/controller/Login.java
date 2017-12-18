/*
 * Esta classe é responsável pela validação do usuário ao entrar no sistema.
 */
package controller;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

@ManagedBean
public class Login {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext request = RequestContext.getCurrentInstance();

        Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
        String hash = requestParamMap.get("hash");

        FacesMessage message = null;
        boolean loggedIn = false;

        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Teste de Hash", "Hash: " + hash);
//        if (username != null && username.equals("admin") && password != null && password.equals("admin")) {
//            loggedIn = true;
//            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
//        } else {
//            loggedIn = false;
//            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
//        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        request.addCallbackParam("loggedIn", loggedIn);
    }
}
