package dev.tilera.cwg.beta;

public interface IBetaChunkManager {
    
    double[] getHumidity();

    double[] getTemperature();

    double getTemperature(int var1, int var2);

    double[] getTemperatures(double[] var1, int var2, int var3, int var4, int var5);

}
