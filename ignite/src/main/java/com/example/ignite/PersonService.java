package com.example.ignite;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repo;
    
    public Person insert(Person p) {
      if (!repo.selectIdFromName(p.getName()).isEmpty()) {
        throw new DataIntegrityViolationException("name " + p.getName() + " already exists");
      }

      return repo.save(p.getId(), p);
    }
}