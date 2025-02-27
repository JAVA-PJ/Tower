package Game.System;
import Enum.EventType;
import Game.Screen.App;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class RandomEvent {
    private Random random;
    private ArrayList<Event> event;
    private int cntSpawn = 0;
    private final int MAX_OBJECTS = 3;

    class Event {
        private EventType type;
        private int posX, posY;
        private Image image;
        
        public Event(EventType type, int posX, int posY) {
            this.type = type;
            this.posX = posX;
            this.posY = posY;
        }
        
        public void loadImage() {
            image = new ImageIcon(getClass().getResource(type.getPath())).getImage();
        }
    }
    
    public RandomEvent() {
        random = new Random();
        event = new ArrayList<>();
    }

    public void clearEvent() { event.clear(); }
    
    public void spawnNewEvent(int score) {
        event.removeIf(obj -> obj.posY > App.HEIGHT);
        
        if (event.size() >= MAX_OBJECTS) {
            if (cntSpawn % 2 != 0)
                cntSpawn++;
            return;
        }
        
        EventType eventType = null;
        int randomX = random.nextInt(App.WIDTH - 200);
        int randomY = -random.nextInt(200) - 400;
        
        if (score <= 15) {
            switch (random.nextInt(3)) {
                case 0: eventType = EventType.CLOUD_1; break;
                case 1: eventType = EventType.CLOUD_2; break;
                case 2: eventType = EventType.CLOUD_3; break;
            }
        } else if (score <= 20) {
                eventType = EventType.MOON_1;
        } else {
            switch (random.nextInt(3)) {
                case 0: eventType = EventType.PLANET_1; break;
                case 1: eventType = EventType.PLANET_2; break;
                case 2: eventType = EventType.STAR; break;
            }
        }
        
        Event newObject = new Event(eventType, randomX, randomY);
        newObject.loadImage();
        event.add(newObject);
        cntSpawn++;
    }
    
    public void moving() {
        for (Event obj : event)
            obj.posY += 10;
    }
    
    // Call this method in the game's paint method
    public void draw(Graphics g) {
        for (Event obj : event)
            if (obj.image != null)
                g.drawImage(obj.image, obj.posX, obj.posY, 200, 200, null);
    }
}
