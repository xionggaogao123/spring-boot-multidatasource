package com.qy.controller;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {


    public static void main(String[] args) throws Exception{

        //场景1测试代码块开始
        CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new ComponentService(latch));
            thread.start();
        }

        System.out.println("main thread begin...");
        latch.await(); //使当前线程在锁存器倒计数至零之前一直等待
        System.out.println("main thread end....");
    }

    static class ComponentService implements Runnable {
        private CountDownLatch latch;

        public ComponentService(CountDownLatch latch) {
            super();
            this.latch = latch;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            System.out.println(Thread.currentThread().getName() + " 服务启动成功 !");
            latch.countDown();
        }
    }


}
