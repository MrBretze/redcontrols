package crazypants.enderio.conduit;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//import com.enderio.core.common.util.BlockCoord;

//import crazypants.enderio.conduit.geom.CollidableCache.CacheKey;
//import crazypants.enderio.conduit.geom.CollidableComponent;

public interface IConduit {

    // Base functionality
    Class<? extends IConduit> getBaseConduitType();

    ItemStack createItem();

    List<ItemStack> getDrops();

    int getLightValue();

    boolean isActive();

    void setActive(boolean active);

    void writeToNBT(NBTTagCompound conduitBody);

    void readFromNBT(NBTTagCompound conduitBody, short nbtVersion);

    // Container

    //void setBundle(IConduitBundle tileConduitBundle);

    //IConduitBundle getBundle();

    void onAddedToBundle();

    void onRemovedFromBundle();

    //BlockCoord getLocation();

    // Conections
    boolean hasConnections();

    boolean hasExternalConnections();

    boolean hasConduitConnections();

    // Conduit Connections

    boolean canConnectToConduit(EnumFacing direction, IConduit conduit);

    Set<EnumFacing> getConduitConnections();

    boolean containsConduitConnection(EnumFacing dir);

    void conduitConnectionAdded(EnumFacing fromDirection);

    void conduitConnectionRemoved(EnumFacing fromDirection);

    void connectionsChanged();

    //AbstractConduitNetwork<?, ?> getNetwork();

    //boolean setNetwork(AbstractConduitNetwork<?, ?> network);

    // External Connections

    boolean canConnectToExternal(EnumFacing direction, boolean ignoreConnectionMode);

    Set<EnumFacing> getExternalConnections();

    boolean containsExternalConnection(EnumFacing dir);

    void externalConnectionAdded(EnumFacing fromDirection);

    void externalConnectionRemoved(EnumFacing fromDirection);

    boolean isConnectedTo(EnumFacing dir);

    //ConnectionMode getConnectionMode(ForgeDirection dir);

    //void setConnectionMode(ForgeDirection dir, ConnectionMode mode);

    //boolean hasConnectionMode(ConnectionMode mode);

    //ConnectionMode getNextConnectionMode(ForgeDirection dir);

    //ConnectionMode getPreviousConnectionMode(ForgeDirection dir);

    // rendering, only needed us default rendering is used

    //IIcon getTextureForState(CollidableComponent component);

    //IIcon getTransmitionTextureForState(CollidableComponent component);

    float getTransmitionGeometryScale();

    //float getSelfIlluminationForState(CollidableComponent component);

    // geometry

    boolean haveCollidablesChangedSinceLastCall();

    //Collection<CollidableComponent> getCollidableComponents();

    //Collection<CollidableComponent> createCollidables(CacheKey key);

    Class<? extends IConduit> getCollidableType();

    // Actions

    //boolean onBlockActivated(EntityPlayer player, RaytraceResult res, List<RaytraceResult> all);

    void onChunkUnload(World worldObj);

    void updateEntity(World worldObj);

    boolean onNeighborBlockChange(Block blockId);

    boolean onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos otherPos);

    //For Copy/Paste of connection settings
    boolean writeConnectionSettingsToNBT(EnumFacing dir, NBTTagCompound nbt);

    boolean readConduitSettingsFromNBT(EnumFacing dir, NBTTagCompound nbt);

    //public AbstractConduitNetwork<?, ?> createNetworkForType();

    /**
     * Should the texture of the conduit connectors be mirrored around the conduit
     * node?
     */
    boolean shouldMirrorTexture();

}