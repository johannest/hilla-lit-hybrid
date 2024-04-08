package com.example.application.services;

import com.example.application.data.SamplePerson;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import dev.hilla.exception.EndpointException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class SamplePersonEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(SamplePersonEndpoint.class);

    private final SamplePersonService service;

    public SamplePersonEndpoint(SamplePersonService service) {
        this.service = service;
    }

    public Page<SamplePerson> list(Pageable page) {
        return service.list(page);
    }

    public Optional<SamplePerson> get(Long id) {
        return service.get(id);
    }

    public SamplePerson update(SamplePerson entity) {
        try {
            logger.info("Sample person {} updated at the endpoint", entity.getId());
            return service.update(entity);
        } catch (OptimisticLockingFailureException e) {
            throw new EndpointException("Somebody else has updated the data while you were making changes.");
        }
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public int count() {
        return service.count();
    }

}
