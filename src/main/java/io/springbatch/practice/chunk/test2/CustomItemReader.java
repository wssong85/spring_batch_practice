package io.springbatch.practice.chunk.test2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class CustomItemReader implements ItemReader<Customer> {

    private List<Customer> customers;

    public CustomItemReader(List<Customer> customers) {
        this.customers = new ArrayList<>(customers);
    }

    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        log.info("read execute.. item.size=>{} ", customers.size());

        if (!customers.isEmpty()) {

            Customer getCustomer = customers.remove(0);
            log.info("read execute.. removeCustomer={}", getCustomer);
            return getCustomer;
        }

        return null;
    }
}
