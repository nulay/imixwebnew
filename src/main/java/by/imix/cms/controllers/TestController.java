package by.imix.cms.controllers;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@ConfigurationProperties
@RestController
@Api(tags = "TestController")
@RequiredArgsConstructor
public class TestController {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.user}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    protected SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();

    }

    @Autowired
    private CommonDataSource dataSource;

    @GetMapping("/index")
    String index() {
        return "index";
    }

    @GetMapping("/hello")
    String hello(Map<String, Object> model) {

        List list = List.of("hello Miha", "hello Ira", "hello Denis");

        model.put("records", list);
        return "db";
    }

    @GetMapping("/createdb")
    String db(Map<String, Object> model) {
        String template = getSession().doReturningWork(new ReturningWork<String>() {
            @Override
            public String execute(Connection con) throws SQLException {
                // do something useful
                try (Statement stmt = con.createStatement()) {
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (" +
                            " task_id SERIAL PRIMARY KEY,\n" +
                            "    title VARCHAR(255) NOT NULL,\n" +
                            "    start_date DATE,\n" +
                            "    due_date DATE,\n" +
                            "    status SMALLINT NOT NULL,\n" +
                            "    priority SMALLINT NOT NULL,\n" +
                            "    description TEXT,\n" +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                            ")");
                    model.put("message", "Таблица успешно создана");
                    return "dbcreate";
                } catch (Exception e) {
                    model.put("message", e.getMessage());
                    return "error";
                }
            }

        });
        return template;
    }

//    @GetMapping("/readdb")
//    String dbRead(Map<String, Object> model) {
//        try (Connection connection = dataSource.getConnection()) {
//            Statement stmt = connection.createStatement();
//
//            ResultSet rs = stmt.executeQuery("SELECT * FROM ticks");
//
//            ArrayList<String> output = new ArrayList<String>();
//            while (rs.next()) {
//                output.add("Read from DB: " + rs.getString("title") + " Статус: " + rs.getString("status"));
//            }
//
//            model.put("records", output);
//            return "db";
//        } catch (Exception e) {
//            model.put("message", e.getMessage());
//            return "error";
//        }
//    }
//
//    @GetMapping("/insticket")
//    boolean insertTicket(@RequestParam String newTick, @RequestParam String state) {
//        try (Connection connection = dataSource.getConnection()) {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("INSERT INTO ticks (title,start_date,status,priority) VALUES ('" + newTick + "',now(), '" + state + "', '1')");
//            return true;
//        } catch (Exception e) {
//
//            return false;
//        }
//    }

//    @Bean
//    public DataSource dataSource() throws SQLException {
//        if (dbUrl == null || dbUrl.isEmpty()) {
//            return new HikariDataSource();
//        } else {
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(dbUrl);
//            config.setUsername(userName);
//            config.setPassword(password);
//            return new HikariDataSource(config);
//        }
//    }
}
