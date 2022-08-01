package com.springboot.jettyserver.example.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExampleService extends Thread{

    public int partitionedListSize;
    public Set<String> set;

    @Override
    public void run(){
        this.setPartitionedListSize(set.size()-1);
    }
}
