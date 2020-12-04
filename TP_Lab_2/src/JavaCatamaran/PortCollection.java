package JavaCatamaran;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class PortCollection {

    private final Map<String, Port<Vehicle, IDrawing>> portStages;

    private final int pictureWidth;

    private final int pictureHeight;

    private final String separator = ":";

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

    public boolean SaveData(String filename) {
        StringBuilder textToFile = new StringBuilder("");
        textToFile.append("PortCollection\n");
        for (int i = 0; i < portStages.size(); i++) {
            var level = portStages.get(getKeys().get(i));
            textToFile.append("Port" + separator + getKeys().get(i) + '\n');
            ITransport boat = null;
            for (int i1 = 0; (boat = level.GetNext(i1)) != null; i1++) {
                if (boat != null) {
                    if (boat instanceof Catamaran) {
                        textToFile.append("Catamaran" + separator);
                    } else {
                        textToFile.append("Boat" + separator);
                    }
                    //Записываемые параметры
                    textToFile.append(boat);
                    textToFile.append('\n');
                }
            }
        }
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write(textToFile.toString());
        } catch (Exception e) {

        }
        return true;
    }

    public boolean LoadData(String filename) {
        File file = new File(filename);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            if (line.contains("PortCollection")) {
                //очищаем записи
                portStages.clear();
            } else {
                return false;
            }
            Vehicle boat = null;
            String key = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("Port")) {
                    key = line.split(separator)[1];
                    portStages.put(key, new Port<Vehicle, IDrawing>(pictureWidth,
                            pictureHeight));
                    continue;
                }
                if (line == null || line.equals("")) {
                    continue;
                }
                if (line.split(separator)[0].equals("Boat")) {
                    boat = new Boat(line.split(separator)[1]);
                } else if (line.split(separator)[0].equals("Catamaran")) {
                    boat = new Catamaran(line.split(separator)[1]);
                }
                var result = portStages.get(key).append(boat);
                if (!result) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean savePort(String filename, String key) {
        if (!portStages.containsKey(key)) {
            return false;
        }
        StringBuilder textToFile = new StringBuilder("");
        textToFile.append("OnlyPort\n");
        var port = portStages.get(key);
        textToFile.append("Port" + separator + key + '\n');
        ITransport boat = null;
        for (int i = 0; (boat = port.GetNext(i)) != null; i++) {
            if (boat != null) {
                if (boat instanceof Catamaran) {
                    textToFile.append("Catamaran" + separator);
                } else {
                    textToFile.append("Boat" + separator);
                }
                //Записываемые параметры
                textToFile.append(boat);
                textToFile.append('\n');
            }
        }
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try (FileWriter fw = new FileWriter(file, false)) {
            fw.write(textToFile.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean loadPort(String filename) {
        File file = new File(filename);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            String key = "";
            if (!line.contains("OnlyPort")) {
                return false;
            }
            Vehicle boat = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Port")) {
                    key = line.split(separator)[1];
                    if (portStages.containsKey(key)) {
                        portStages.get(key).clearList();
                    }
                    portStages.put(key, new Port<Vehicle, IDrawing>(pictureWidth,
                            pictureHeight));
                    continue;
                }
                if (line == null || line.equals("")) {
                    continue;
                }
                if (line.split(separator)[0].equals("Boat")) {
                    boat = new Boat(line.split(separator)[1]);
                } else if (line.split(separator)[0].equals("Catamaran")) {
                    boat = new Catamaran(line.split(separator)[1]);
                }
                var result = portStages.get(key).append(boat);
                if (!result) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
