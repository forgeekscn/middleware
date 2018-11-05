package cn.forgeeks.springcloud.thread.common;

/**
 * 简单实现自旋锁
 */
public class SelfCircleLock{
    public Object resources=new Object();
    public boolean isAvailable = false;
    public void actionOfThreadA(){
        while(!isAvailable){
            System.out.println("ThreadA : Waitting for lock.");
            try{
                resources.wait();
                System.out.println("ThreadA : Obtained lock.");
            }catch(Exception e){
            }
        }
    }
    public void actionOfThreadB(){
        try{
            resources.notifyAll();
        }catch(Exception e){
        }
        isAvailable = true;
        System.out.println("ThreadB : Notified lock.");
    }
    public static void main(String[] args) throws InterruptedException {
        SelfCircleLock main = new SelfCircleLock();

        MyThreadA t1 = new MyThreadA(main) , t3 = new MyThreadA(main);
        MyThreadB t2 = new MyThreadB(main);
        t1.start();
        t3.start();
        Thread.sleep(1000);
        t2.start();
    } 

}

class MyThreadA extends Thread{
    public SelfCircleLock main;
    public MyThreadA(SelfCircleLock main){
        this.main = main;
    }
    @Override
    public void run(){
        main.actionOfThreadA();
    }
}
class MyThreadB extends  Thread{
    public SelfCircleLock main;
    public MyThreadB(SelfCircleLock main){
        this.main = main;
    }
    @Override
    public void run(){
        main.actionOfThreadB();
    }
}