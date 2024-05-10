package cinema.model;

public class Purchase {
    private String token;
    private Seat ticket;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (getToken() != null ? !getToken().equals(purchase.getToken()) : purchase.getToken() != null) return false;
        return getTicket() != null ? getTicket().equals(purchase.getTicket()) : purchase.getTicket() == null;
    }

    @Override
    public int hashCode() {
        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getTicket() != null ? getTicket().hashCode() : 0);
        return result;
    }
}
