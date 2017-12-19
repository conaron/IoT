/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javax.faces.bean.ManagedBean;

@ManagedBean
/**
 *
 * @author Ton
 */
public class Rotas {

    public static String BASE;
    public static String WEB;
    public static String JAVA;
    public static String CLASSES;
    public static String JARS;
    public final static String S = System.getProperty("file.separator");
    public final static String N = System.getProperty("line.separator");

    private static Object usuario;

    public static Object getUsuario() {
        return usuario;
    }

    public static void setUsuario(Object usuario) {
        Rotas.usuario = usuario;
    }

    public static void init() {

//        FacesContext context = FacesContext.getCurrentInstance();
//        Rotas.BASE = context.getExternalContext().getRealPath("index.xhtml");
        Rotas.BASE = System.getenv("CATALINA_WEB") + Rotas.S + "index.xhtml";
        File arquivo = new File(Rotas.BASE);
        arquivo = new File(arquivo.getParent());
        Rotas.BASE = arquivo.getPath();
        Rotas.CLASSES = Rotas.BASE + Rotas.S + "WEB-INF" + Rotas.S + "classes";
        Rotas.JARS = Rotas.BASE + Rotas.S + "WEB-INF" + Rotas.S + "lib";
    }

}
