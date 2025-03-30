package com.bap.intern.shopee.batch.adminSendEmailBatch;

import com.bap.intern.shopee.entity.SendEmailBatch;
import com.bap.intern.shopee.entity.User;
import com.bap.intern.shopee.entity.User.Role;
import com.bap.intern.shopee.repository.SendEmailBatchRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminSendEmailToUserBatchConfiguration extends DefaultBatchConfiguration {
	private final EntityManagerFactory entityManagerFactory;
    private final MailSender mailSender;

    @Bean
    public Job adminSendEmailToUserJob(JobRepository jobRepository, Step adminSendEmailStep,
            @Qualifier("sendEmailBatchlistener") JobExecutionListener sendEmailBatchlistener) {
        return new JobBuilder("adminSendEmailToUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
				.listener(sendEmailBatchlistener)
                .flow(adminSendEmailStep)
                .end()
                .build();
    }

    public ItemReader<User> reader() {
    	log.info("AdminSendEmailToUserBatch: reading users from db ...--------------");
    	Map<String, Object> parameterValues = new HashMap<>();
	    parameterValues.put("role", Role.CUSTOMER);
        return new JpaPagingItemReaderBuilder<User>()
                .name("getUsers")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
                .parameterValues(parameterValues)
                .build();
    }

    @Bean
    @StepScope
	public ItemWriter<User> writer(@Value("#{jobParameters['subject']}") String subject,
			@Value("#{jobParameters['text']}") String text) {
		return users -> {
	    	log.info("AdminSendEmailToUserBatch: sending email to users...--------------");
	    	log.info("subject: " + subject);
	    	log.info("text: " + text);
            for (User user : users) {
            	String emailAdress = user.getEmail();
            	log.info("Sending email to user: " + emailAdress + "---------------");

            	SimpleMailMessage email = new SimpleMailMessage();
                email.setTo(emailAdress);
                email.setSubject(subject);
                email.setText(text);
                mailSender.send(email);
            }
        };
	}

	@Bean
	public Step adminSendEmailStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<User> writer) {
		return new StepBuilder("adminSendEmailStep", jobRepository)
				.<User, User>chunk(10, transactionManager)
				.reader(reader())
				.writer(writer)
				.build();
	}

	@Bean
    @StepScope
	public JobExecutionListener sendEmailBatchlistener(@Value("#{jobParameters['id']}") String id,
										SendEmailBatchRepository sendEmailBatchRepository) {
		return new JobExecutionListener() {
			@Override
			public void beforeJob(JobExecution jobExecution) {
				log.info("Begin excecuting adminSendEmailToUserBatch");
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
					int sendEmailBatchId = Integer.parseInt(id);
					SendEmailBatch sendEmailBatch = sendEmailBatchRepository.findById(sendEmailBatchId).get();
					sendEmailBatch.setStatus("COMPLETED");
					sendEmailBatchRepository.save(sendEmailBatch);
				}
			}
		};
	}
}
