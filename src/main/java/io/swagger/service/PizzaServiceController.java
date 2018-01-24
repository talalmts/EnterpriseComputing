package io.swagger.service;

import io.swagger.annotations.ApiParam;
import io.swagger.helper.Util;
import io.swagger.model.Pizza;
import io.swagger.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class PizzaServiceController {

    @Autowired
    private PizzaRepository _pizzaRepository;

    @RequestMapping("service/pizza/get-all")
    public ArrayList<Pizza> getAllPizza(){
        Iterable<Pizza> allPizzas = this._pizzaRepository.findAll();
        return Util.toList(allPizzas);
    }

    @RequestMapping("service/pizza/add")
    public ResponseEntity<Boolean> add(@ApiParam(value = "Pizza that should be added to the menu" ,required=true ) @RequestBody Pizza body, HttpServletRequest request){
        try{
            body.setPrice(getPizzaBasePrice(body));
            this._pizzaRepository.save(body);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }

    private BigDecimal getPizzaBasePrice(Pizza pizza) {
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
