package lab1.main;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ThreadsFactory factory = ThreadsFactory.getInstance(); //получаем фабрику

        ThreadStarter controller = factory.newThreadStarter(0);//создаём псевдопоток , чтоб все потоки стартовали в 1 время
        controller.startWorkWithResource();//псевдопоток занимает ресурс

        factory.newThreadStarter(2).startThread();//создаю n-ое количество потоков
        factory.newThreadStarter(2).startThread();
        factory.newThreadStarter(4).startThread();
        factory.newThreadStarter(5).startThread();

        controller.endWorkWithResource();//освобождаю ресурс
    }
}