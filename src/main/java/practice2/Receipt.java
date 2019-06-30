package practice2;

import java.math.BigDecimal;
import java.util.List;

public class Receipt {

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateReducedSubtotal(products, items);
        BigDecimal taxTotal = getTaxTotal(subTotal);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


	private BigDecimal calculateReducedSubtotal(List<Product> products, List<OrderItem> items) {
		BigDecimal subTotal = calculateSubtotal(products, items);

        for (Product product : products) {
            OrderItem curItem = findOrderItemByProduct(items, product);
            BigDecimal reducedPrice = getReducedPrice(product, curItem);
            subTotal = subTotal.subtract(reducedPrice);
        }
		return subTotal;
	}

	private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = new BigDecimal(0);
        for (Product product : products) {
            OrderItem item = findOrderItemByProduct(items, product);
            BigDecimal itemTotal = getItemTotal(product, item);
            subTotal = subTotal.add(itemTotal);
        }
        return subTotal;
    }
	
	
	 private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
	        OrderItem curItem = null;
	        for (OrderItem item : items) {
	            if (item.getCode() == product.getCode()) {
	                curItem = item;
	                break;
	            }
	        }
	        return curItem;
	 }
	 
	 
	private BigDecimal getItemTotal(Product product, OrderItem item) {
		BigDecimal itemTotal = product.getPrice().multiply(new BigDecimal(item.getCount()));
		return itemTotal;
	}
	
	private BigDecimal getReducedPrice(Product product, OrderItem curItem) {
		BigDecimal reducedPrice = getItemTotal(product, curItem)
		        .multiply(product.getDiscountRate());
		return reducedPrice;
	}

   
	private BigDecimal getTaxTotal(BigDecimal subTotal) {
		BigDecimal tax;
		tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
        
		BigDecimal taxTotal = subTotal.multiply(tax);
		return taxTotal;
	}
}
