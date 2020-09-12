package au.com.aem.batch.config;

import org.springframework.core.io.Resource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.com.aem.batch.model.Participant;

@Configuration
@EnableBatchProcessing //Needed by the JobLauncher to trigger the batch job
public class SpringBatchConfig {

	// Job is built using JobBuilderFactory
	// A Job can have 1 or more steps
	// Each step is built using StepBuilderFactory
	// Also each step has an ItemReader, ItemProcessor and ItemWriter
	// So all these needs to be passed as arguments
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<Participant> itemReader, ItemProcessor<Participant, Participant> itemProcessor, 
			ItemWriter<Participant> itemWriter) {
		
		Step step = stepBuilderFactory.get("ETL-File-Load")
						// Need to specify <Participant, Participant> as it's req for itemProccessor
						// which needs to know the input type it's going to process
						// and also the output type it should return
						.<Participant, Participant> chunk(100)
						.reader(itemReader)
						.processor(itemProcessor)
						.writer(itemWriter)
						.build();
		// Although we've autowired itemReader, itemProcessor and itemWriter
		// We have to provide their implementations
		
		// If there are multiple steps
		/*return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.flow(step1)
				.next(step2)
				.next(step3)
				.build();*/
		
		// If there is only one step
		return jobBuilderFactory.get("ETL-Load")
			.incrementer(new RunIdIncrementer())
			.start(step)
			.build();
		
		// Each step has a reader, processor and writer
		// Each Step is built using StepBuilderFactory
	}
	
	// For reading a csv file, we have an implementation FlatFileItemReader which can be re-used
	// Input passed is a Resource which is autowired using @value
	// "input" needs to be specified in the application.properties
	@Bean
	public FlatFileItemReader<Participant> fileItemReader(@Value("${input}") Resource resource) {
		FlatFileItemReader<Participant> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1); // Since 1st line is column name
		// We also have to map the data obtained from the 2nd line to 
		// the Participant class , using LineMapper
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	@Bean
	protected LineMapper<Participant> lineMapper() {
		DefaultLineMapper<Participant> defaultLineMapper = new DefaultLineMapper<>();
		// Add tokenizer to identify the fields
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setStrict(false);
		// Define the field names that needs to be set
		lineTokenizer.setNames(new String[] {"id", "participantId", "participantName", "participantEmail", "participantPhone", "status"});
		
		//Now Each field needs to be mapped to the respective values via the POJO
		BeanWrapperFieldSetMapper<Participant> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Participant.class);
		
		//Add BeanWrapperFieldSetMapper and DelimitedLineTokenizer to LineMapper
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
	
}
