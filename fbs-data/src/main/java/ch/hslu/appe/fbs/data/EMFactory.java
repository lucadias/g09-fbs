package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.model.entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {



        public static void main(String args[]){

        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("luca");

        EntityManager entitymanager = emfactory.createEntityManager();

        entitymanager.getTransaction().begin();
        Article article= entitymanager.find(Article.class, 0);



        //entitymanager.persist( article );
        //entitymanager.getTransaction( ).commit( );

        entitymanager.close();
        emfactory.close();


    }

}
