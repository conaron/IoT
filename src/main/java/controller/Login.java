/*
 * Esta classe é responsável pela validação do usuário ao entrar no sistema.
 */
package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.Adm;

import org.primefaces.context.RequestContext;

@ManagedBean

public class Login {

    private Object usuario;
    private String barra;

    public Object getUsuario() {
        return usuario;
    }

    public void setUsuario(Object usuario) {
        this.usuario = usuario;
    }

    public String getBarra() {
        return barra;
    }

    public void setBarra(String barra) {
        this.barra = barra;
    }

    public void login(ActionEvent event) throws NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext request = RequestContext.getCurrentInstance();

        Map<String, String> requestParamMap = context.getExternalContext().getRequestParameterMap();
        String hash = requestParamMap.get("hash");

        String admin = "adminadmin";
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(admin.getBytes(), 0, admin.length());
        admin = new BigInteger(1, m.digest()).toString(16);

        boolean logado = false;
        FacesMessage.Severity nivel = FacesMessage.SEVERITY_FATAL;
        String titulo = "Erro no login";
        String mensagem = "E-mail ou senha inválidos";

        if (hash.equals(admin)) {
            logado = true;
            nivel = FacesMessage.SEVERITY_INFO;
            titulo = "Bem vindo";
            mensagem = "Usuário administativo";
            this.barra = "adm";
            this.usuario = new Adm();
            request.update("barra");
            
            
        } else {
        }
        context.addMessage(null, new FacesMessage(nivel, titulo, mensagem));
        request.addCallbackParam("logado", logado);

    }
}
