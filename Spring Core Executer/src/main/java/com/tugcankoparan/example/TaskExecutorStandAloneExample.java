package com.tugcankoparan.example;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

public class TaskExecutorStandAloneExample {

    public static void main (String... strings) {
        TaskExecutor theExecutor = new SimpleAsyncTaskExecutor();

        theExecutor.execute(new Runnable() {
            @Override
            public void run () {
                System.out.println("running task in thread: "
                                             + Thread.currentThread()
                                                     .getName());
            }
        });

        System.out.println("in main thread: " + Thread.currentThread()
                                                      .getName());

    }
}