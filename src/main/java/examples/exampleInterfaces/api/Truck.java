package examples.exampleInterfaces.api;

public interface Truck extends Vehicle {
    int getPayloadPounds();
    default int getPayloadKg(){
        return convertPoundsToKg(getPayloadPounds());
    }
    static int convertKgToPounds(int kilograms){
        return (int) Math.round(2.205 * kilograms);
    }
    default int getWeightKg(int pounds){
        return convertPoundsToKg(pounds);
    }
    private int convertPoundsToKg(int pounds){
        return (int) Math.round(0.454 * pounds);
    }
}