package com.example.tacocloud;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcTacoOrderRepository implements TacoOrderRepository{

    private JdbcOperations jdbcOperations;

    public JdbcTacoOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        var pscf = new PreparedStatementCreatorFactory(
        "insert into TACO_ORDER" +
                "(delivery_name, delivery_street, delivery_city," +
                "delivery_state, delivery_zip, credit_card_number," +
                "credit_card_expiration, credit_card_cvv, placed_at)" +
                "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        order.setPlaceAt(new Date());
        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCreditCardNumber(),
                        order.getCreditCardExpiration(),
                        order.getCreditCardCVV(),
                        order.getPlaceAt()));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        var tacos = order.getTacos();
        int i = 0;
        for (var taco : tacos) {
            saveTaco(orderId, i++, taco);
        }

        return order;
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        var pscf = new PreparedStatementCreatorFactory(
                "insert into TACO (NAME, TACO_ORDER, TACO_ORDER_KEY, CREATED_AT)" +
                        "values (?, ?, ?, ?)",
                Types.VARCHAR, Type.LONG, Type.LONG, Types.TIMESTAMP
        );
        pscf.setReturnGeneratedKeys(true);

        taco.setCreatedAt(new Date());
        var psc = pscf.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        orderId,
                        orderKey,
                        taco.getCreatedAt()));

        var keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    private void saveIngredientRefs(Long tacoId, List<Ingredient> ingredients) {
        int key = 0;
        for (var ingredient : ingredients) {
            jdbcOperations.update("insert into INGREDIENT_REF (ingredient, taco, taco_key) values (?,?,?)",
                    ingredient.getId(), tacoId, key++);
        }
    }

}
