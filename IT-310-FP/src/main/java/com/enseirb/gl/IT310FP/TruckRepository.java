package com.enseirb.gl.IT310FP;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TruckRepository {

    private final Map<Long, Truck> trucks = new HashMap<>();

    public TruckRepository() {
        Calendar.getInstance().set(2022, Calendar.MARCH, 12);
        trucks.put(0L, new Truck("DF-122-PJ", Calendar.getInstance().getTime(), 3400));

        trucks.put(1L, new Truck("BB-421-GJ", new Date(), 11800));

        Calendar.getInstance().set(2022, Calendar.SEPTEMBER, 21);
        trucks.put(2L, new Truck("ZT-094-OA", Calendar.getInstance().getTime(), 7200));

        Calendar.getInstance().set(2021, Calendar.JANUARY, 4);
        trucks.put(4L, new Truck("HI-278-HO", Calendar.getInstance().getTime(), 10400));
    }

    public Truck getById(long truckId) {
        Truck truck = trucks.get(truckId);
        if(truck == null) {
            throw new TruckNotFoundException("truck with id " + truckId + " not found");
        }
        return truck;
    }
}
