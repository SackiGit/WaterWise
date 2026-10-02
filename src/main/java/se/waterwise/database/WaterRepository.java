package se.waterwise.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class WaterRepository {

    private final JdbcTemplate jdbcTemplate;

    public WaterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getDailyConsumption(int userId) {
        String sql = """
                SELECT Datum, TotalLitres
                FROM DailyConsumption
                WHERE Userid = ?
                ORDER BY Datum
                """;

        return jdbcTemplate.queryForList(sql, userId);
    }
    public Map<String, Object> getTotalConsumption(int userId) {
        String sql = """
            SELECT TotalLitres
            FROM TotalConsumption
            WHERE Userid = ?
            """;

        return jdbcTemplate.queryForMap(sql, userId);
    }
    public void addConsumption(int userId, String activity, int duration, LocalDate date){
        String sql = """
                Insert INTO ConsMinutes (Userid, Activity, Duration, Datum)
                VALUES(?,?,?,?)
                """;
        jdbcTemplate.update(sql,userId,activity,duration,date);
    }
}