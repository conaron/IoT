/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ton
 */
@ManagedBean

public class Sistema {

    private String situacao;

    public Sistema() {

    }

    @PostConstruct
    public void init() {
        if (!(this.situacao instanceof String)) {
            this.situacao = "login";
        }
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void logout() {
        situacao = "login";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
