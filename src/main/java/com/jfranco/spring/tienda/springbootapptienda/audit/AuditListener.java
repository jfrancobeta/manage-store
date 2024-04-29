package com.jfranco.spring.tienda.springbootapptienda.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        rev entity = (rev) revisionEntity;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            entity.setUsername(authentication.getName());
        } else {
            // Manejar el caso en el que no haya autenticación activa
            entity.setUsername("Sistema");
        }
    }

}
