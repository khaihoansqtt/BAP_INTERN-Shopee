package com.bap.intern.shopee.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener {

    @EventListener
    public void listener(TestEvent event) throws InterruptedException {
        System.out.println("this is listener ------------------------------------------");
        Thread.sleep(1000);
        System.out.println("wake up 1");
        System.out.println(event.category().getName());
        System.out.println("sua category");
        event.category().setName("sua lan 1");
        System.out.println(event.category().getName());
    }
}
