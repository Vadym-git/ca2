package ie.mvo.newsreaderca2.interfaces;

public interface MyObserver {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
    void notifySubscribers(int currentPosition, int positionToRemove);
}


