package com.tutorial.lively_danmaku.Entity;

import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.item.ItemSanaeGohei;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class FiveStarEmitter extends Projectile {
    private int Time = 0;
    private int num = 0;
    public boolean isDrawStar = false;
    public boolean isShoot = false;
    private Player player;
    private ArrayList<ItemSanaeGohei.DoublePoint> arrayList = new ArrayList<>();
    private final ArrayList<Danmaku> danmakuArrayList = new ArrayList<>();
    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level, ArrayList<ItemSanaeGohei.DoublePoint> arrayList, Player player) {
        super(entityType, level);
        this.arrayList = arrayList;
        this.isDrawStar = true;
        this.player = player;
    }

    @Override
    public void tick() {
        if (isDrawStar) {
            Time++;
            if(Time == 1 && num <= arrayList.size() - 2) {
                Time = 0;
                num++;
                Danmaku danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(),level(),0.25F);
                danmakuArrayList.add(danmaku);
                danmaku.moveTo(this.getX() + arrayList.get(num).x,this.getY() + arrayList.get(num).z,this.getZ() + arrayList.get(num).y);
                level().addFreshEntity(danmaku);
            }
            if (isShoot) {
                danmakuArrayList.forEach((danmaku -> danmaku.shootFromRotation(player,player.getXRot(),player.getYRot(),0, 1,0)));
                isDrawStar = false;
                this.discard();
            }
        }
    }

    @Override
    protected void defineSynchedData() {
    }
}
