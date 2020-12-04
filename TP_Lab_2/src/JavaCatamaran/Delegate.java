package JavaCatamaran;

import java.util.LinkedList;
import java.util.List;

public class Delegate {

    private List<AddBoatListener> listeners = new LinkedList<>();

    public Delegate(AddBoatListener listener) {
        listeners.add(listener);
    }

    public void addListener(AddBoatListener newListener) {
        listeners.add(newListener);
    }

    public void invoke(Vehicle boat) {
        for (AddBoatListener listeners : this.listeners) {
            listeners.addBoat(boat);
        }
    }
}
