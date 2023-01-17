package io.springbatch.practice.chunk.test2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        log.info("process execute.. item=>{} ", item);
        item.setName(item.getName().toUpperCase());
        return item;
    }
}
