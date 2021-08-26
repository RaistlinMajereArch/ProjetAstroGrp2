package astro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import astro.metier.CorpsCeleste;

public interface SystemeRepository extends JpaRepository<CorpsCeleste, Integer>{

}
