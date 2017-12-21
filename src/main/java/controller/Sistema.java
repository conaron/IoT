/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
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

public class Sistema implements Serializable {

    private String corpo = "login";
    private Object usuario;
    private Object barra;
    private String area;
    private String operacao;

    public Sistema() {

    }

//    Getters e Setters
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

    public String getCorpo() {
        Dao.getInstance();
        return this.corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getArea() {
        return this.area;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

//    Atividades do sistema
    public void sArea(ActionEvent event) {
        this.corpo = "branco";
        HashMap<String, String> destino = new HashMap();
        destino.put("servidores", "adm");
        destino.put("professores", "professor");
        destino.put("alunos", "aluno");
        destino.put("objetos", "objeto");

        String value = event.getComponent().getAttributes().get("value").toString().toLowerCase();

        this.area = destino.get(value);

        RequestContext.getCurrentInstance().update(Arrays.asList("corpo", "area"));
    }

    public void sOperacao(ActionEvent event) {
        this.corpo = "mod_" + this.area;
        String value = event.getComponent().getAttributes().get("value").toString().toLowerCase();
        if ((value.equalsIgnoreCase("cadastrar")) | (value.equalsIgnoreCase("listar"))) {
            this.operacao = value;
        } else {
            this.corpo = "branco";
        }
        RequestContext.getCurrentInstance().update("corpo");
    }

    public void logout() {
        RequestContext request = RequestContext.getCurrentInstance();
        String titulo;
        try {
            titulo = "Adeus " + this.usuario.getClass().getMethod("getNome").invoke(this.usuario).toString();
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            titulo = "Adeus";
        }

        this.corpo = "login";
        this.area = "";
        this.barra = "";
        request.update(Arrays.asList("corpo", "area", "barra", "alerta"));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, titulo, "Obrigado por utilizar nosso sistema."));

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void login(ActionEvent event) throws NoSuchAlgorithmException {
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
            this.usuario = new Adm();
            this.barra = "adm";
            this.corpo = "branco";
            ((Adm) this.usuario).setNome("Usuário administativo");
            mensagem = ((Adm) this.usuario).getNome();
            request.update(Arrays.asList("barra", "corpo"));
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
                logado = true;
                titulo = "Bem vindo";
                nivel = FacesMessage.SEVERITY_INFO;
                this.corpo = "branco";
                if ((int) retorno[0] == 1) {
                    this.barra = "adm";
                    this.usuario = (Adm) Dao.getInstance().buscaRegistro(
                            "select * from adm where id = " + retorno[1], Adm.class);
                    mensagem = ((Adm) this.usuario).getNome();
                } else {
                    this.barra = "professor";
                    this.usuario = (Professor) Dao.getInstance().buscaRegistro(
                            "select * from professor where id = " + retorno[1], Professor.class);
                    mensagem = ((Professor) this.usuario).getNome();
                }
                request.update(Arrays.asList("barra", "corpo"));
            }
        }
        context.addMessage(null, new FacesMessage(nivel, titulo, mensagem));
        request.addCallbackParam("logado", logado);

    }
}
