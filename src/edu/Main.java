/*
 * Copyright (c) 2017. Aleksey Eremin
 * 13.01.17 13:01
 */

package edu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
 Многопоточность с двигателем для потоков
 by novel  https://www.youtube.com/watch?v=tVRcT-Ngvsw
 */
public class Main {

    public static void main(String[] args) {
      // write your code here
      // заведем движок для процессов
      ExecutorService es = Executors.newFixedThreadPool(4);
      List<Future<String>> list = new ArrayList<>();
      
      // запуск процессов
      for(int i=0; i<9; i++) {
        Mytask mt = new Mytask("id-"+i);
        System.out.println("                     +++submit "+i);
        Future<String> fu = es.submit(mt);
        System.out.println("                     ---submit "+i);
        list.add(fu);
      }
      
      // получаем результат
      for(Future<String> pr : list) {
        try {
          String otv = pr.get();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          System.out.println("Ошибка в потоке!");
          e.printStackTrace();
        }
      }
  
      // заглушим двигатель потоков!
      es.shutdown();
    }
  
  
}


class Mytask implements Callable<String>
{
  private String f_name;
  
  Mytask(String name)
  {
    f_name=name;
  }
  
  @Override
  public String call()
  {
    //
    System.out.println("Start: "+f_name);
    sleep(3000 + (int)(Math.random()*1000));
    System.out.println(" Stop: "+f_name);
    return f_name;
  }
  
  private static void sleep(int msec)
  {
    try {
      Thread.sleep(msec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
}