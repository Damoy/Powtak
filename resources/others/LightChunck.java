package core.world.light;

import java.util.ArrayList;
import java.util.List;

public class LightChunck {
	
	private List<LightSpot> lights;
	
	public LightChunck() {
		super();
		lights = new ArrayList<>();
	}
	
	public LightChunck add(LightSpot lightSpot) {
		lights.add(lightSpot);
		return this;
	}

	public LightChunck(List<LightSpot> lights) {
		this.lights = lights;
	}

	public static LightChunck of(List<LightSpot> lights) {
		return new LightChunck(lights);
	}

	public void enlight(float brightness) {
		lights.forEach(light -> light.enlight(brightness));
	}

	public void darken(float brightness) {
		lights.forEach(light -> light.darken(brightness));
	}

}
