package com.example.tacocloud;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient", (row, rowNum) -> new Ingredient(
                   row.getString("id"),
                   row.getString("name"),
                   Ingredient.Type.valueOf(row.getString("type")))
        );
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        var results = jdbcTemplate.query(
                "select id, name, type from Ingredient where id=?",
                (row, rowNum) -> new Ingredient(
                    row.getString("id"),
                    row.getString("name"),
                    Ingredient.Type.valueOf(row.getString("type"))),
                id
        );
        return results.size() > 0 ?  Optional.of(results.get(0)) : Optional.empty();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient (id, name, type) values(?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType());
        return ingredient;
    }
}
