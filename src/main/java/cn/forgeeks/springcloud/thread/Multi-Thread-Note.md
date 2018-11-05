
### FOCUS POINT

* 什么是线程？
<br>
线程是cpu调度最小单位,一个进程可包含多个线程来协作完成计算任务.

* 什么是线程安全和线程不安全？
<br>
多个线程对同一个变量读写可能会造成脏读数据不一致等并发问题,除非通过加锁达到串行写并行读来保持并发安全.

* 什么是自旋锁？
<br>
一个线程一直尝试自旋获取某个对象的锁例如调用wait,期间不释放cpu若不采取措施处理会浪费cpu资源
<br>
手写一个自旋锁
<br>
```$java
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
```

* 什么是Java内存模型？
<br>
内存模型描述了线程之间如何通过内存进行交互,包括三个区域,heap堆,stack栈,method方法区
<br>
对象实例放在heap,局部变量基本数据类型对象引用放在stack,类信息静态变量常量放在method
<br>
内存模型有三个重要关键字valitile,sycronized,final ,valitile有两种内存语义:可见性和有序性
<br>
编译器优化过程可能会打乱指令原本顺序,用valitile修饰的变量操作会拒绝指令重排序
<br>
另外用valitile修饰的变量会刷到主存,每个线程的本地内存会在访问该变量时先去主存拉取数据,这样能保证该变量对所有线程都具有一致性

* 什么是CAS？
<br>
也就是compare and swap 顾名思义,先比较实际取到的值是否和预期的相等,是就赋值否则失败
<br>
他是乐观锁,优点是所有的读改写操作都是原子操作,由汇编指令lock实现,适合冲突少的场景
<br>
缺点是如果值多次改变后与原来值一样,compare时候会认定他没有变,从而执行覆盖操作,这样会带来并发问题,思路是用版本号
<br>
另外还有cas自旋开销大,在锁竞争激烈情况下会带来严重cpou消耗
<br>
最后是cas只能保证一个共享变量的原子操作

* 什么是乐观锁和悲观锁？


什么是AQS？
什么是原子操作？在Java Concurrency API中有哪些原子类(atomic classes)？
什么是Executors框架？
什么是阻塞队列？如何使用阻塞队列来实现生产者-消费者模型？
什么是Callable和Future?
什么是FutureTask?
什么是同步容器和并发容器的实现？
什么是多线程？优缺点？
什么是多线程的上下文切换？
ThreadLocal的设计理念与作用？
ThreadPool（线程池）用法与优势？
Concurrent包里的其他东西：ArrayBlockingQueue、CountDownLatch等等。
synchronized和ReentrantLock的区别？
Semaphore有什么作用？
Java Concurrency API中的Lock接口(Lock interface)是什么？对比同步它有什么优势？
Hashtable的size()方法中明明只有一条语句”return count”，为什么还要做同步？
ConcurrentHashMap的并发度是什么？
ReentrantReadWriteLock读写锁的使用？
CyclicBarrier和CountDownLatch的用法及区别？
LockSupport工具？
Condition接口及其实现原理？
Fork/Join框架的理解?
wait()和sleep()的区别?
线程的五个状态（五种状态，创建、就绪、运行、阻塞和死亡）?
start()方法和run()方法的区别？
Runnable接口和Callable接口的区别？
volatile关键字的作用？
Java中如何获取到线程dump文件？
线程和进程有什么区别？
线程实现的方式有几种（四种）？
高并发、任务执行时间短的业务怎样使用线程池？并发不高、任务执行时间长的业务怎样使用线程池？并发高、业务执行时间长的业务怎样使用线程池？
如果你提交任务时，线程池队列已满，这时会发生什么？
锁的等级：方法锁、对象锁、类锁?
如果同步块内的线程抛出异常会发生什么？
并发编程（concurrency）并行编程（parallellism）有什么区别？
如何保证多线程下 i++ 结果正确？
一个线程如果出现了运行时异常会怎么样?
如何在两个线程之间共享数据?
生产者消费者模型的作用是什么?
怎么唤醒一个阻塞的线程?
Java中用到的线程调度算法是什么
单例模式的线程安全性?
线程类的构造方法、静态块是被哪个线程调用的?
同步方法和同步块，哪个是更好的选择?
如何检测死锁？怎么预防死锁？