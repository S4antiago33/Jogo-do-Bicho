package org.example.utils;

import org.example.models.Animal;
import org.example.models.Jogo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static{
        try{
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
                metadataSources.addAnnotatedClass(Animal.class);
                metadataSources.addAnnotatedClass(Jogo.class);
            Metadata metadata = metadataSources.buildMetadata();
            sessionFactory = metadata.buildSessionFactory();
        }catch (Exception e){
            System.out.println("Erro ao iniciar o hibernate" .concat(e.getMessage()).concat("."));
            throw new RuntimeException("Hibernate não foi iniciado.");
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}