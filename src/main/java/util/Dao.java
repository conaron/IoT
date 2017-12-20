package util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import model.Aluno;
import model.Objeto;

public class Dao {

    private static final String UNIDADE = "PU";

    private static Dao instance;
    private static EntityTransaction transacao;
    protected EntityManager entidadeM;

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    private Dao() {
        entidadeM = getEntidadeM();
    }

    private EntityManager getEntidadeM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIDADE);
        if (entidadeM == null) {
            entidadeM = factory.createEntityManager();
        }
        return entidadeM;
    }

    public Object buscaPorId(Object objeto) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return entidadeM.find(objeto.getClass(),
                (int) objeto.getClass().getMethod("getId").invoke(objeto));
    }

    public boolean existe(Object objeto) {
        String classe = objeto.getClass().getSimpleName().toLowerCase();
        String query = "select tablename from pg_catalog.pg_tables where tablename = '" + classe + "'";

        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {
            return true;
        }
        return false;
    }

    public boolean existe(String tabelaDinamica) {
        String query = "select tablename"
                + " from pg_catalog.pg_tables"
                + " where tablename = 'objeto_" + tabelaDinamica.toLowerCase() + "'";
        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {

            return true;
        }
        return false;
    }

    public String desespero(Object objeto) throws SQLException {
        String classe = objeto.getClass().getSimpleName().toLowerCase();
        String query = "select tablename from pg_catalog.pg_tables where tablename = '" + classe + "'";

        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {
            return " ";
        }
        return " não ";
    }

    public List<Object> busca(String query) {
        List<Object> lista = new ArrayList();
        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {
            lista.add(t);
        }
        if (lista.isEmpty()) {
            return null;
        }
        return lista;
    }

    public List<Object> busca(String query, Class classe) {
        List<Object> lista = new ArrayList();
        for (Object t : entidadeM.createNativeQuery(query, classe).getResultList()) {
            lista.add(t);
        }
        if (lista.isEmpty()) {
            return null;
        }
        return lista;
    }

    public Object[] buscaRegistro(String query) {
        Query q = entidadeM.createNativeQuery(query);
        try {
            return (Object[]) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Object buscaRegistro(String query, Class classe) {
        Query q = entidadeM.createNativeQuery(query, classe);
        return q.getSingleResult();
    }

    public List<Aluno> busca_alunos(Objeto objeto) {
        String tabela = "Objeto_" + objeto.getNome();
        String query = ""
                + "select * from aluno"
                + " where id in (select aluno from " + tabela + ")"
                + " order by nome";

//        List<Aluno> lista = new ArrayList();
        List<Aluno> lista = (List<Aluno>) entidadeM.createNativeQuery(query, Aluno.class).getResultList();

//        for (Object t : entidadeM.createNativeQuery(query, Aluno.class).getResultList()) {
//            System.out.println(t.toString());
////            lista.add((Aluno) t);
//        }
        if (lista.isEmpty()) {
            return null;
        }
        return lista;
    }

    public List<Object> busca(Object objeto, int nivel) {
        if (existe(objeto)) {
            return entidadeM.createNamedQuery(objeto.getClass().getSimpleName() + "." + nivel).getResultList();
        }
        return null;
    }

    @SuppressWarnings("unchecked")

    public String insere(Object objeto) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIDADE);
        entidadeM = factory.createEntityManager();
        try {
            entidadeM.getTransaction().begin();
            entidadeM.persist(objeto);
            entidadeM.getTransaction().commit();
//            entidadeM.close();
            return "Inserido com sucesso";
        } catch (Exception ex) {
            if (entidadeM.getTransaction().isActive()) {
                entidadeM.getTransaction().rollback();
//                entidadeM.close();
//                entidadeM = getEntidadeM();
            }
            String mensagem;
            mensagem = "Dao - " + ex.toString();

//            try {
//                mensagem = ex.getCause().getCause().getCause().getMessage();
//            } catch (Exception e) {
//                try {
//                    mensagem = e.getCause().getCause().getMessage();
//                } catch (Exception exx) {
//                    mensagem = exx.getCause().getMessage();
//                }
//            }
            System.out.println(""
                    + ""
                    + mensagem
                    + ""
                    + "");
            entidadeM.clear();
            return mensagem;

//            String[] campo = mensagem
//                    .substring(mensagem.indexOf(" (") + 1, mensagem.indexOf(") ") + 1)
//                    .split("=");
//            campo[0] = campo[0].replaceAll("\\(|\\)", "");
//            return "O " + campo[0] + " " + campo[1] + " já está registrado no sistema;";
        }
    }

    public String altera(Object objeto) {
        try {
            entidadeM.getTransaction().begin();
            entidadeM.merge(objeto);
            entidadeM.getTransaction().commit();
            return "Alterado com sucesso";
        } catch (Exception ex) {
            if (entidadeM.getTransaction().isActive()) {
                entidadeM.getTransaction().rollback();
            }
            String mensagem = ex.getCause().getCause().getCause().getMessage();
            String[] campo = mensagem
                    .substring(mensagem.indexOf(" (") + 1, mensagem.indexOf(") ") + 1)
                    .split("=");
            campo[0] = campo[0].replaceAll("\\(|\\)", "");
            return "O " + campo[0] + " " + campo[1] + " já está registrado no sistema;";
        }
    }

    public String remove(Object objeto) {
        transacao = entidadeM.getTransaction();

        try {
            Object aRemover = buscaPorId(objeto);
            transacao.begin();
            entidadeM.remove(aRemover);
            transacao.commit();
            return "Removido com sucesso";
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            return "Registro não excluido";
        }
    }
}
