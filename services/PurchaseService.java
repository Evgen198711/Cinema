package cinema.services;

import cinema.exceptions.AlreadyPurchasedException;
import cinema.exceptions.WrongRowOrColumnNumberException;
import cinema.exceptions.WrongTocketException;
import cinema.model.*;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    private final SeatAll seatAll;
    private final PurchasesAll purchases;
    private final Statistics statistics;

    public PurchaseService(SeatAll seatAll, PurchasesAll purchases, Statistics statistics) {
        this.seatAll = seatAll;
        this.purchases = purchases;
        this.statistics = statistics;
    }
    public Seat purchaseSeat(Seat seat) {
        if(seat.getRow() >= 9 || seat.getRow() < 1 || seat.getColumn() >= 9 || seat.getColumn() < 1) {
            throw new WrongRowOrColumnNumberException();
        } else if (seatAll.findAllSeats().get((seat.getRow() - 1) * 9 + (seat.getColumn() - 1)).isOccupied()) {
          throw new AlreadyPurchasedException();
        } else {
            Seat someSeat =  seatAll.findAllSeats().get((seat.getRow() - 1) * 9 + (seat.getColumn() - 1));
            seatAll.findAllSeats().get((seat.getRow() - 1) * 9 + (seat.getColumn() - 1)).setOccupied(true);
            return  someSeat;
        }
    }

    public void addPurchase (Purchase purchase) {
        purchases.getPurchases().put(purchase.getToken(), purchase);
    }

    public Purchase removePurchase(Purchase purchase) {
        if(!purchases.getPurchases().containsKey(purchase.getToken())) {
            throw new WrongTocketException();
        } else {
            Purchase somePurchase = purchases.getPurchases().remove(purchase.getToken());
            seatAll.findAllSeats().get(seatAll.findAllSeats().indexOf(somePurchase.getTicket())).setOccupied(false);
            return somePurchase;
        }
    }

    public Statistics stats() {
        int incomeSum = 0;
        int available = seatAll.getRows() * seatAll.getColumns();
        int purchased = 0;

        for (Seat seat : seatAll.findAllSeats()) {
            if(seat.isOccupied()) {
                incomeSum += seat.getPrice();
                available--;
                purchased++;
            }
        }
        statistics.setPurchased(purchased);
        statistics.setAvailable(available);
        statistics.setIncome(incomeSum);

        return statistics;
    }

    public SeatAll getSeatAll() {
        return seatAll;
    }
}
