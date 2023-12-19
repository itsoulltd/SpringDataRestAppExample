package com.infoworks.lab.client;

import com.infoworks.lab.client.datasource.Any;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Passenger extends Any<Long> {
    private String name;
    private String sex = "NONE";
    private int age = 18;
    private Date dob = new java.sql.Date(new Date().getTime());
    private boolean active;

    public Passenger() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void unmarshallingFromMap(Map<String, Object> data, boolean inherit) {
        Object dob = data.get("dob");
        if (dob != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                Date parsed = formatter.parse(dob.toString());
                data.put("dob", parsed);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
        super.unmarshallingFromMap(data, inherit);
    }
}
