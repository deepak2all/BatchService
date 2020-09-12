package au.com.aem.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.aem.batch.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Integer>{

}
