package core.configs;

import java.awt.Color;

import rendering.Texture;
import utils.Colors;
import utils.exceptions.PowtakException;
import utils.exceptions.UnknownEnumOptionException;

public enum CharacterSkin {
	NORMAL, BLUE, GREEN, PINK;
	
	public static Color getCharacterColor(CharacterSkin characterSkin) throws PowtakException {
		Color color = null;
		
		switch (characterSkin) {
		case NORMAL:
			color = Colors.SIENNA_BROWN;
			break;
		case BLUE:
			color = Colors.DODGER_BLUE;
			break;
		case GREEN:
			color = Colors.LIGHT_GREEN;
			break;
		case PINK:
			color = Colors.HOT_PINK;
			break;
		default:
			throw new UnknownEnumOptionException(CharacterSkin.class.getName(), characterSkin.name());
		}
		
		return color;
	}
	
	public static Texture getCharacterSpriteSheet(CharacterSkin characterSkin) throws PowtakException {
		Texture texture = null;
		
		switch (characterSkin) {
		case NORMAL:
			texture = Texture.NORMAL_PLAYER_SPRITESHEET;
			break;
		case BLUE:
			texture = Texture.BLUE_PLAYER_SPRITESHEET;
			break;
		case GREEN:
			texture = Texture.GREEN_PLAYER_SPRITESHEET;
			break;
		case PINK:
			texture = Texture.PINK_PLAYER_SPRITESHEET;
			break;
		default:
			throw new UnknownEnumOptionException(CharacterSkin.class.getName(), characterSkin.name());
		}
		
		return texture;
	}
	
	public static Texture getCharacterSkinIcon(CharacterSkin characterSkin) throws PowtakException {
		Texture texture = null;
		
		switch (characterSkin) {
		case NORMAL:
			texture = Texture.NORMAL_PLAYER_SKIN_ICON;
			break;
		case BLUE:
			texture = Texture.BLUE_PLAYER_SKIN_ICON;
			break;
		case GREEN:
			texture = Texture.GREEN_PLAYER_SKIN_ICON;
			break;
		case PINK:
			texture = Texture.PINK_PLAYER_SKIN_ICON;
			break;
		default:
			throw new UnknownEnumOptionException(CharacterSkin.class.getName(), characterSkin.name());
		}
		
		return texture;
	}
	
	public static Texture getCharacterReducedIcon(CharacterSkin characterSkin) throws PowtakException {
		Texture texture = null;
		
		switch (characterSkin) {
		case NORMAL:
			texture = Texture.NORMAL_PLAYER_ICON;
			break;
		case BLUE:
			texture = Texture.BLUE_PLAYER_ICON;
			break;
		case GREEN:
			texture = Texture.GREEN_PLAYER_ICON;
			break;
		case PINK:
			texture = Texture.PINK_PLAYER_ICON;
			break;
		default:
			throw new UnknownEnumOptionException(CharacterSkin.class.getName(), characterSkin.name());
		}
		
		return texture;
	}
}
