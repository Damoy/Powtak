package rendering.particles;

import java.util.ArrayList;
import java.util.List;

import rendering.Screen;

public class ParticleEngine {
	
	private List<List<Particle>> particlesComponents;
	
	private ParticleEngine(List<List<Particle>> particlesComponents) {
		this.particlesComponents = particlesComponents;
	}
	
	private ParticleEngine() {
		this.particlesComponents = new ArrayList<>();
	}
	
	public static ParticleEngine empty() {
		return new ParticleEngine();
	}
	
	public static ParticleEngine of(List<List<Particle>> particlesComponents) {
		return new ParticleEngine(particlesComponents);
	}
	
	public void update() {
		particlesComponents.forEach(particlesComponent -> particlesComponent.forEach(particle -> particle.update()));
	}
	
	public void render(Screen s) {
		particlesComponents.forEach(particlesComponent -> particlesComponent.forEach(particle -> particle.render(s)));
	}
	
	public void addComponent(List<Particle> particlesComponent) {
		particlesComponents.add(particlesComponent);
	}
	
	public void removeComponent(List<Particle> particlesComponent) {
		particlesComponents.remove(particlesComponent);
	}
	
	public void remove(int componentId, Particle particle) {
		particlesComponents.get(componentId).remove(particle);
	}
	
}
