package examples.exampleInterfaces.api;

@FunctionalInterface
public interface SpeedModel {
    double getSpeedMph(double timeSec, int weightPounds, int horsePower);
}
