package net.minecraft2.client.settings;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import net.minecraft2.client.settings.KeyBinding;
import net.minecraft2.entity.player.EntityPlayer;
import net.minecraft2.client.Minecraft;

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
	public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
	public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
	public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
	public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
	public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
	public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
	public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
	public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
	public KeyBinding keyBindSwapHands = new KeyBinding("key.swapHands", 33, "key.categories.inventory");
	public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.inventory");
	public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
	public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
	public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
	public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayers");
	public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayers");
	public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayers");
	public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
	public KeyBinding keyBindTogglePerpesctive = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
	public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
	public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 0, "key.categories.misc");
	public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 87, "key.categories.misc");
	public KeyBinding keyBindAdvancement = new KeyBinding("key.advancements", 38, "key.categories.misc");
	public KeyBinding[] keyBindsHotBar = new KeyBinding[] {
			new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), 
			new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), 
			new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
			new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
			new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
			new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
			new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
			new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
			new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")};
	public KeyBinding keyBindSaveToolbar = new KeyBinding("key.saveToolbarActivator", 46, "key.categories.creative");
	public KeyBinding keyBindloadToolbar = new KeyBinding("key.loadToolbarActivator", 45, "key.categories.creative");
	public KeyBinding[] keyBindings;
	protected Minecraft mc;
	private File optionsFile;
	public EnumDifficulty difficulty;
	public boolean hideGUI;
	public int thirdPersonView;
	
	public boolean showDebugInfo;
	public boolean showDebugProfilerChart;
	public boolean showLagometer;
	
	public boolean smoothCamera;
	public boolean debugCamEnable;
	public float gammaSetting;
	public float fovSetting;
	public float saturation;
	
	public int guiScale;
	
	public int particleSetting;
	public int narrator;
	
	public String language;
	public boolean forceUnicodeFont;
	public int ofFogType = 1;
	public float ofFogStart = 0.8f;
	public int ofMipmapType = 0;
	public boolean ofOcclusionFancy = false;
	public boolean ofSmoothFps = false;
	public boolean ofSmoothWorld = Config.isSingleProcessor();
	public boolean ofLazyChunkLoading = Config.isSingleProcessor();
	public float ofAoLevel = 1.0f;
	public int ofAaLevel = 0;
	public int ofAfLevel = 1;
	public int ofClouds = 0;
	public float ofCloudsHeight = 0f;
	public int ofTrees = 0;
	public int ofRain = 0;
	public int ofDroppedItems = 0;
	public int ofBetterGrass = 3;
	public int ofAutoSaveTicks = 4000;
	public boolean ofLagometer = false;
	public boolean ofProfiler = false;
	public boolean ofShowFps = false;
	public boolean ofWeather = true;
	public boolean ofSky = true;
	public boolean ofStars = true;
	public boolean ofSunMoon = true;
	public int ofVignette = 0;
	public int ofChunkUpdates = 1;
	public boolean ofChunkUpdateDynamic = false;
	public int ofTime = 0;
	public boolean ofClearWater = false;
	public boolean ofBetterSnow = false;
	public String ofFullscreenMode = "Default";
	public boolean ofSwampColors = true;
	public boolean ofRandomMobs = true;
	public boolean ofSmoothBiomes = true;
	public boolean ofCustomFonts = true;
	public boolean ofCustomColors = true;
	public boolean ofCustomSky = true;
	public boolean ofShowCapes = true;
	public int ofConnectedTexture = 2;
	public boolean ofCustomItem = true;
	public boolean ofNaturalTexture = false;
	public boolean ofFastRender = false;
	public boolean ofFastMath = false;
	public int ofTranslucentBlocks = 0;
	public boolean ofDynamicFov = true;
	public boolean ofAlternateBlocks = true;
	public int ofDynamicLights = 3;
	public boolean ofCustomEntityModels = true;
	public int ofScreenhotSize = 1;
	public int ofAnimatedWater = 0;
	public int ofAnimatedLava = 0;
	public boolean ofAnimatedFire = true;
	public boolean ofAnimatedPortal = true;
	public boolean ofAnimatedRedstone = true;
	public boolean ofAnimatedExplosion = true;
	public boolean ofAnimatedFlame = true;
	public boolean ofAnimatedSmoke = true;
	public boolean ofVoidParticles = true;
	public boolean ofWaterParticles = true;
	public boolean ofRainSplash = true;
	public boolean ofPortalParticles = true;
	public boolean ofPotionParticles = true;
	public boolean ofFireworkParticles = true;
	public boolean ofDrippingWaterLava = true;
	public boolean ofAnimatedTerrain = true;
	public boolean ofAnimatedTextures = true;
	public static final int DEFAULT = 0;
	public static final int FAST = 1;
	public static final int FANCY  = 2;
	public static final int OFF = 3;
	public static final int SMART = 4;
	public static final int ANIM_ON = 0;
	public static final int ANIM_GENERATED = 1;
	public static final int ANIM_OFF = 2;
	public static final String DEFAULT_STR = "Default";
	private static final int[] OF_TREES_VALUES = new int[] {0,1,4,2};
	private static final int[] OF_DYNAMIC_LIGHTS = new int[] {3,1,2};
	private static final String[] KEYS_DYNAMIC_LIGHTS = new String[] {"options.off", "options.graphics.fast", "options.graphics.fancy"};
	public KeyBinding ofKeyBindZoom;
	private File optionFileOF;
	private boolean needsResourceRefresh = false;
	
	public GameSettings(Minecraft mc, File optionsFile) {
		this.keyBindings = (KeyBinding[])ArrayUtils.addAll(new KeyBinding[] {this.keyBindAttack, this.keyBindUseItem, 
			this.keyBindForward, this.keyBindLeft, 
			this.keyBindBack, this.keyBindRight, 
			this.keyBindJump, this.keyBindSneak, 
			this.keyBindSprint, this.keyBindDrop,
			this.keyBindInventory, this.keyBindChat,
			this.keyBindPlayerList, this.keyBindPickBlock,
			this.keyBindCommand, this.keyBindScreenshot,
			this.keyBindTogglePerpesctive, this.keyBindSmoothCamera,
			this.keyBindFullscreen, this.keyBindSpectatorOutlines,
			this.keyBindSwapHands, this.keyBindAdvancement,
			this.keyBindSaveToolbar, this.keyBindloadToolbar}, this.keyBindsHotBar);
		//TODO
	}
	
}
