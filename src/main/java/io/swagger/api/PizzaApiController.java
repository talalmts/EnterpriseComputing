package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.data.DataAccessLayer;
import io.swagger.exceptions.InvalidPizzaException;
import io.swagger.helper.UrlHelper;
import io.swagger.model.Pizza;
import io.swagger.model.Topping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-11-19T00:27:22.232Z")

@Controller
public class PizzaApiController implements PizzaApi {

    public ResponseEntity<Void> addPizza(@ApiParam(value = "Pizza that should be added to the menu" ,required=true ) @RequestBody Pizza body, HttpServletRequest request) {
        if(StringUtils.isEmpty(body.getName()))
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        boolean isAdded = DataAccessLayer.addPizza(body);
        if(!isAdded)
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        try {
            return ResponseEntity.<Void>created(URI.create(UrlHelper.getURLBase(request) + "/v1/pizza/" + body.getId())).build();
        } catch (MalformedURLException e) { }
        return ResponseEntity.<Void>created(URI.create("/v1/pizza/" + body.getId())).build();
    }

    public ResponseEntity<Void> createTopping(@ApiParam(value = "ID of pizza to update",required=true ) @PathVariable("pizzaId") Long pizzaId,
        @ApiParam(value = "Topping to be added to the pizza" ,required=true ) @RequestBody Topping body, HttpServletRequest request) {

        if(StringUtils.isEmpty(body.getName()) || body.getPrice().equals(0)) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        Pizza pizza = DataAccessLayer.getPizzaById(pizzaId);
        if(pizza == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        boolean isToppingAdded = pizza.addTopping(body);
        if(!isToppingAdded)
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        try {
            return ResponseEntity.<Void>created(URI.create(UrlHelper.getURLBase(request) + "/v1/pizza/" + pizzaId + "/topping/" + body.getId())).build();
        } catch (MalformedURLException e) { }
        return ResponseEntity.<Void>created(URI.create("/v1/pizza/" + pizzaId + "/topping/" + body.getId())).build();
    }

    public ResponseEntity<Void> deletePizza(@ApiParam(value = "Id of pizza to delete.",required=true ) @PathVariable("pizzaId") Long pizzaId) {
        boolean isDeleted = DataAccessLayer.deletePizza(pizzaId);
        if(!isDeleted)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleteToppingById(@ApiParam(value = "ID of the pizza.",required=true ) @PathVariable("pizzaId") Long pizzaId,
        @ApiParam(value = "ID of the topping.",required=true ) @PathVariable("toppingId") Long toppingId) {
        Pizza pizza = DataAccessLayer.getPizzaById(pizzaId);
        if(pizza == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

        boolean deleteTopping = pizza.deleteTopping(toppingId);

        if(!deleteTopping)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Pizza> getPizzaById(@ApiParam(value = "ID of pizzas",required=true ) @PathVariable("pizzaId") Long pizzaId) {
        Pizza pizza = DataAccessLayer.getPizzaById(pizzaId);
        if(pizza == null)
            return new ResponseEntity<Pizza>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Pizza>(pizza, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getPizzas() {
        List<Pizza> allPizza = DataAccessLayer.getAllPizza();
        List<Integer> ids = new ArrayList<>();

        for (Pizza pizza : allPizza) {
            ids.add(Integer.parseInt(pizza.getId().toString()));
        }

        return new ResponseEntity<List<Integer>>(ids, HttpStatus.OK);
    }

    public ResponseEntity<Topping> getToppingById(@ApiParam(value = "ID of the pizza.",required=true ) @PathVariable("pizzaId") Long pizzaId,
        @ApiParam(value = "ID of the topping.",required=true ) @PathVariable("toppingId") Long toppingId) {
        Pizza pizza = DataAccessLayer.getPizzaById(pizzaId);
        if(pizza == null)
            return new ResponseEntity<Topping>(HttpStatus.NOT_FOUND);

        Topping topping = pizza.getTopping(toppingId);
        if(topping == null)
            return new ResponseEntity<Topping>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Topping>(topping, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> listToppings(@ApiParam(value = "ID of pizza",required=true ) @PathVariable("pizzaId") Long pizzaId) {
        Pizza pizza = DataAccessLayer.getPizzaById(pizzaId);
        if(pizza == null)
            return new ResponseEntity<List<Integer>>(HttpStatus.NOT_FOUND);

        List<Integer> toppingIds = new ArrayList<>();
        for (Topping topping :
                pizza.getToppings()) {
            toppingIds.add(Integer.parseInt(topping.getId().toString()));
        }
        return new ResponseEntity<List<Integer>>(toppingIds, HttpStatus.OK);
    }

    public ResponseEntity<Void> updatePizza(@ApiParam(value = "Pizza that should be modified" ,required=true ) @RequestBody Pizza body,
        @ApiParam(value = "ID of pizzas",required=true ) @PathVariable("pizzaId") Long pizzaId) {
        if(StringUtils.isEmpty(body.getName()))
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        boolean updatePizza = false;
        try {
            updatePizza = DataAccessLayer.updatePizza(pizzaId, body);
            if(!updatePizza)
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (InvalidPizzaException e) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

}
