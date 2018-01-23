package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.data.DataAccessLayer;
import io.swagger.helper.UrlHelper;
import io.swagger.model.Order;
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
public class OrderApiController implements OrderApi {

    public ResponseEntity<Void> createOrder(@ApiParam(value = "order of pizzas" ,required=true ) @RequestBody Order body, HttpServletRequest request) {

        if(body.getOrderItems() == null || body.getOrderItems().size() == 0)
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        if(StringUtils.isEmpty(body.getRecipient()))
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        boolean isAdded = DataAccessLayer.addOrder(body);
        if(!isAdded)
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        try {
            return ResponseEntity.<Void>created(URI.create(UrlHelper.getURLBase(request) + "/v1/order/" + body.getId())).build();
        } catch (MalformedURLException e) { }
        return ResponseEntity.<Void>created(URI.create("/v1/order/" + body.getId())).build();
    }

    public ResponseEntity<Void> deleteOrder(@ApiParam(value = "ID of the order to be deleted",required=true ) @PathVariable("orderId") Long orderId) {
        boolean isDeleted = DataAccessLayer.deleteOrder(orderId);
        if(!isDeleted)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Order> getOrderById(@ApiParam(value = "ID of order to be returned",required=true ) @PathVariable("orderId") Long orderId) {
        Order order = DataAccessLayer.getOrderById(orderId);
        if(order == null)
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getOrders() {
        List<Order> orders = DataAccessLayer.getAllOrders();
        List<Integer> orderIds = new ArrayList<>();
        for (Order order :
                orders) {
            orderIds.add(Integer.parseInt(order.getId().toString()));
        }

        if(orderIds.size() == 0)
            return new ResponseEntity<List<Integer>>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<Integer>>(orderIds, HttpStatus.OK);
    }

}
