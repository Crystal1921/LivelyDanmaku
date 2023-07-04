package com.tutorial.crystal.Entity.ai;

import com.tutorial.crystal.Entity.Danmaku;
import com.tutorial.crystal.Entity.Hakurei_bullet;
import com.tutorial.crystal.Entity.Reimu;
import com.tutorial.crystal.init.EntityTypeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class FlyAndShoot extends Goal {
    private final Reimu reimu;
    private Random random = new Random();
    private final ArrayList<Hakurei_bullet> hakureiBullets = new ArrayList<>();
    private int attackTime;
    private int Time;
    private int StageTime;
    private final double flyHeight;
    public FlyAndShoot(Reimu reimu, int flyHeight) {
        this.reimu = reimu;
        this.flyHeight = flyHeight;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.reimu.getTarget();
        return livingentity != null && livingentity.isAlive() && this.reimu.canAttack(livingentity);
    }

    public void tick() {
        --this.attackTime;
        ++this.Time;
        LivingEntity living = this.reimu.getTarget();
        if (living != null) {
            reimu.setNoGravity(reimu.distanceTo(living) < 15);
            this.reimu.getLookControl().setLookAt(living, 10.0F, 10.0F);
            if (reimu.getEntityData().get(Reimu.REIMU_STAGE) == 1 && Time % 20 == 0) {
                reimu.setDeltaMovement(random.nextDouble(0.2) - 0.2,0,random.nextDouble(0.2) - 0.1);
                System.out.println(random.nextDouble(0.2) - 0.1);
            }
            if (living.getY() > reimu.getY() - flyHeight) {
                reimu.setDeltaMovement(reimu.getDeltaMovement().add(0,0.2,0));
            } else {
                reimu.setDeltaMovement(reimu.getDeltaMovement().multiply(1,0,1));
            }

            if (this.attackTime <= 0) {
                attackTime = 10;
                if (reimu.getEntityData().get(Reimu.REIMU_STAGE) == 3) {    //一般弹幕射击
                    Danmaku danmaku = new Danmaku(EntityTypeRegistry.DANMAKU.get(), reimu.getLevel());
                    danmaku.moveTo(reimu.getX(), reimu.getY(0.5), reimu.getZ());
                    ShootFromEntityToEntity(reimu,living,danmaku,1);
                    reimu.getLevel().addFreshEntity(danmaku);
                }
                if (reimu.getEntityData().get(Reimu.REIMU_STAGE) == 2) {    //梦想封印 集
                    for (int i = 0; i < 360; i += 18) {
                        for (int j = 0; j < 360; j += 18) {
                            Hakurei_bullet bullet = new Hakurei_bullet(EntityTypeRegistry.HAKUREI_BULLET.get(), reimu.getLevel(), 18 * i - 180, 18 * j - 180,140);
                            bullet.moveTo(reimu.getX(), reimu.getY(0.5), reimu.getZ());
                            bullet.shootFromRotation(reimu, i, j + Time, 0, 0.5F, 1);
                            reimu.getLevel().addFreshEntity(bullet);
                        }
                    }
                }
            }
            if (reimu.getEntityData().get(Reimu.REIMU_STAGE) == 1) {        //梦想天生
                StageTime++;
                if (StageTime <= 6) {
                    for (int i = 0; i < 6; i++) {
                        Hakurei_bullet bullet = new Hakurei_bullet(EntityTypeRegistry.HAKUREI_BULLET.get(),reimu.getLevel());
                        bullet.moveTo(reimu.getX() + (i * 0.5 + 2) * Math.sin(Math.toRadians(StageTime * 60)),reimu.getY(0.5),reimu.getZ() + (i * 0.2 + 2) * Math.cos(Math.toRadians(StageTime * 60)));
                        bullet.shootFromRotation(reimu,0,-StageTime * 60,0,0.02F,1);
                        hakureiBullets.add(bullet);
                        reimu.getLevel().addFreshEntity(bullet);
                    }
                }
                if (StageTime >= 20) StageTime = 0;
                hakureiBullets.forEach((hakurei_bullet -> {
                    if (hakurei_bullet.tickCount >= 50 && hakurei_bullet.tickCount <= 60) {
                        ShootFromEntityToEntity(hakurei_bullet,living,hakurei_bullet,0.4F);
                    }
                }));
            }
        }
    }
    public static void ShootFromEntityToEntity (Entity target, Entity source, ThrowableItemProjectile bullet, float speed) {
        double d1 = source.getX() - target.getX();
        double d2 = source.getY(0.5D) - target.getY(0.5D);
        double d3 = source.getZ() - target.getZ();
        // 计算水平方向上的角度（偏航角）
        float yaw = (float) (Math.atan2(d3, d1) * (180 / Math.PI)) - 90;
        // 计算垂直方向上的角度（俯仰角）
        float pitch = (float) -(Math.atan2(d2, Math.sqrt(d1 * d1 + d3 * d3)) * (180 / Math.PI));
        bullet.shootFromRotation(source, pitch,yaw, 0, speed, 1);
    }
}