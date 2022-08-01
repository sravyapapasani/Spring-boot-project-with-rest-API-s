package com.springboot.jettyserver.example.contoller;

import com.springboot.jettyserver.example.services.ExampleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/example")
@AllArgsConstructor
public class ExampleController {

    @Autowired
    private final ExampleService exampleService;

    @GetMapping("hello")
    public ResponseEntity<String> firstRequest() {
        System.out.println("hello");
        Set<String> set = new HashSet<>();
        for(int i=0;i<30001;i++){
            set.add(Integer.toString(i));
        }
        int chunk = 500;
//        List<Set<String>> op = exampleService.partition(set,500);
//        System.out.println(op.toString());
//        ExampleService thread = new ExampleService();
//        List<Set<String>> = thread.start();
        List<Set<String>> partitionedList = new ArrayList<>();
        if(set == null || set.isEmpty() || chunk < 1)
            partitionedList = new ArrayList<>();
        else {
            double loopsize = Math.ceil((double) set.size() / (double) chunk);

            for (int i = 0; i < loopsize; i++) {
                partitionedList.add(set.stream().skip((long) i * chunk).limit(chunk).collect(Collectors.toSet()));
            }
        }

        for (Set<String> part: partitionedList){
            ExampleService ex = new ExampleService(500,part);
            ex.start();
            System.out.println(ex.getSet());
            System.out.println(ex.getPartitionedListSize());
        }
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Callable<Integer> callable = new Callable<Integer>() {
//            @Override
//            public Integer call() {
//                return 2;
//            }
//        };
//        Future<Integer> future = executor.submit(callable);
//        System.out.println(partitionedList.get(partitionedList.size()-1).toString());
        return new ResponseEntity<>("body",HttpStatus.OK);
    }

    //As the graceful shutdown period is 10s. This request will give error when the server stops as this request take>10s
    @RequestMapping(value = "secondRequest")
    public ResponseEntity<?> secondRequest() throws Exception{
        Thread.sleep(15000);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Second response.");
    }
}
