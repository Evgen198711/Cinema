package cinema.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PurchasesAll {
    private Map<String, Purchase> purchases;

    public PurchasesAll() {
        this.purchases = new HashMap<>();
    }

    public Map<String, Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Map<String, Purchase> purchases) {
        this.purchases = purchases;
    }
}
