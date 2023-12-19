package com.infoworks.lab;

import com.infoworks.lab.client.Passenger;
import com.infoworks.lab.client.datasource.DataRestClient;
import com.infoworks.lab.client.datasource.Page;
import com.infoworks.lab.client.datasource.PaginatedResponse;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws Exception {
        //
        URL url = new URL("http://localhost:8080/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        //dataSource.setEnableLogging(true);
        //DOC: load() will initialize the loading of a HATEOAS origin.
        PaginatedResponse response = dataSource.load();

        Page page = response.getPage();
        System.out.println("Current Page: " + page.getNumber());

        System.out.println("Number of item on Remote: " + page.getTotalElements());
        if (page.getTotalElements() <= 0) {
            //Let's insert a new Item:
            Passenger newPassenger = new Passenger();
            newPassenger.setName("Sohana Islam Khan");
            newPassenger.setAge(28);
            newPassenger.setSex("FEMALE");
            newPassenger.setActive(true);

            Date dob = new Date(Instant.now().minus(28 * 365, ChronoUnit.DAYS).toEpochMilli());
            newPassenger.setDob(dob);
            //Create:
            Object id = dataSource.add(newPassenger);
            if(id != null)
                System.out.println("New Item created with id: " + id.toString());
        }

        //Read data from cache:
        Object[] rows = dataSource.readSync(0, dataSource.size());
        System.out.println("Row found in cache: " + rows.length);
        Arrays.asList(rows).forEach(passenger -> {
            if (passenger instanceof Passenger) {
                System.out.println(String.format("Name: %s, Age: %s, Sex: %s"
                        , ((Passenger) passenger).getName()
                        , ((Passenger) passenger).getAge()
                        , ((Passenger) passenger).getSex()));
            }
        });

        //Close:
        dataSource.close();
    }

}
