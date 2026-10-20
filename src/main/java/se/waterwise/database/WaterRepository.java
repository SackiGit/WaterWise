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

    public List<Map<String, Object>> getConsumptionByActivityByDate(int userId, LocalDate startDate, LocalDate endDate) {
        String sql = """
                SELECT Activity,
                SUM (TotalLitres) AS TotalLitres
                FROM DailyConsumptionByActivity
                WHERE Userid = ?
                AND
                Datum >= ?
                AND
                Datum <= ?
                GROUP BY Activity
                """;
        return jdbcTemplate.queryForList(sql, userId, startDate, endDate);
    }
    public Map<String, Object> getTotalConsumptionByDate(int userId, LocalDate startDate, LocalDate endDate) {
        String sql = """
                SELECT SUM (TotalLitres) AS TotalLitres
                FROM DailyConsumptionByActivity
                WHERE Userid = ?
                AND
                Datum >= ?
                AND
                Datum <= ?
                """;
        return jdbcTemplate.queryForMap(sql, userId, startDate, endDate);
    }

    public Map<String, Object> findUser(String username) {
        String sql = """
            SELECT Userid, UserName, UserPassword, Municipality
            FROM Users
            WHERE UserName = ?
            """;

        List<Map<String, Object>> users =
                jdbcTemplate.queryForList(sql, username);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public void createUser(String username, String password, String municipality){
        String sql = """
                INSERT INTO Users (UserName, UserPassword, Municipality)
                VALUES (?,?,?)
                """;
        jdbcTemplate.update(sql,username,password,municipality);
    }
    public void addCsv(int userId, int duration, LocalDate date){
        String sql = """
                INSERT INTO ConsMinutes (Userid, Activity, Duration, Datum)
                VALUES(?,'Other',?,?)
                """;
        jdbcTemplate.update(sql,userId,duration,date);
    }
    public void changeMunicipality(String municipality, int userId){
        String sql = """
                UPDATE Users SET Municipality = ?
                WHERE Userid = ?
                """;
        jdbcTemplate.update(sql, municipality, userId);
    }
    public void updateUser(String password, int userId){
        String sql = """
                UPDATE Users SET UserPassword = ?
                WHERE Userid = ?
                """;
        jdbcTemplate.update(sql,password, userId);
    }
    public Map<String, Object> getRecommendation() {
        String sql = """
                SELECT Recommendation
                FROM Recommendations
                ORDER BY RANDOM()
                LIMIT 1
                """;
        return jdbcTemplate.queryForMap(sql);
    }
    public Map<String, Object> getCoords(String municipality){
        String sql = """
                SELECT Lat,Long
                FROM MunicipalityCoords
                WHERE Municipality=?
                """;
        return jdbcTemplate.queryForMap(sql, municipality);
    }

    public List<Map<String, Object>> getMunConMonth(String municipality, LocalDate startDate, LocalDate endDate){
        String sql = """
                SELECT
                months.month,
                COALESCE(
                FLOOR(
                SUM(dc.TotalLitres)/NULLIF(COUNT(distinct dc.Userid),0)),0) AS avgCon
                FROM generate_series(1,12) AS months(month)
                LEFT JOIN(
                SELECT
                EXTRACT(MONTH FROM dc.Datum)::INT AS month,
                dc.Userid,
                dc.TotalLitres
                FROM DailyConsumption dc
                JOIN Users u ON dc.Userid = u.Userid
                WHERE u.Municipality = ?
                       AND dc.Datum >= ?
                       AND dc.Datum <= ?
                ) dc ON dc.month = months.month
                GROUP BY months.month
               ORDER BY months.month
               """;
        return jdbcTemplate.queryForList(sql,municipality, startDate, endDate);

    }
    public List<Map<String, Object>> getMunCon(String municipality, LocalDate startDate, LocalDate endDate){
        String sql = """
                SELECT
                dc.Datum,
                EXTRACT(MONTH FROM dc.Datum)::INT AS month,
                FLOOR(SUM(dc.TotalLitres)/COUNT(distinct dc.Userid)) AS avgCon
                FROM DailyConsumption dc
                JOIN Users u ON dc.Userid = u.Userid
                Where u.Municipality = ?
                AND dc.Datum >= ?
                AND dc.Datum <= ?
                GROUP BY dc.Datum
                ORDER BY dc.Datum
                """;
        return jdbcTemplate.queryForList(sql,municipality, startDate, endDate);

    }
}
