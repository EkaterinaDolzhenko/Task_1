package praktikum;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;

public class BurgerSetBunsParameterizedTest {

    // Метод-источник данных, который берёт данные из Database
    static Stream<Arguments> getBunData() {
        Database database = new Database();
        return database.availableBuns().stream()
                .map(bun -> Arguments.of(bun.getName(), bun.getPrice()));
    }


    @ParameterizedTest
    @MethodSource("getBunData")
    public void testSetBunsWithDifferentParameters(String bunName, float bunPrice) {
        Burger burger = new Burger();
        Bun bun = new Bun(bunName, bunPrice);

        burger.setBuns(bun);

        Assertions.assertEquals(bunName, burger.bun.getName());
        Assertions.assertEquals(bunPrice, burger.bun.getPrice(), 0.001);
    }
}