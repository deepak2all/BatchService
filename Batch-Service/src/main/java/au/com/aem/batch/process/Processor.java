package au.com.aem.batch.process;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import au.com.aem.batch.model.Participant;

@Component
public class Processor implements ItemProcessor<Participant, Participant>{

	//This map is created to persist a different (but processed data in the DB)
	private static final Map<String, String> PARTICIPANT_STATUS = new HashMap<>();
	
	public Processor() {
		PARTICIPANT_STATUS.put("A", "ACTIVE");
		PARTICIPANT_STATUS.put("I", "INACTIVE");
		PARTICIPANT_STATUS.put("P", "PENDING");
	}
	
	@Override
	public Participant process(Participant item) throws Exception {
		String statusCode = item.getStatus();
		String status = PARTICIPANT_STATUS.get(statusCode); // Transforming using processor
		item.setStatus(status);
		item.setTime(new Date());
		System.out.println(String.format("Converted from [%s] to [%s]", statusCode, status));
		return item;
	}

}
