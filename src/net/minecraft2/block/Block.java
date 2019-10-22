package net.minecraft2.block;

import net.minecraft2.block.material.MapColor;
import net.minecraft2.block.material.Material;
import net.minecraft2.block.properties.IProperty;
import net.minecraft2.block.state.BlockStateContainer;
import net.minecraft2.block.state.IBlockState;
import net.minecraft2.util.ResourceLocation;
import net.minecraft2.util.registry.RegistryNamespacedDefaultedByKey;

public class Block {

	private static final ResourceLocation AIR_ID = new ResourceLocation("air");
	public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block>  REGISTRY = new RegistryNamespacedDefaultedByKey<ResourceLocation, Block>(AIR_ID);
	
	private String unlocalizedName;
	protected boolean enableStats;
	protected SoundType blockSoundType;
	public float blockParticleGravity;
	public float slipperiness;
	protected final Material blockMaterial;
	protected final MapColor blockMapColor;
	protected final BlockStateContainer blockState;
	private IBlockState defaultBlockState;
	protected boolean fullBlock;
	protected int lightOpacity;
	protected boolean translucent;
	
	
	public Block(Material material) {
		this(material, material.getMaterialMapColor());
	}
	public Block(Material material, MapColor materialMapColor) {
		this.enableStats = true;
		this.blockSoundType = SoundType.STONE;
		this.blockParticleGravity = 1.0f;
		this.slipperiness=0.6f;
		this.blockMaterial = material;
		this.blockMapColor = materialMapColor;
		this.blockState = this.createBlockState();
		this.setDefaultState(this.blockState.getBaseState());
		this.fullBlock = this.getDefaultState().isOpaqueCube();
		this.lightOpacity = this.fullBlock ? 255 : 0;
		this.translucent = !material.blocksLight();
	}
	public final IBlockState getDefaultState() {
		return this.defaultBlockState;
	}
	protected final void setDefaultState(IBlockState state) {
		this.defaultBlockState = state;
	}
	private BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[0]);
	}
	public static void registerBlocks(){
		registerBlock(0, AIR_ID, (new BlockAir().setUnlocalizedName("air")));
	}
	public static void registerBlock(int id, ResourceLocation textualId, Block block_) {
		REGISTRY.register(id, textualId, block_);
	}
	public static void registerBlock(int id, String textualId, Block block_) {
		REGISTRY.register(id, new ResourceLocation(textualId), block_);
	}
	
	public Block setUnlocalizedName(String name) {
		this.unlocalizedName = name;
		return this;
	}
}
