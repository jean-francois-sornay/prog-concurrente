import java.util.concurrent.atomic.AtomicInteger;

public class ServerObserver {
    private final AtomicInteger lineHeaderNum = new AtomicInteger(0);

    public void increment() {
        this.lineHeaderNum.incrementAndGet();
    }

    public void increment(int i) {
        this.lineHeaderNum.addAndGet(i);
    }

    public int getLineHeaderNum() {
        return this.lineHeaderNum.get();
    }
}
