package tech.octopusdragon.creaturecreator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tech.octopusdragon.creaturecreator.model.Creature;

public interface CreatureRepository extends CrudRepository<Creature, Long>, PagingAndSortingRepository<Creature, Long> { }
