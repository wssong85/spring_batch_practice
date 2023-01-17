package io.springbatch.practice.chunk.test2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


@Slf4j
public class CustomItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> items) throws Exception {
      log.info("write execute.. items=>{} ", items);
    }
}
