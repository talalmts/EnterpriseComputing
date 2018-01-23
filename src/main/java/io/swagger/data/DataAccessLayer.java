package io.swagger.data;

import io.swagger.exceptions.InvalidPizzaException;
import io.swagger.model.Order;
import io.swagger.model.OrderItem;
import io.swagger.model.Pizza;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DataAccessLayer {

    private static long pizzaCounter = 0;
    private static long orderCounter = 0;
    private static long toppingCounter = 0;

    private static ArrayList<Pizza> pizzas = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();

    public static boolean addPizza(Pizza pizza) {
        if(pizza.getSize() == null)
            return false;
        pizza.setId(getNewPizzaId());
        pizza.setPrice(getPizzaBasePrice(pizza));
        if (pizza.getPrice() == null)
            return false;
        return pizzas.add(pizza);
    }

    public static boolean addOrder(Order order){
        order.setId(getNewOrderId());
        double totalprice = 0;
        for (OrderItem item :
                order.getOrderItems()) {
            Pizza pizza = getPizzaById(item.getPizzaId());
            double price = pizza.getPrice().doubleValue() * (double) item.getQuantity();
            totalprice += price;
        }

        if(order.getOrderItems() == null || order.getOrderItems().size() == 0)
            return false;

        order.setTotalPrice(BigDecimal.valueOf(totalprice));
        return orders.add(order);
    }

    public static ArrayList<Pizza> getAllPizza() {
        return pizzas;
    }

    public static ArrayList<Order> getAllOrders() {
        return orders;
    }

    public static Pizza getPizzaById(Long pizzaId) {
        for (Pizza pizza : pizzas) {
            if (pizza.getId().equals(pizzaId))
                return pizza;
        }
        return null;
    }

    public static Order getOrderById(Long orderId){
        for (Order order: orders) {
            if (order.getId().equals(orderId))
                return order;
        }
        return null;
    }

    public static boolean updatePizza(Long pizzaId, Pizza updatedPizza) throws InvalidPizzaException {
        for (Pizza pizza : pizzas) {
            if (pizza.getId().equals(pizzaId)) {

                BigDecimal newPrice = getPizzaBasePrice(updatedPizza);
                if (newPrice == null)
                    throw new InvalidPizzaException();

                pizza.setName(updatedPizza.getName());
                pizza.setSize(updatedPizza.getSize());
                pizza.setPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    public static boolean deletePizza(Long pizzaId) {
        for (Pizza pizza : pizzas) {
            if (pizza.getId().equals(pizzaId)) {
                return pizzas.remove(pizza);
            }
        }
        return false;
    }

    public static boolean deleteOrder(Long orderId) {
        for (Order order: orders) {
            if (order.getId().equals(orderId)) {
                return pizzas.remove(order);
            }
        }
        return false;
    }

    public static long getNewOrderId(){
        return ++orderCounter;
    }

    public static long getNewPizzaId(){
        return ++pizzaCounter;
    }

    public static long getNewToppingId(){
        return ++toppingCounter;
    }

    private static BigDecimal getPizzaBasePrice(Pizza pizza) {
        switch (pizza.getSize()) {
            case LARGE:
                return new BigDecimal(8.5);
            case STANDARD:
                return new BigDecimal(5.0);
            default:
                return null;
        }
    }

}
