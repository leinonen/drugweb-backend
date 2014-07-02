package se.leinonen.drugweb.util;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import org.apache.log4j.Logger;

/**
 * Created by leinonen on 2014-03-27.
 */
public class EBeanConfig {

    static final Logger logger = Logger.getLogger(EBeanConfig.class);

    private static EBeanConfig instance;

    public static EBeanConfig getInstance(){
        if (instance == null){
            instance = new EBeanConfig();
        }
        return instance;
    }


    public void setup() {

        logger.info("Configuring EBEAN.");

        ServerConfig config = new ServerConfig();
        Settings settings = Settings.getInstance();

        String dsDefault = "datasource." +settings.getString("datasource.default");
        config.setName("default");

        // Define DataSource parameters
        DataSourceConfig datasource = new DataSourceConfig();
        datasource.setDriver(settings.getString(dsDefault + ".databaseDriver"));
        datasource.setUsername(settings.getString(dsDefault + ".username"));
        datasource.setPassword(settings.getString(dsDefault + ".password"));
        datasource.setUrl(settings.getString(dsDefault + ".databaseUrl"));
        datasource.setMinConnections(settings.getInteger(dsDefault + ".minConnections"));
        datasource.setMaxConnections(settings.getInteger(dsDefault + ".maxConnections"));
        datasource.setIsolationLevel(Transaction.READ_COMMITTED);
        datasource.setHeartbeatSql(settings.getString(dsDefault + ".heartbeatsql"));

        config.setDataSourceConfig(datasource);

        // set DDL options...
        config.setDdlGenerate(settings.getBoolean("ebean.ddl.generate"));
        config.setDdlRun(settings.getBoolean("ebean.ddl.run"));

        config.setDefaultServer(true);
        config.setRegister(true);

        // create the EbeanServer instance
        EbeanServer server = EbeanServerFactory.create(config);
    }
}
