package com.infoworks.lab.client;

import com.infoworks.lab.client.datasource.*;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class DataRestClientTestCase {

    private static Logger LOG = Logger.getLogger("DataRestClientTestCase");

    public void doLoadTest() throws Exception {
        //
        RestTemplate template = new RestTemplate();
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class
                , url
                , template);

        dataSource.setEnableLogging(true);
        PaginatedResponse response = dataSource.load();

        Page page = response.getPage();

        Links links = response.getLinks();
        //Close:
        dataSource.close();
    }


    public void doAsyncLoadTest() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load((response) -> {
            //In-case of exception:
            if (response.getStatus() >= 400) {
                System.out.println(response.getError());
            }
            Page page = response.getPage();

            Links links = response.getLinks();
        });
        //Close:
        dataSource.close();
    }


    public void addSingleItem() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //
        Passenger newPassenger = new Passenger();
        newPassenger.setName("Sohana Islam Khan");
        newPassenger.setAge(28);
        newPassenger.setSex("FEMALE");
        newPassenger.setActive(true);
        newPassenger.setDob(new Date(Instant.now().minus(28 * 365, ChronoUnit.DAYS).toEpochMilli()));
        //Create:
        Object id = dataSource.add(newPassenger);
        //Close:
        dataSource.close();
    }


    public void updateSingleItem() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //
        Object[] passengers = dataSource.readSync(0, dataSource.size());
        //
        Passenger passenger = (Passenger) passengers[0];
        passenger.setName("Dr. Sohana Khan");
        //Update:
        Object id = passenger.parseId().orElse(null);
        if(id != null) dataSource.put(id, passenger);
        //Close:
        dataSource.close();
    }


    public void readTest() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        Passenger passenger = dataSource.read(1l);
        System.out.println(passenger.getName());
        //Close:
        dataSource.close();
    }


    public void sizeTest() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        int size = dataSource.size();
        System.out.println("Size is: " + size);
        //Close:
        dataSource.close();
    }


    public void readNextTest() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //
        Optional<List<Passenger>> passengers = dataSource.next();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //Close:
        dataSource.close();
    }


    public void readAsyncNextTest() throws Exception {
        //
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //
        dataSource.next((passengers) -> {
            //
            System.out.println("Passenger Found: " + passengers.isPresent());
        });
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //Close:
        dataSource.close();
    }


    public void CRUDTest() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        dataSource.load();
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        //
        Passenger newPassenger = new Passenger();
        newPassenger.setName("Sohana Islam Khan");
        newPassenger.setAge(28);
        newPassenger.setSex("FEMALE");
        newPassenger.setActive(true);
        newPassenger.setDob(new Date(Instant.now().minus(28 * 365, ChronoUnit.DAYS).toEpochMilli()));
        //Create:
        Object id = dataSource.add(newPassenger);
        //Read One:
        Passenger read = dataSource.read(id);
        //Read from local:
        Object[] items = dataSource.readSync(0, dataSource.size());
        Stream.of(items).forEach(item -> {
            if (item instanceof Passenger)
                System.out.println(((Passenger) item).getName());
        });
        //Update:
        newPassenger.setName("Dr. Sohana Islam Khan");
        dataSource.put(id, newPassenger);
        //Read again: (will read from local)
        Passenger readAgain = dataSource.read(id);
        System.out.println(readAgain.getName());
        //Delete:
        System.out.println("Count before delete: " + dataSource.size());
        dataSource.remove(id);
        System.out.println("Count after delete: " + dataSource.size());
        //
        System.out.println("Is last page: " + dataSource.isLastPage());
        dataSource.close();
    }


    public void readAllPages() throws Exception {
        URL url = new URL("http://localhost:8080/api/data/passengers");
        DataRestClient<Passenger> dataSource = new DataRestClient(Passenger.class, url);
        //Read All Pages Until last page:
        dataSource.load();
        Optional<List<Passenger>> opt;
        do {
            opt = dataSource.next();
            System.out.println("Current Page: " + dataSource.currentPage());
            System.out.println("Local Size: " + dataSource.size());
        } while (opt.isPresent());
        //
        Object[] all = dataSource.readSync(0, dataSource.size());
        Stream.of(all).forEach(item -> {
            if (item instanceof Passenger)
                System.out.println(((Passenger) item).getName());
        });
        //Close:
        dataSource.close();
    }

}
