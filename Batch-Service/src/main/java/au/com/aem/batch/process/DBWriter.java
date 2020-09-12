package au.com.aem.batch.process;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.aem.batch.model.Participant;
import au.com.aem.batch.repository.ParticipantRepository;

@Component
public class DBWriter implements ItemWriter<Participant>{

	//Need to inject ParticipantRepository to write to DB
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Override
	public void write(List<? extends Participant> items) throws Exception {
		System.out.println("Data saved for participants : " + items);
		participantRepository.saveAll(items);
	}

}
