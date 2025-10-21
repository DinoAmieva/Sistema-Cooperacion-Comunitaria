package proyectoparalela.utils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizationUtils {
    public static final Lock dbLock = new ReentrantLock(true);
    public static final Semaphore writeSemaphore = new Semaphore(1, true);
}


