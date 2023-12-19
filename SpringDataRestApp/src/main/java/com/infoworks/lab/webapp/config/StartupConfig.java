package com.infoworks.lab.webapp.config;

import com.infoworks.lab.domain.entities.Passenger;
import com.infoworks.lab.jsql.ExecutorType;
import com.infoworks.lab.jsql.JsqlConfig;
import com.it.soul.lab.sql.SQLExecutor;
import com.it.soul.lab.sql.query.QueryType;
import com.it.soul.lab.sql.query.SQLQuery;
import com.it.soul.lab.sql.query.SQLSelectQuery;
import com.it.soul.lab.sql.query.models.Predicate;
import com.it.soul.lab.sql.query.models.Table;
import com.it.soul.lab.sql.query.models.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class StartupConfig implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //How to use executor:
        //
    }
}
