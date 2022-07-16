package com.example.ignite;

import java.util.List;

import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.Query;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RepositoryConfig(cacheName = "PersonCache")
public interface PersonRepository extends IgniteRepository<Person, Long> {

    public List<Person> findByName(String name);

    @Query("SELECT id FROM Person WHERE name = ?")
    public List<Long> selectIdFromName(String name);
}