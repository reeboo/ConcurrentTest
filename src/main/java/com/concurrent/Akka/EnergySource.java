package com.concurrent.Akka;

/**
 * 实现描述: EnergySource
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-09-16 17:41
 */
public interface EnergySource {
    long getUnitAvailable();

    long getUsageConunt();

    void useEnergy(final long units);
}
