import java.util.Queue;

public abstract class Worker {
    private int id;
    Queue<Work> workQueue;
    private static int numWorkers = 0;

    public abstract void run();

    Worker(Queue<Work> workQueue) {
        // TODO: problem1
        this.workQueue = workQueue;
        id = numWorkers++;
    }

    void report(String msg){
        System.out.print("\b".repeat(300) +
                "[" + ">".repeat(workQueue.size()) + "] " + id + "-th Worker(" + getClass().getName() + ") " + msg);
    }
}

class Producer extends Worker {
    Producer(Queue<Work> workQueue) {
        super(workQueue);
    }

    public void run() {
        // TODO: problem2
        Work work = new Work();
        workQueue.add(work);
        report(String.format("produced work%d", work.getId()));
    }
}

class Consumer extends Worker {
    Consumer(Queue<Work> workQueue) {
        super(workQueue);
    }

    public void run() {
        // TODO: problem2
        if(Math.random() < (double) 1/3 && workQueue.size() != 0){
            Work work = workQueue.poll();
            report(String.format("consumed work%d", work.getId()));
        }
        else report("failed to consume work");

    }
}
