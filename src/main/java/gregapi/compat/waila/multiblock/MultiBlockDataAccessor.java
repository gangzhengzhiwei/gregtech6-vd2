package gregapi.compat.waila.multiblock;

import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A "Fake" IWailaDataAccessor contains some fuck things of a MultiBlockController.<br>
 * Used in {@link WailaMultiBlockPart} for waila to get controller information from any part of multiblock structure.<br>
 * **/
public class MultiBlockDataAccessor implements IWailaDataAccessor {
    private final IWailaDataAccessor mPartDataAccessor; // The multiblockpart which used
    private final TileEntityBase10MultiBlockBase mController;
    private final NBTTagCompound mNBT;
    private final MovingObjectPosition mPosition;
    private static final Vec3 ZERO = Vec3.createVectorHelper(0, 0, 0);

    public MultiBlockDataAccessor(IWailaDataAccessor aPartAccessor, TileEntity aController, NBTTagCompound aNBT) {
        mPartDataAccessor = aPartAccessor;
        mController = (TileEntityBase10MultiBlockBase) aController;
        mNBT = aNBT;
        mPosition = new MovingObjectPosition(mController.xCoord, mController.yCoord, mController.zCoord, -1, ZERO);
    }

    @Override
    public World getWorld() {
        return mPartDataAccessor.getWorld();
    }
    @Override
    public EntityPlayer getPlayer() {
        return mPartDataAccessor.getPlayer();
    }
    @Override
    public Block getBlock() {
        return mController.getBlockType();
    }
    @Override
    public int getBlockID() {
        return 0; //I don't know how to deal it;
    }
    @Override
    public String getBlockQualifiedName() {
        return ""; //I don't know how to deal it;
    }
    @Override
    public int getMetadata() {
        return mController.blockMetadata;
    }
    @Override
    public TileEntity getTileEntity() {
        return mController;
    }
    @Override
    public MovingObjectPosition getPosition() {
        return mPosition; //I don't know how to deal it;
    }
    @Override
    public Vec3 getRenderingPosition() {
        return null; //I don't know how to deal it;
    }
    @Override
    public NBTTagCompound getNBTData() {
        return mNBT;
    }
    @Override
    public int getNBTInteger(NBTTagCompound tag, String keyname) {
        return 0; //I don't know how to deal it;
    }
    @Override
    public double getPartialFrame() {
        return 0; //I don't know how to deal it;
    }
    @Override
    public ForgeDirection getSide() {
        return null; //I don't know how to deal it;
    }
    @Override
    public ItemStack getStack() {
        return null; //I don't know how to deal it;
    }
}
