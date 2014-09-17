package com.concurrent.Akka;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;

/**
 * 实现描述: EnergySourceImpl
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-09-16 17:42
 */
public class EnergySourceImpl implements EnergySource {

    private final long MAXLEVEL = 100L;
    private long level = MAXLEVEL;
    private long usageCount = 0L;

    @Override
    public long getUnitAvailable() {
        return level;
    }

    @Override
    public long getUsageConunt() {
        return usageCount;
    }

    @Override
    public void useEnergy(long units) {
        if (units > 0 && level - units >= 0) {
            System.out.println("Thread useEnergy:" + Thread.currentThread().getName());
            level -= units;
            usageCount++;
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Hello");
        EnergySource mySquarer =
                TypedActor.get(system).typedActorOf(
                        new TypedProps<EnergySourceImpl>(EnergySource.class, EnergySourceImpl.class));
        mySquarer.useEnergy(10);
        mySquarer.useEnergy(10);
        System.out.println(mySquarer.getUnitAvailable());
        /*TypedActor.get(system).stop(mySquarer);
        TypedActor.get(system).poisonPill(mySquarer);*/
        system.shutdown();
    }
}
