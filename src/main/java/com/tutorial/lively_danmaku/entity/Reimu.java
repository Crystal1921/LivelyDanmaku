package com.tutorial.lively_danmaku.entity;

import com.tutorial.lively_danmaku.entity.ai.FlyAndShoot;
import com.tutorial.lively_danmaku.entity.ai.ReimuMoveControl;
import com.tutorial.lively_danmaku.init.EntityTypeRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Reimu extends PathfinderMob {
    public static final EntityDataAccessor<Integer> REIMU_STAGE = SynchedEntityData.defineId(Reimu.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> IS_GENERATED = SynchedEntityData.defineId(Reimu.class, EntityDataSerializers.BOOLEAN);
    private final int NUM = 6;
    private final float DEGREE = (360 / (float)NUM);
    private final int DISTANCE = 2;
    private double angle = 0;
    public ArrayList<YinYangOrb> yinYangOrbs = new ArrayList<>();
    public Reimu(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.moveControl = new ReimuMoveControl(this);
        this.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(ItemRegistry.HakureiGohei.get()));
        if (!this.getEntityData().get(IS_GENERATED)) {
            for (int i = 0; i < NUM; i++) {
                yinYangOrbs.add(new YinYangOrb(EntityTypeRegistry.YIN_YANG_ORB.get(), level));
                yinYangOrbs.get(i).moveTo(this.getX() + DISTANCE * Math.cos(angle + DEGREE * i),this.getY(),this.getZ() + DISTANCE * Math.sin(angle + DEGREE * i));
                level.addFreshEntity(yinYangOrbs.get(i));
                this.getEntityData().set(IS_GENERATED, true);
            }
        }
    }
    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE,32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new FlyAndShoot(this,2));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, player.class, true));
    }
    @Override
    public void tick() {
        super.tick();
        this.resetFallDistance();
        if (this.getHealth() <= 50) {
            this.getEntityData().set(REIMU_STAGE, 2);
        }
        angle += 4;
        for (int i = 0; i < NUM; i++) {
            yinYangOrbs.get(i).moveTo(this.getX() + DISTANCE * Math.cos(Math.toRadians(angle + DEGREE * i)),this.getY(),this.getZ() + DISTANCE * Math.sin(Math.toRadians(angle + DEGREE * i)));
            yinYangOrbs.get(i).setDeltaMovement(0,0,0);
        }
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(REIMU_STAGE, 1);
        this.getEntityData().define(IS_GENERATED, false);
    }
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        yinYangOrbs.forEach(Entity::discard);
    }
    public Level getLevel () {
        return this.level();
    }
}
