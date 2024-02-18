package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.util.ColorPoint;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.ArrayList;

public class FiveStarEmitter extends Projectile {
    private int cycleNum = 4;
    private int num = 0;
    public boolean isDrawStar = false;
    public boolean isShoot = false;
    private float XRot;
    private float YRot;
    private String type;
    private ArrayList<ColorPoint> arrayList = new ArrayList<>();
    private final ArrayList<AbstractDanmaku> danmakuArrayList = new ArrayList<>();
    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public FiveStarEmitter(EntityType<? extends Projectile> entityType, Level level, ArrayList<ColorPoint> arrayList, float XRot, float YRot, String string) {
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
            if(num <= arrayList.size() - 2 - cycleNum) {
                for (int i = 0; i < cycleNum; i++) {
                    num++;
                    AbstractDanmaku danmaku;
                    if (type.equals("danmaku")) {
                        danmaku = new NormalDanmaku(EntityTypeRegistry.DANMAKU.get(), level(), 0.25F, new Color(arrayList.get(num).color));
                    }   else {
                        danmaku = new StarDanmaku(EntityTypeRegistry.STAR_DANMAKU.get(), level());
                    }
                    danmaku.setIsTick(false);
                    danmakuArrayList.add(danmaku);
                    danmaku.moveTo(this.getX() + arrayList.get(num).x,this.getY() + arrayList.get(num).z,this.getZ() + arrayList.get(num).y);
                    level().addFreshEntity(danmaku);
                    if (num == arrayList.size() - 2) isShoot = true;
                }
            }
            if (isShoot) {
                danmakuArrayList.forEach((danmaku -> danmaku.shootFromRotation(XRot,YRot,0, 1,0,false)));
                danmakuArrayList.forEach((danmaku -> danmaku.setIsTick(true)));
                isDrawStar = false;
                this.discard();
            }
        }
        if (this.tickCount >= arrayList.size()) this.discard();
    }

    @Override
    protected void defineSynchedData() {
    }
}
