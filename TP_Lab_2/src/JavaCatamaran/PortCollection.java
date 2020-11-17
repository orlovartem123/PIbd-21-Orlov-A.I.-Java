package JavaCatamaran;

import java.util.*;

public class PortCollection {

    private final Map<String, Port<Vehicle, IDrawing>> portStages;

    private final int pictureWidth;

    private final int pictureHeight;

    public PortCollection(int pictureWidth, int pictureHeight) {
        portStages = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public List<String> getKeys() {
        return new LinkedList<String>(portStages.keySet());
    }

    public void AddParking(String name) {
        if (portStages.containsKey(name)) {
            return;
        }
        portStages.put(name, new Port<>(pictureWidth, pictureHeight));
    }

    public void DelParking(String name) {
        if (portStages.containsKey(name)) {
            portStages.remove(name);
        }
    }

    public Port<Vehicle, IDrawing> getPort(String ind) {
        if (portStages.containsKey(ind)) {
            return portStages.get(ind);
        }
        return null;
    }

    public Vehicle getBoat(String key, int index) {
        if (portStages.containsKey(key)) {
            return portStages.get(key).takeObject(index);
        }
        return null;
    }
}
