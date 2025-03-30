package com.bap.intern.shopee.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TestEventListener2 {

    @EventListener
    public void listener(TestEvent event) throws InterruptedException {
        System.out.println("this is listener 2222-------------------------------------");
        Thread.sleep(3000);
        System.out.println("wake up 2");
        System.out.println(event.category().getName());
        System.out.println("sua category");
        event.category().setName("sua lan 222");
        System.out.println(event.category().getName());
    }
}
