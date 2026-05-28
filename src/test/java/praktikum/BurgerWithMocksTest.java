package praktikum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BurgerWithMocksTest {
    private Burger burger;

    @Mock
    Bun mockBun;

    @BeforeEach
    public void setUp() {
        burger = new Burger();
    }

    @Test
    @DisplayName("Проверка, что цена = цена булочки * 2 (с моком)")
    public void testGetPriceCalculatesBunPriceCorrectly() {
        when(mockBun.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);

        float price = burger.getPrice();

        // Проверяем, что метод getPrice() вызвался 1 раз
        verify(mockBun, times(1)).getPrice();
        // Проверяем, что цена рассчиталась как цена_булочки * 2
        Assertions.assertEquals(200f, price, 0.001);
    }
    @Test
    @DisplayName("Мок-тест: проверка, что getName() у булочки вызывается при получении чека")
    public void testBunGetNameCalledInReceipt() {
        when(mockBun.getName()).thenReturn("mock bun");
        when(mockBun.getPrice()).thenReturn(100f);

        burger.setBuns(mockBun);

        //получаем чек
        burger.getReceipt();

        //метод getName должен вызваться 2 раза (верхняя и нижняя булочка)
        verify(mockBun, times(2)).getName();
    }
}
