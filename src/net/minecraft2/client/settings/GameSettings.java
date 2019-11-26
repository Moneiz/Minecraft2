package net.minecraft2.client.settings;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

public class GameSettings {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new Gson();
	private static final Type TYPE_LIST_STRING = new ParameterizedType() {
		
		public Type getRawType() {
			return List.class;
		}	
		
		@Override
		public Type getOwnerType() {
			return null;
		}
		
		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] {String.class};
		}
	};
	public static final Splitter COLON_SPLITTER = Splitter.on(':');
	private static final String[] GUISCALES = new String[] {"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
	private static final String[] PARTICLES = new String[] {"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
	private static final String[] AMBIENT_OCCLUSIONS = new String[] {"options.ao.off", "options.ao.min", "options.ao.max"};
	private static final String[] CLOUDS_TYPES = new String[] {"options.off", "options.clouds.fast", "options.clouds.fancy"};
	private static final String[] ATTACK_INDICATORS = new String[] {"options.off", "options.attack.crosshair", "options.attack.hotbar"};
	private static final String[] NARRATOR = new String[] {"options.narrator.off", "options.narrator.all", "options.narrator.chat", "options.narrator.system"};
	
	public float mouseSensibility = 0.5f;
	public boolean invertMouse;
	public int renderDistanceChunks = -1;
	public boolean viewBobbing = true;
	public boolean anaglyph;
	public boolean fboEnable = true;
	public int limitFramerate = 120;
	
	public int clouds = 2;
	public boolean fancyGraphics = true;
	
	public int ambientOcclusion = 2;
	public List<String> resourcePacks = Lists.<String>newArrayList();
	public List<String> incompatibleResourcePacks = Lists.<String>newArrayList();
	public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
	public boolean chatColours = true;
	public boolean chatLinks = true;
	public boolean chatLinksPrompt = true;
	public float chatOpacity = 1.0f;
	public boolean snooperEnabled = true ;
	public boolean fullscreen;
	public boolean enableVsync = true;
	public boolean useVbo = true;
	public boolean reducedDebugInfo;
	public boolean hideServerAddress;
	
	public boolean advancedItemTooltips;
	
	public boolean pauseOnLostFocus = true;
	private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
	public boolean touchScreen;
	public EnumHandSide mainHand = EnumHandSide.RIGHT;
	public int overrideWidth;
	public int overrideHeight;
	public boolean heldItemTooltips = true;
	public float chatScale = 1.0f;
	public float chatWidth = 1.0f;
	public float chatHeightUnfocused = 0.44366196f;
	public float chatHeightFocused = 1.0f;
	public int mipmapLevels = 4;
	public final Map<SoundCategory, Float> soundLevels = Maps.newEnumMap(SoundCategory.class);
	public boolean useNativeTransport = true;
	public boolean entityShadow = true;
	public int attackIndicator = 1;
	public boolean enableWeakAttacks;
	public boolean showSubtitles;
	public boolean realmsNotifications = true;
	public boolean autoJump = true;
	public TutorialSteps tutorialSteps = TutorialSteps.MOVEMENT;
	//TODO
}
