package com.jacob.myspringtrial;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyTrialRequest {




    private static final String hint = "this is a test,%s";
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/trial")
    public String myReturn(
        @RequestParam(value = "name", defaultValue = "nothing provided") String name){
        //return String.format(hint,name),counter.incrementAndGet();
        return "this a placeholder";
    }
}