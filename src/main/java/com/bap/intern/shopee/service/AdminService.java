package com.bap.intern.shopee.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.job.JobParametersExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bap.intern.shopee.dto.BaseRes;
import com.bap.intern.shopee.dto.admin.AdminSendEmailReq;
import com.bap.intern.shopee.entity.SendEmailBatch;
import com.bap.intern.shopee.repository.SendEmailBatchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {
	
	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job adminSendEmailToUserJob;
	@Autowired
	SendEmailBatchRepository sendEmailBatchRepository;

	public BaseRes sendEmailToUser(AdminSendEmailReq req) throws JobExecutionException {
		String subject = req.getSubject();
		String text = req.getText();
		
		log.info("In adminService: sendEmailToUser---------------");
		log.info("subject: " + req.getSubject());
		log.info("text: " + req.getText());
		
		SendEmailBatch newBatchRecord = new SendEmailBatch();
		newBatchRecord.setStatus("INPROGRESS");
		sendEmailBatchRepository.save(newBatchRecord);
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("id", Integer.toString(newBatchRecord.getId()))
				.addString("subject", subject)
				.addString("text", text)
				.toJobParameters();

		jobLauncher.run(adminSendEmailToUserJob, jobParameters);
		return new BaseRes(200, "Request send email is running");
	}
}
