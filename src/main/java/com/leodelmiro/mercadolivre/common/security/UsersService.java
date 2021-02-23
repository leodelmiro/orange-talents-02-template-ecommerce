package com.leodelmiro.mercadolivre.common.security;

import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${security.username-query}")
    private String query;

    private static Logger log = LoggerFactory.getLogger(UsersService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<?> objects = entityManager.createQuery(query).setParameter("username", username).getResultList();
        Assert.isTrue(objects.size() <= 1, "[BUG] mais de um autenticável tem o mesmo username " + username);
        if (objects.size() <= 0)
            throw new UsernameNotFoundException("Não foi possível encontrar usuário com email: " + username);

        log.info("User encontrado: " + username);

        return (UserDetails) objects.get(0);
    }
}
