package core.entities.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rendering.Screen;

public class EnergyChunck {

	private List<Energy> energies;

	private EnergyChunck(List<Energy> energies) {
		this.energies = energies;
	}
	
	private EnergyChunck() {
		this.energies = new ArrayList<>();
	}
	
	public static EnergyChunck empty() {
		return new EnergyChunck();
	}
	
	public static EnergyChunck of(Energy... energies) {
		return new EnergyChunck(Arrays.asList(energies));
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
