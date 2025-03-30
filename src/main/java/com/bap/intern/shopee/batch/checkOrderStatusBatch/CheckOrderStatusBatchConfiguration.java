package com.bap.intern.shopee.batch.checkOrderStatusBatch;

import com.bap.intern.shopee.entity.Order;
import com.bap.intern.shopee.entity.Order.OrderStatus;
import com.bap.intern.shopee.repository.OrderRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CheckOrderStatusBatchConfiguration {
	private final EntityManagerFactory entityManagerFactory;
	private final OrderRepository orderRepository;

	@Bean
	public Job checkOrderStatusJob(JobRepository jobRepository, Step checkStatusOrderStep) {
		return new JobBuilder("checkOrderStatusJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(checkStatusOrderStep)
				.end()
				.build();
	}

    public ItemReader<Order> reader() {
		Date twoDaysAgo = new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
		Map<String, Object> parameterValues = new HashMap<>();
	    parameterValues.put("twoDaysAgo", twoDaysAgo);
	    log.info("reader -------------------");
        return new JpaPagingItemReaderBuilder<Order>()
                .name("getOders")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select o from Order o where o.createdAt <= :twoDaysAgo")
                .parameterValues(parameterValues)
                .build();
    }

	public ItemProcessor<Order, Order> processor() {
		return (order) -> {
				log.info("processor -------------------");
                if (order.getStatus() == OrderStatus.UNACCEPTED) {
                    order.setStatus(OrderStatus.REJECTED);
                }
                return order;
        };
	}

	public ItemWriter<Order> writer() {
		return orders -> {
			log.info("writer -------------------");
            for (Order order : orders) {
                orderRepository.save(order);
            }
        };
	}

	@Bean
	public Step checkStatusOrderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("checkStatusOrderStep", jobRepository)
				.<Order, Order>chunk(10, transactionManager)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	public JobExecutionListener listener() {
		return new JobExecutionListener() {
			@Override
			public void beforeJob(JobExecution jobExecution) {
				log.info("Begin check order status at 12:00 every day =))");
			}
			
			@Override
			public void afterJob(JobExecution jobExecution) {
				if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
					log.info("!!! Check order status finished!");
				}
			}
		};
	}
}
