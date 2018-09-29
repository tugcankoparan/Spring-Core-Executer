package com.tugcankoparan.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AsyncTaskExecutorExample {
    public static void main (String[] args) throws Exception {
        ApplicationContext context =
                  new AnnotationConfigApplicationContext(MyConfig.class);
        MyBean bean = context.getBean(MyBean.class);
        bean.runTasks();
    }
    @Configuration
    public static class MyConfig {
        @Bean
        MyBean myBean () {
            return new MyBean();
        }

        @Bean
        AsyncTaskExecutor taskExecutor () {
            SimpleAsyncTaskExecutor t = new SimpleAsyncTaskExecutor();
            t.setConcurrencyLimit(100);
            return t;
        }
    }

    private static class MyBean {
        @Autowired
        private AsyncTaskExecutor executor;

        public void runTasks () throws Exception {
            List<Future<?>> futureList = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Future<?> future = executor.submit(getTask(i));
                futureList.add(future);
            }

            for (Future<?> future : futureList) {
                System.out.println(future.get());
            }
        }

        private Callable<String> getTask (int i) {
             return () -> {
                System.out.printf("running task %d. Thread: %s%n",
                                  i,
                                  Thread.currentThread().getName());
                 return String.format("Task finished %d", i);
            };
        }
    }
}