package cinema.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatAll {
    private int rows = 9;
    private int columns = 9;
    private final List<Seat> seats;

    public SeatAll() {
        seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Seat seat = new Seat();
                seat.setRow(i + 1);
                seat.setColumn(j + 1);
                if(seat.getRow() <= 4) {
                    seat.setPrice(10);
                } else {
                    seat.setPrice(8);
                }
                seats.add(seat);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> findAllSeats() {
        return seats;
    }

    public List<Seat> getSeats() {
        List<Seat> notOccupiedSeats = new ArrayList<>();
        for(Seat seat : seats) {
            if(!seat.isOccupied()) {
                notOccupiedSeats.add(seat);
            }
        }
        return notOccupiedSeats;
    }


}
