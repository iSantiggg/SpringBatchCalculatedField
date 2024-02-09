package com.spring.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.spring.batch.model.Empleado;

@Configuration
@EnableBatchProcessing
public class EmpleadoBatchConfiguration {

	@Autowired
	private StepBuilderFactory stepBuilderF;
	
	@Autowired
	private JobBuilderFactory jobBuilderF;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Empleado> readFromCsv(){
		FlatFileItemReader<Empleado> reader = new FlatFileItemReader<Empleado>();
		//reader.setResource(new FileSystemResource("D:/Descargas/ejemplo1.csv"));
		reader.setResource(new ClassPathResource("ejemplo1.csv"));
		reader.setLineMapper(new DefaultLineMapper<Empleado>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(Empleado.fields());
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Empleado>() {
					{
						setTargetType(Empleado.class);
					}
				});
			}
		});
		return reader;
	}
	@Bean
	public JdbcBatchItemWriter<Empleado> writerIntoDB(){
		JdbcBatchItemWriter<Empleado> writer = new JdbcBatchItemWriter<Empleado>();
		writer.setDataSource(dataSource);
		writer.setSql("insert into csvtodbdatatres (id,nombre,apellido,email,cantidad,totalConIva) values(:id,:nombre,:apellido,:email,:cantidad,:totalConIva)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Empleado>());
		return writer;
	}
	@Bean
	public Step step() {
	    return stepBuilderF.get("step36").<Empleado,Empleado>chunk(10)
	        .reader(readFromCsv())
	        .processor(new ItemProcessor<Empleado, Empleado>() {
	            @Override
	            public Empleado process(Empleado empleado) throws Exception {
	            	double IVA = 0.16;
	                double cantidad = empleado.getCantidad();
	                double totalConIVA = cantidad * (1 + IVA);
	                empleado.setTotalConIva(totalConIVA);
	                return empleado;
	            }
	        })
	        .writer(items -> {
	            for (Empleado item : items) {
	                System.out.println("Item: " + item.toString());
	            }
	            writerIntoDB().write(items); // Write to database after printing
	        })
	        .build();
	}
	@Bean
	public Job job() {
		return jobBuilderF.get("job").flow(step()).end().build();
	}
}