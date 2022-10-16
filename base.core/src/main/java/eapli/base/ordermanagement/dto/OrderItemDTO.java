package eapli.base.ordermanagement.dto;

public class OrderItemDTO {

    /**
     * String that contains the productID of the OrderItemDTO.
     */
    private String productID;

    /**
     * Integer that contains the quantity of the OrderItemDTO.
     */
    private int quantity;

    /**
     * Double that contains the priceWithouTaxes of the OrderItemDTO.
     */
    private double priceWithoutTaxes;

    /**
     * Double that contains the priceWithTaxes of the OrderItemDTO.
     */
    private double priceWithTaxes;

    /**
     * Creates a constructor OrderItemDTO receiving by parameter the productID,
     * the quantity, the priceWithoutTaxes and the priceWithTaxes.
     * @param productID
     * @param quantity
     * @param priceWithoutTaxes
     * @param priceWithTaxes
     */
    public OrderItemDTO(String productID, int quantity, double priceWithoutTaxes, double priceWithTaxes) {
        this.productID = productID;
        this.quantity = quantity;
        this.priceWithoutTaxes = priceWithoutTaxes;
        this.priceWithTaxes = priceWithTaxes;
    }

    /**
     * Method toString that returns all characteristics of OrderItemDTO.
     * @return all characteristics of OrderItemDTO.
     */
    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", priceWithoutTaxes=" + priceWithoutTaxes +
                ", priceWithTaxes=" + priceWithTaxes +
                '}';
    }

    /**
     * Returns the productID of the OrderItemDTO.
     * @return the productID of the OrderItemDTO.
     */
    public String productID() {
        return productID;
    }

    /**
     * Returns the quantity of the OrderItemDTO.
     * @return the quantity of the OrderItemDTO.
     */
    public int quantity() {
        return quantity;
    }

    /**
     * Returns the priceWithoutTaxes of the OrderItemDTO.
     * @return the priceWithoutTaxes of the OrderItemDTO.
     */
    public double priceWithoutTaxes() {
        return priceWithoutTaxes;
    }

    /**
     * Returns the priceWithTaxes of the OrderItemDTO.
     * @return the priceWithTaxes of the OrderItemDTO.
     */
    public double priceWithTaxes() {
        return priceWithTaxes;
    }
}
