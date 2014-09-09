package lab1.main;

import java.util.Date;
import java.util.List;

public class ThreadStarter implements Runnable{

    private static int threadsCount = 0;

    private int id;
    private int priority = 0 ;
    private boolean inCriticalZone = false;
    private boolean workWithResource = false;

    private MyLoggerPanel logger = null; //ToDo: лишняя связь , избавиться
    private Thread thread = null;

    /**
     * работает ли в данный момент времени поток с ресурсом
     */
    public boolean isWorkWithResource() {
        return workWithResource;
    }

    /**
     * выставляет флаг работы с ресурсом
     */
    public void startWorkWithResource() {
        logger.addLog("начинаю работать с ресурсом");
        workWithResource = true;
    }

    /**
     * снимает флаг работы с ресурсом
     */
    public void endWorkWithResource() {
        logger.addLog("освободил ресурс");
        workWithResource = false;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * находиться ли ресурс в критической зоне
     */
    public boolean InCriticalZone() {
        return inCriticalZone;
    }

    /**
     * желает получить доступ к ресурсу , вешает флаг
     */
    public void enterToCriticalZone() {
        logger.addLog("поток №" + id + " хочет войти в критическую зону");
        inCriticalZone = true;
    }

    /**
     * освобождает ресурс, снимает флаг
     */
    public void exitFromCriticalZone() {
        logger.addLog("поток №" + id + " вышел из критической зоны");
        logger.addLog(new Date().toString());
        logger.updateTitle(id, "хорошо поработал");
        inCriticalZone = false;
    }

    //конструктор
    public ThreadStarter (int priority) {
        this.priority = priority;
        synchronized (this){threadsCount++;}
        id = threadsCount;
        logger = new MyLoggerPanel(id);
    }

    /**
     * стартует поток
     */
    public void startThread () {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * проверяет использует ли кто-то ресурс
     *
     * @return свободность ресурса
     */
    private boolean checkEmploymentResource() {
        List <ThreadStarter> list = ThreadsFactory.getInstance().getThreadsList(); //получить список всех потоков
        //for (ThreadStarter threadStarter : list) {
        for (int i = 0; i < list.size(); i++) {
            ThreadStarter threadStarter = list.get(i);
            if (threadStarter.getId() != this.id) //если сравнивается сам с собой
                if (threadStarter.isWorkWithResource()) { //если кто-то уже работает с ресурсом, то
                    logger.addLog("поток № " + threadStarter.getId() + " уже работает с ресурсом");
                    return false; //никто другой, до освобождения, с ним работать не может
                }
                else if (threadStarter.InCriticalZone()) { //если кто-то другой изъявил желание воспользоваться ресурсом , то
                    logger.addLog("поток № " + threadStarter.getId() + " тоже находиться в криточеском участке");
                    if (threadStarter.getPriority() > this.getPriority()) {//сравнить приоритеты, если приоритет другого потока выше
                        logger.addLog("но его приоритет выше");
                        return false; //никто другой, до освобождения, с ним работать не может
                    }
                    else if (threadStarter.getId() < this.id && threadStarter.getPriority() > this.getPriority()) {//если поток с таким-же приоритетом раньше изъявил желание
                        logger.addLog("но он раньше подал заявку");
                        return false;
                    }
                }
        }
        //прошли по всем потокам и не встретили ниодного работающего с ресурсом
        //и желающего работать с ресурсом выше приоритетом
        logger.addLog("получил ресурс");
        logger.updateTitle(id, "ресурс получен");
        return true; //ресурс свободен
    }

    private void doSomething() {//получили ресурс
        try {
            logger.updateTitle(id, "работаю с ресурсом");
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            System.err.println("что-то с потоком");
        }
    }

    private void hmmmm() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(id + " started " + "№потока = " + thread.getId() + ", priority = " + this.getPriority());
        //фабричный метод
        enterToCriticalZone();
        while (!checkEmploymentResource()) { //пока не освободимся или не подойдет наше время, хммммм..., ждем...
            hmmmm();
            logger.updateTitle(id, "ресурс занят");
        }
        startWorkWithResource();    //начать работу с ресурсом
        doSomething();              //получили контроль над ресурсом , делаем с ним что-то
        endWorkWithResource();      //закончить работу с ресурсом
        exitFromCriticalZone();     //освобождаем ресурс , снимаем флаг
    }
}