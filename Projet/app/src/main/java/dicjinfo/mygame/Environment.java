package dicjinfo.mygame;

import android.media.audiofx.EnvironmentalReverb;

import java.util.ArrayList;
import java.util.Random;

public class Environment {

    private XMLManager xmlManager;
    private Section[] sections;
    private Player player;

    public Section[] getSections() {
        return sections;
    }

    public Environment(Player player) {

        xmlManager = new XMLManager();
        sections = new Section[3];
        this.player = player;
        init();
    }

    public void check() {

        if(player.getY() < -2000) {
            shift();
        }
    }

    private void init() {

        Random random = new Random();
        int randomNum = random.nextInt() % 10;
        for (int i = 0; i < sections.length; i++) {
            sections[i] = xmlManager.getSection(0);
            for (int j = 0; j < sections[i].getContent().size(); j++) {
                GameObject go = sections[i].getContent().get(j);
                go.setY(go.getY() - (2000 * i));
                if(go instanceof CollidableGameObject)
                    ((CollidableGameObject) go).getCollider().update();
            }
            GameObject.getGameObjects().addAll(sections[i].getContent());
        }
    }

    private void shift() {

        Random random = new Random();
        int randomNum = random.nextInt() % 10;
        GameObject.getGameObjects().removeAll(sections[0].getContent());
        sections[0] = sections[1];
        sections[1] = sections[2];
        sections[2] = xmlManager.getSection(0);
        for(int i = 0; i < GameObject.getGameObjects().size(); i++) {
            GameObject go = GameObject.getGameObjects().get(i);
            go.setY(go.getY() + 2000);
            if(go instanceof CollidableGameObject)
                ((CollidableGameObject) go).getCollider().update();
        }
        for(int i = 0; i < sections[2].getContent().size(); i++) {
            GameObject go = sections[2].getContent().get(i);
            go.setY(go.getY() - 4000);
        }
        GameObject.getGameObjects().addAll(sections[2].getContent());
    }
}