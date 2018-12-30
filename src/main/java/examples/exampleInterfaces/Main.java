package examples.exampleInterfaces;

import examples.exampleInterfaces.api.Car;
import examples.exampleInterfaces.api.SpeedModel;
import examples.exampleInterfaces.api.Truck;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        double timeSec = 10.0;
        int horsePower = 246;
        int vehicleWeight = 4000;
        Properties drivingConditions = new Properties();
        drivingConditions.put("roadCondition", "Wet");
        drivingConditions.put("tireCondition", "New");
        SpeedModel speedModel = FactorySpeedModel.generateSpeedModel(drivingConditions);
        Car car = FactoryVehicle.
                buildCar(4, vehicleWeight, horsePower);
        car.setSpeedModel(speedModel);
        System.out.println("Car speed (" + timeSec + " sec) = "
                + car.getSpeedMph(timeSec) + " mph");
        Truck truck = FactoryVehicle.
                buildTruck(3300, vehicleWeight, horsePower);
        truck.setSpeedModel(speedModel);
        int kg = truck.getPayloadKg();
        System.out.println("Truck speed (" + timeSec + " sec) = "
                + truck.getSpeedMph(timeSec) + " mph " + kg + " kg "  + Truck.convertKgToPounds(kg) + " pounds ");
    }

}