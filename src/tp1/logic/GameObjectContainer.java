package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameObject;

public class GameObjectContainer {

	private List<GameObject> objects;

	public GameObjectContainer() {
		objects = new ArrayList<>();
	}

	public void add(GameObject object) {
		objects.add(object);
	}

	public void remove(GameObject object) {
		objects.remove(object);
	}

	/**
	 * Automatic moves of every object
	 * */
	public void automaticMoves() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);						
			object.automaticMove();	
		}
	}

	/**
	 * Computer actions of every object
	 * */
	public void computerActions() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject object = objects.get(i);
			object.computerAction();
		}
	}
	
	public GameObject getObject(int n) {
		return objects.get(n);	
	}
	
	public int getNum() {
		return this.objects.size();
	}
	
	public Position getObjectPosition(int num) {
		GameObject object = objects.get(num);
		return (object.getPos());
	}
	
	public String getObjectSymbol(int num) {
		GameObject object = objects.get(num);
		return (object.getSymbol());
	}
	
	public int getObjectPoints(int num) {
		GameObject object = objects.get(num);
		return (object.getPoints());
	}
	
	public int getObjectDamage(int num) {
		GameObject object = objects.get(num);
		return (object.getDamage());
	}
	
}
