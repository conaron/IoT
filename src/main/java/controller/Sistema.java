/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.Adm;
import model.Professor;
import org.primefaces.context.RequestContext;
import util.Dao;

/**
 *
 * @author Ton
 */
@ManagedBean
@SessionScoped

public class Sistema {

    private String situacao;
    private Object usuario;
    private Object barra;
    private String area;

    public Sistema() {

    }

    @PostConstruct
    public void init() {
        if (!(this.situacao instanceof String)) {
            this.situacao = "login";
        }
    }

    public Object getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Object usuario) {
        this.usuario = usuario;
    }

    public Object getBarra() {
        return this.barra;
    }

    public void setBarra(Object barra) {
        this.barra = barra;
    }

    public String getSituacao() {
        Dao.getInstance();
        return this.situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getArea() {
        return this.area;
    }

    public void defineArea(String area) {
        this.area = area;
        System.out.println(this.area);
    }

    public void logout() {
        situacao = "login";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void login(ActionEvent event) throws NoSuchAlgorithmException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String query;
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
            this.barra = "professor";
            this.usuario = new Adm();
            request.update("barra");
        } else {
            query = "select zona,id"
                    + " from"
                    + "	("
                    + "	select 1 zona, id, email, md5(email||senha) hash from adm"
                    + "	union all"
                    + "	select 2 zona, id, email, md5(email||senha) hash from professor"
                    + "	) as t"
                    + " where"
                    + "	hash = '" + hash + "';";
            Object[] retorno = Dao.getInstance().buscaRegistro(query);
            if (retorno != null) {
                if ((int) retorno[0] == 1) {
                    this.barra = "adm";
                    this.usuario = (Adm) Dao.getInstance().buscaRegistro(
                            "select * from adm where id = " + retorno[1], Adm.class);
                } else {
                    this.barra = "professor";
                    this.usuario = (Professor) Dao.getInstance().buscaRegistro(
                            "select * from professor where id = " + retorno[1], Professor.class);
                }
                logado = true;
                nivel = FacesMessage.SEVERITY_INFO;
                titulo = "Bem vindo";
                mensagem = this.usuario.getClass().getMethod("getNome").invoke(this.usuario).toString();
                request.update("barra");
            }
        }
        context.addMessage(null, new FacesMessage(nivel, titulo, mensagem));
        request.addCallbackParam("logado", logado);

    }
}
