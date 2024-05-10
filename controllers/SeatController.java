package cinema.controllers;

import cinema.errors.ErrorDetails;
import cinema.exceptions.AlreadyPurchasedException;
import cinema.exceptions.WrongRowOrColumnNumberException;
import cinema.exceptions.WrongTocketException;
import cinema.model.Purchase;
import cinema.model.Seat;
import cinema.model.SeatAll;
import cinema.processors.LoginProcessor;
import cinema.services.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SeatController {

    private final PurchaseService purchaseService;
    private final LoginProcessor loginProcessor;

    public SeatController(PurchaseService purchaseService, LoginProcessor loginProcessor) {
        this.purchaseService = purchaseService;
        this.loginProcessor = loginProcessor;
    }

    @GetMapping("/seats")
    public SeatAll findAll() {
      return purchaseService.getSeatAll();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        try {
            String token = UUID.randomUUID().toString();
            Purchase purchase = new Purchase();
            purchase.setToken(token);
            purchase.setTicket(purchaseService.purchaseSeat(seat));
            purchaseService.addPurchase(purchase);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(purchase);
        } catch (WrongRowOrColumnNumberException e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setError("The number of a row or a column is out of bounds!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorDetails);
        } catch (AlreadyPurchasedException e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setError("The ticket has been already purchased!");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorDetails);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Purchase purchase) {
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("ticket", purchaseService.removePurchase(purchase).getTicket());
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(result);
        } catch (WrongTocketException e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setError("Wrong token!");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorDetails);
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {

            loginProcessor.setPassword(password);

            boolean loggedIn = loginProcessor.login();

            if(loggedIn) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(purchaseService.stats());
            } else {
                ErrorDetails errorDetails = new ErrorDetails();
                errorDetails.setError("The password is wrong!");
                return ResponseEntity
                         .status(HttpStatus.UNAUTHORIZED)
                        .body(errorDetails);
            }
    }
}
