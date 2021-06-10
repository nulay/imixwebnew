package by.imix.cms;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

import javax.persistence.EntityManagerFactory;
//@Configuration
public class SessionFactConf {


//    @Bean(name = "cmsSessionFactory")

//    public HibernateJpaSessionFactoryBean cmsSessionFactory(EntityManagerFactory entityManagerFactory)
//    {
//        HibernateJpaSessionFactoryBean hibernateJpaSessionFactoryBean = new HibernateJpaSessionFactoryBean();
//        hibernateJpaSessionFactoryBean.setEntityManagerFactory(entityManagerFactory);
//        return hibernateJpaSessionFactoryBean;
//    }
//    public SessionFactory cmsSessionFactory( @Autowired EntityManagerFactory factory) {
//        if(factory.unwrap(SessionFactory.class) == null){
//            throw new NullPointerException("factory is not a hibernate factory");
//        }
//        return factory.unwrap(SessionFactory.class);
//    }
}
