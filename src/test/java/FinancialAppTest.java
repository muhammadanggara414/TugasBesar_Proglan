import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FinancialAppTest {
    private FinancialApp app;

    @Before
    public void setUp() {
        app = new FinancialApp();
    }

    @Test
    public void testFormatCurrency() {
        String formatted = app.formatCurrency("1234567.89");
        assertEquals("1,234,568", formatted);
    }

    @Test
    public void testAddTransaction() {
        Map<String, String> transaction = new HashMap<>();
        transaction.put("date", "2023-10-01");
        transaction.put("desc", "Penjualan");
        transaction.put("category", "Pemasukan");
        transaction.put("amount", "100000");

        app.addTransaction(transaction); // Anda perlu menambahkan metode ini di FinancialApp

        assertEquals(1, app.getTransactions().size()); // Anda perlu menambahkan metode ini di FinancialApp
        assertEquals("Penjualan", app.getTransactions().get(0).get("desc"));
    }

    @Test
    public void testUpdateSummary() {
        app.addTransaction(createTransaction("2023-10-01", "Penjualan", "Pemasukan", "100000"));
        app.addTransaction(createTransaction("2023-10-02", "Belanja", "Pengeluaran", "50000"));

        app.updateSummary(); // Anda perlu menambahkan metode ini di FinancialApp

        assertEquals("Pemasukan: Rp1,000,000", app.getIncomeLabelText()); // Anda perlu menambahkan metode ini di FinancialApp
        assertEquals("Pengeluaran: Rp50,000", app.getExpenseLabelText()); // Anda perlu menambahkan metode ini di FinancialApp
        assertEquals("Saldo: Rp950,000", app.getBalanceLabelText()); // Anda perlu menambahkan metode ini di FinancialApp
    }

    private Map<String, String> createTransaction(String date, String desc, String category, String amount) {
        Map<String, String> transaction = new HashMap<>();
        transaction.put("date", date);
        transaction.put("desc", desc);
        transaction.put("category", category);
        transaction.put("amount", amount);
        return transaction;
    }
}