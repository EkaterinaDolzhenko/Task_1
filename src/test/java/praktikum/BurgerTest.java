package praktikum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BurgerTest {
    private Burger burger;
    private Bun bun;
    private Ingredient sauce;
    private Ingredient filling;
    private Ingredient chiliSauce;

    @BeforeEach
    public void setUp() {
        burger = new Burger();
        sauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100);
        filling = new Ingredient(IngredientType.FILLING, "cutlet", 150);
        chiliSauce = new Ingredient(IngredientType.SAUCE, "chili sauce", 200);
    }

    @Test
    @DisplayName("Изменение добавленной булочки")
    public void testSetBunsOverwritesPreviousBun() {
        bun = new Bun("black bun", 100);

        // Устанавливаем первую булочку
        burger.setBuns(bun);
        Assertions.assertEquals("black bun", burger.bun.getName());

        // Устанавливаем вторую булочку
        Bun secondBun = new Bun("red bun", 300);
        burger.setBuns(secondBun);

        // Проверяем, что булочка перезаписалась
        Assertions.assertEquals("red bun", burger.bun.getName());
        Assertions.assertEquals(300, burger.bun.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Добавление одного ингредиента")
    public void testAddSingleIngredient() {
        // Вызываем тестируемый метод
        burger.addIngredient(sauce);

        // Проверяем, что ингредиент добавился
        Assertions.assertEquals(1, burger.ingredients.size(), "Размер списка должен быть 1");
        Assertions.assertSame(sauce, burger.ingredients.get(0), "Добавленный ингредиент должен совпадать");
    }

    @Test
    @DisplayName("Добавление двух одинаковых ингредиентов (разные объекты)")
    public void testAddDuplicateIngredients() {
        // Добавляем два одинаковых ингредиента
        Ingredient sameSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 100);

        burger.addIngredient(sauce);
        burger.addIngredient(sameSauce);

        // Проверяем, что оба добавились (разные объекты с одинаковыми данными)
        Assertions.assertEquals(2, burger.ingredients.size());
        Assertions.assertNotSame(sauce, sameSauce); // Разные объекты
        Assertions.assertEquals(sauce.getName(), burger.ingredients.get(1).getName());
    }

    @Test
    @DisplayName("Добавление нескольких ингредиентов")
    public void testAddMultipleIngredients() {
        // Добавляем несколько ингредиентов
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(new Ingredient(IngredientType.SAUCE, "chili sauce", 200));

        // Проверяем размер списка
        Assertions.assertEquals(3, burger.ingredients.size(), "Размер списка должен быть 3");

        // Проверяем порядок добавления
        Assertions.assertEquals("hot sauce", burger.ingredients.get(0).getName());
        Assertions.assertEquals("cutlet", burger.ingredients.get(1).getName());
        Assertions.assertEquals("chili sauce", burger.ingredients.get(2).getName());
    }

    @Test
    @DisplayName("Удаление первого ингредиента")
    public void testRemoveFirstIngredient() {
        // Добавляем 3 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2

        burger.removeIngredient(0);

        // Проверяем размер списка
        Assertions.assertEquals(2, burger.ingredients.size(), "После удаления должно остаться 2 ингредиента");

        // Проверяем, что оставшиеся ингредиенты на правильных позициях
        Assertions.assertEquals(filling, burger.ingredients.get(0), "На позиции 0 должен быть filling");
        Assertions.assertEquals(chiliSauce, burger.ingredients.get(1), "На позиции 1 должен быть chiliSauce");
        // Проверяем, что удалённый ингредиент отсутствует
        Assertions.assertFalse(burger.ingredients.contains(sauce), "sauce должен быть удалён");
    }

    @Test
    @DisplayName("Удаление ингредиента из середины списка")
    public void testRemoveMiddleIngredient() {
        // Добавляем 3 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2

        burger.removeIngredient(1);

        Assertions.assertEquals(2, burger.ingredients.size());

        // Проверяем смещение индексов после удаления
        Assertions.assertEquals(sauce, burger.ingredients.get(0), "sauce должен остаться на индексе 0");
        Assertions.assertEquals(chiliSauce, burger.ingredients.get(1), "chiliSauce должен сместиться на индекс 1");
        Assertions.assertFalse(burger.ingredients.contains(filling), "filling должен быть удалён");
    }

    @Test
    @DisplayName("Удаление последнего ингредиента")
    public void testRemoveLastIngredient() {
        // Добавляем 3 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2

        burger.removeIngredient(2);

        Assertions.assertEquals(2, burger.ingredients.size());
        Assertions.assertEquals(sauce, burger.ingredients.get(0));
        Assertions.assertEquals(filling, burger.ingredients.get(1));
        Assertions.assertFalse(burger.ingredients.contains(chiliSauce));
    }

    @Test
    @DisplayName("Удаление из пустого списка")
    public void testRemoveFromEmptyList() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            burger.removeIngredient(0);
        }, "Удаление из пустого списка должно вызывать исключение");
    }

    @Test
    @DisplayName("Перемещение первого ингредиента в центр")
    public void testMoveFirstIngredient() {
        // Добавляем 3 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2

        burger.moveIngredient(0, 1);

        // Проверяем размер списка
        Assertions.assertEquals(3, burger.ingredients.size(), "После перемещения количество не должно измениться");

        // Проверяем, что все ингредиенты на правильных позициях
        Assertions.assertEquals(filling, burger.ingredients.get(0), "На позиции 0 должен быть filling");
        Assertions.assertEquals(sauce, burger.ingredients.get(1), "На позиции 1 должен быть sauce");
        Assertions.assertEquals(chiliSauce, burger.ingredients.get(2), "На позиции 2 должен быть chiliSauce");

    }

    @Test
    @DisplayName("Перемещение игредиентов в середине")
    public void testMoveIngredientsInTheMiddle() {
        Ingredient sausage = new Ingredient(IngredientType.FILLING, "sausage", 300);

        // Добавляем 4 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2
        burger.addIngredient(sausage);    // индекс 3

        burger.moveIngredient(2, 1);

        // Проверяем размер списка
        Assertions.assertEquals(4, burger.ingredients.size(), "После перемещения количество не должно измениться");

        // Проверяем, что все ингредиенты на правильных позициях
        Assertions.assertEquals(sauce, burger.ingredients.get(0), "На позиции 0 должен быть sauce");
        Assertions.assertEquals(chiliSauce, burger.ingredients.get(1), "На позиции 1 должен быть chiliSauce");
        Assertions.assertEquals(filling, burger.ingredients.get(2), "На позиции 2 должен быть filling");
        Assertions.assertEquals(sausage, burger.ingredients.get(3), "На позиции 3 должен быть sausage");
    }

    @Test
    @DisplayName("Перемещение последнего игредиента в начало")
    public void testMoveLastIngredientToFirst() {
        Ingredient sausage = new Ingredient(IngredientType.FILLING, "sausage", 300);

        // Добавляем 4 ингредиента
        burger.addIngredient(sauce);      // индекс 0
        burger.addIngredient(filling);    // индекс 1
        burger.addIngredient(chiliSauce); // индекс 2
        burger.addIngredient(sausage);    // индекс 3

        burger.moveIngredient(3, 0);

        // Проверяем размер списка
        Assertions.assertEquals(4, burger.ingredients.size(), "После перемещения количество не должно измениться");

        // Проверяем, что все ингредиенты на правильных позициях
        Assertions.assertEquals(sausage, burger.ingredients.get(0), "На позиции 0 должен быть sausage");
        Assertions.assertEquals(sauce, burger.ingredients.get(1), "На позиции 1 должен быть sauce");
        Assertions.assertEquals(filling, burger.ingredients.get(2), "На позиции 2 должен быть filling");
        Assertions.assertEquals(chiliSauce, burger.ingredients.get(3), "На позиции 3 должен быть chiliSauce");
    }

    @Test
    @DisplayName("Расчёт цены: только булочка (без ингредиентов)")
    public void testGetPriceOnlyBun() {
        // Дано: булочка стоит 100
        bun = new Bun("black bun", 100);
        burger.setBuns(bun);

        //Проверяем: цена = цена булочки * 2
        Assertions.assertEquals(200, burger.getPrice(), 0.001, "Цена должна быть 200 (две половинки булочки)");
    }

    @Test
    @DisplayName("Расчёт цены: булочка + один ингредиент")
    public void testGetPriceWithOneIngredient() {
        bun = new Bun("black bun", 100);
        burger.setBuns(bun);

        burger.addIngredient(sauce);

        //Проверяем: цена = цена булочки * 2 + цена ингредиента
        Assertions.assertEquals(300, burger.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Расчёт цены: булочка + несколько ингредиентов")
    public void testGetPriceWithMultipleIngredients() {
        bun = new Bun("white bun", 200);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(chiliSauce);

        //Проверяем: цена = (200 * 2) + 100 + 150 + 200 = 850
        Assertions.assertEquals(850, burger.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Расчёт цены: булочка + два одинаковых ингредиента")
    public void testGetPriceWithDuplicateIngredients() {
        bun = new Bun("black bun", 100);
        burger.setBuns(bun);
        burger.addIngredient(filling);
        burger.addIngredient(filling);

        //Проверяем: цена = (цена булочки * 2) + (цена ингредиента * 2)
        Assertions.assertEquals(500, burger.getPrice(), 0.001);
    }

    @Test
    @DisplayName("Чек для бургера без ингредиентов")
    public void testGetReceiptOnlyBun() {
        bun = new Bun("black bun", 100);
        burger.setBuns(bun);

        String expected = String.format(
                "(==== black bun ====)%n" +
                        "(==== black bun ====)%n" +
                        "%nPrice: 200,000000%n"
        );

        Assertions.assertEquals(expected, burger.getReceipt());
    }

    @Test
    @DisplayName("Чек для бургера с одним ингредиентом")
    public void testGetReceiptWithOneIngredient() {
        bun = new Bun("black bun", 100);
        burger.setBuns(bun);
        burger.addIngredient(sauce);

        String expected = String.format(
                "(==== black bun ====)%n" +
                        "= sauce hot sauce =%n" +
                        "(==== black bun ====)%n" +
                        "%nPrice: 300,000000%n"
        );

        Assertions.assertEquals(expected, burger.getReceipt());
    }
    
    @Test
    @DisplayName("Чек для бургера с несколькими ингредиентами")
    public void testGetReceiptWithMultipleIngredients() {
        bun = new Bun("white bun", 200);
        burger.setBuns(bun);

        burger.addIngredient(sauce);
        burger.addIngredient(filling);
        burger.addIngredient(chiliSauce);

        String expected = String.format(
                "(==== white bun ====)%n" +
                        "= sauce hot sauce =%n" +
                        "= filling cutlet =%n" +
                        "= sauce chili sauce =%n" +
                        "(==== white bun ====)%n" +
                        "%nPrice: 850,000000%n"
        );

        Assertions.assertEquals(expected, burger.getReceipt());
    }
}