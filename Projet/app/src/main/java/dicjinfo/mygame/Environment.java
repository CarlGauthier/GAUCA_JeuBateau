package dicjinfo.mygame;

import android.media.audiofx.EnvironmentalReverb;

import java.util.ArrayList;
import java.util.Random;

public class Environment {

    private XMLManager xmlManager;
    private Section[] sections;
    private ArrayList<GameObject> gameObjects;
    private Player player;
    
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Player getPlayer() {
        return player;
    }

    public Environment() {
        Random random = new Random();
        int randomNum = random.nextInt(3);
        gameObjects = new ArrayList<GameObject>();
        player = new Player();
        gameObjects.add(player);
        xmlManager = new XMLManager();
        sections = new Section[3];
        sections[0] = new Section();
        sections[1] = new Section();
        sections[2] = xmlManager.getSection(randomNum);
        shiftNewSection(sections[2]);
        gameObjects.addAll(sections[2].getContent());
    }

    public void update(ArrayList<GameObject> emitQueue, ArrayList<GameObject> destroyQueue) {

        for (GameObject go : emitQueue) {
            int index = gameObjects.size();
            for(int i = 0; i < gameObjects.size(); i++) {
                GameObject go2 = gameObjects.get(i);
                if(go2.getzIndex() < go.getzIndex()) {
                    index = i;
                    break;
                }
            }
            gameObjects.add(index, go);
        }
        emitQueue.clear();
        for (GameObject go : destroyQueue) {
            if(sections[1].getContent().contains(go))
                sections[1].getContent().remove(go);
            gameObjects.remove(go);
        }
        destroyQueue.clear();
        if(player.getY() < -2000) {
            shiftAll();
        }
    }

    private void shiftNewSection(Section section) {

        for(GameObject go : section.getContent()) {
            go.setY(go.getY() - 4000);
        }
    }

    private void shiftAll() {

        Random random = new Random();
        int randomNum = random.nextInt(3);
        gameObjects.removeAll(sections[0].getContent());
        sections[0] = sections[1];
        sections[1] = sections[2];
        sections[2] = xmlManager.getSection(randomNum);
        for(int i = 0; i < gameObjects.size(); i++) {
            GameObject go = gameObjects.get(i);
            go.setY(go.getY() + 2000);
        }
        shiftNewSection(sections[2]);
        gameObjects.addAll(sections[2].getContent());
    }
}