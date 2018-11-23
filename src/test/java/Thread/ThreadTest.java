package Thread;

/**
 * @author xuyue_2017@csii.com.cn
 * @ClassName: ThreadTest
 * @Description:
 * @date 2018/11/10 16:32
 * Copyright (c) CSII.
 * All Rights Reserved.
 */


public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开始");
            }
        };
        t1.start();
        t1.join();
        System.out.println("结束");
        //t1.interrupt();
    }
}
