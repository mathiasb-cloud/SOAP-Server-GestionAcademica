package cibertec.pe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/validar/{apiKey}")
    public ResponseEntity<String> validarApiKey(@PathVariable String apiKey) {
        try {
            String sql = "SELECT COUNT(*) FROM api_keys WHERE api_key = ? AND estado = 1";
            Integer count = jdbcTemplate.queryForObject(sql, new Object[]{apiKey}, Integer.class);
            if (count != null && count > 0) {
                return ResponseEntity.ok("valido");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalido");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
        }
    }
}