package de.opendata.multisnake;

/**
 * Created by Cedric on 17.06.2016.
 */
public interface LifecycleListener {

    //an interface to listen for important lifecycle ui changes in the MainActivity
    void notifyStart();
    void notifyPause();

}
