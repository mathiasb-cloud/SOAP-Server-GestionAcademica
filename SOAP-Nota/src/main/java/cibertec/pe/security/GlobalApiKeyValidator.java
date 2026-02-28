package cibertec.pe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GlobalApiKeyValidator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean validate(String apiKey) {
        String sql = "SELECT COUNT(*) FROM api_keys WHERE api_key = ? AND estado = 1";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{apiKey}, Integer.class);
        return count != null && count > 0;
    }
}