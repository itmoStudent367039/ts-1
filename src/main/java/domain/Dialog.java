package domain;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Dialog {

    private Lock lock;
    private Condition fordCondition;
    private Condition arthurCondition;
    private boolean isDialogStart;
    private boolean isFordsTurn = true;
    private Thread first;
    private Thread second;

    public Dialog(Lock lock, Condition fordCondition, Condition arthurCondition) {
        this.lock = lock;
        this.fordCondition = fordCondition;
        this.arthurCondition = arthurCondition;
    }

    public void start() {
        first.start();
        second.start();
    }

    public void setFirst(Thread first) {
        this.first = first;
    }

    public void setSecond(Thread second) {
        this.second = second;
    }

    public void waitForTurn(boolean isFord) throws InterruptedException {
        lock.lock();
        try {
            while (isFord != isFordsTurn) {
                if (isFord) {
                    fordCondition.await();
                } else {
                    arthurCondition.await();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void setDialogStart() {
        try {
            lock.lock();
            isDialogStart = true;
        } finally {
            lock.unlock();
        }
    }

    public boolean isDialogStart() {
        try {
            lock.lock();
            return isDialogStart;
        } finally {
            lock.unlock();
        }
    }

    public void switchTurn() {
        lock.lock();
        try {
            isFordsTurn = !isFordsTurn;
            if (isFordsTurn) {
                fordCondition.signal();
            } else {
                arthurCondition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "isDialogStart=" + isDialogStart +
                ", isFordsTurn=" + isFordsTurn +
                ", first=" + first +
                ", second=" + second +
                '}';
    }
}
