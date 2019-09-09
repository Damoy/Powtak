package core.entities.items;

import java.util.ArrayList;
import java.util.List;

import rendering.Screen;

public class EnergyChunck {

	private List<Energy> energies;

	public EnergyChunck(List<Energy> energies) {
		this.energies = energies;
	}
	
	public EnergyChunck() {
		this.energies = new ArrayList<>();
	}
	
	public void render(Screen s) {
		energies.forEach(energy -> energy.render(s));
	}
	
	public void update() {
		energies.forEach(energy -> energy.update());
	}
	
	public void add(Energy energy) {
		energies.add(energy);
	}
	
	public void remove(Energy energy) {
		energies.remove(energy);
	}
	
}
