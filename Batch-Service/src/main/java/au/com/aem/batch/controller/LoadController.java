package au.com.aem.batch.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class LoadController {

	/*
	 * Acts as trigger point to initiate the batch process
	 * Also we need a Job laucher and job to be supplied during trigger
	 * We have created the Job; JobLauncher is created by the framework itself
	 */
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@GetMapping("/load")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps); // we can pass additional info using parameters
															// Timestamp, in this case
		JobExecution jobExecution = jobLauncher.run(job, parameters); 
		System.out.println("Job Execution : " + jobExecution.getStatus());
		
		System.out.println("Batch is running ..");
		while(jobExecution.isRunning()) {
			System.out.println("...");
		}
		System.out.println("Batch completed ..");
		
		return jobExecution.getStatus();
	}
	
	// We can connect to H2 database from the webpage using url
	// http://localhost:8500/h2-console
	// http://localhost:8500/actuator/health will show if application is up
}
