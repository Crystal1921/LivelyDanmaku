package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.item.ItemSanaeGohei;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class FiveStarEmitter extends Projectile {
    private int Time = 0;
    private int num = 0;
    public boolean isDrawStar = false;
    public boolean isShoot = false;
    private float XRot;
    private float YRot;
    private String type;
    private ArrayList<ItemSanaeGohei.DoublePoint> arrayList = new ArrayList<>();
    private final ArrayList<AbstractDanmaku> danmakuArrayList = new ArrayList<>();
    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level, ArrayList<ItemSanaeGohei.DoublePoint> arrayList, float XRot, float YRot, String string) {
        super(entityType, level);
        this.arrayList = arrayList;
        this.isDrawStar = true;
        this.XRot = XRot;
        this.YRot = YRot;
        this.type = string;
    }

    @Override
    public void tick() {
        if (isDrawStar) {
            Time++;
            if(Time == 1 && num <= arrayList.size() - 2) {
                Time = 0;
                num++;
                AbstractDanmaku danmaku;
                if (type.equals("danmaku")) {
                    danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(), level(), 0.25F);
                }   else {
                    danmaku = new StarDanmaku(EntityTypeRegistry.STAR_DANMAKU.get(), level());
                }
                danmakuArrayList.add(danmaku);
                danmaku.moveTo(this.getX() + arrayList.get(num).x,this.getY() + arrayList.get(num).z,this.getZ() + arrayList.get(num).y);
                level().addFreshEntity(danmaku);
            }
            if (isShoot) {
                danmakuArrayList.forEach((danmaku -> danmaku.shootFromRotation(XRot,YRot,0, 1,0,false)));
                isDrawStar = false;
                this.discard();
            }
        }
        if (this.tickCount >= 200) this.discard();
    }

    @Override
    protected void defineSynchedData() {
    }
}
