package praktikum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;


import java.util.stream.Stream;

public class BurgerAddIngredientParameterizedTest {

    // Метод-источник данных, который берёт данные из Database
    static Stream<Arguments> getIngredientData() {
        Database database = new Database();
        return database.availableIngredients().stream()
                .map(ingredient -> Arguments.of(
                        ingredient.getType(),
                        ingredient.getName(),
                        ingredient.getPrice()
                ));
    }

    @ParameterizedTest
    @MethodSource("getIngredientData")
    public void testAddDifferentIngredients(IngredientType type, String name, float price) {
        Burger burger = new Burger();
        Ingredient ingredient = new Ingredient(type, name, price);

        burger.addIngredient(ingredient);

        Assertions.assertEquals(1, burger.ingredients.size());
        Assertions.assertEquals(type, burger.ingredients.get(0).getType());
        Assertions.assertEquals(name, burger.ingredients.get(0).getName());
        Assertions.assertEquals(price, burger.ingredients.get(0).getPrice(), 0.001);
    }
}