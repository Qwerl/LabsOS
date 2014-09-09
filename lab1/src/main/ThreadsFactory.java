package lab1.main;

import java.util.ArrayList;
import java.util.List;

public class ThreadsFactory { //singleton factory

    private static ThreadsFactory threadsFactory = null;

    public static ThreadsFactory getInstance() {
        if (threadsFactory == null)
            threadsFactory = new ThreadsFactory();
        return threadsFactory;
    }

    private List threads = new ArrayList<ThreadStarter>();

    //закрытый конструктор
    private ThreadsFactory() {
    }

    /**
     * создаёт экземпляр класса ThreadStarter, кладет его в список и возвращает
     * @param priority приопитет потока
     * @return  возвращает созданный экземпляр класса ThreadStarter
     */
    public ThreadStarter newThreadStarter(int priority) {
        ThreadStarter threadStarter = new ThreadStarter(priority);
        //ToDo: тут можно добавить взаимодействие с файлом конфигов
        threads.add(threadStarter);
        return threadStarter;
    }

    /**
     * @return возвращает список созданных ThreadStarter'ов
     */
    public List<ThreadStarter> getThreadsList () {
        return threads;
    }
}
