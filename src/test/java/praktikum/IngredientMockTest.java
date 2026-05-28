package praktikum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientMockTest {
    @Mock
    Ingredient mockIngredient;

    @Test
    @DisplayName("Проверка, что getPrice() вызывается для каждого ингредиента (с моком)")
    public void testIngredientGetPriceCalled() {
        //Задаем мок ингредиентов
        when(mockIngredient.getPrice()).thenReturn(100f);

        Bun bun = new Bun("black bun", 100);
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);

        //вызываем getPrice
        float price = burger.getPrice();
        //метод getPrice у ингредиента должен вызваться 1 раз
        verify(mockIngredient, times(1)).getPrice();
        Assertions.assertEquals(300f, price, 0.001); // 200 + 100
    }

    @Test
    @DisplayName("Мок-тест: проверка вызова getType() и getName() при формировании чека")
    public void testIngredientMethodsCalledInReceipt() {
        // Дано: мок ингредиента
        when(mockIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient.getName()).thenReturn("mock sauce");

        Bun bun = new Bun("black bun", 100);
        Burger burger = new Burger();
        burger.setBuns(bun);
        burger.addIngredient(mockIngredient);

        //получаем чек
        String receipt = burger.getReceipt();

        //методы должны вызваться
        verify(mockIngredient, atLeastOnce()).getType();
        verify(mockIngredient, atLeastOnce()).getName();

        // Проверяем, что в чеке есть данные из мока
        Assertions.assertTrue(receipt.contains("= sauce mock sauce ="));
    }
}
