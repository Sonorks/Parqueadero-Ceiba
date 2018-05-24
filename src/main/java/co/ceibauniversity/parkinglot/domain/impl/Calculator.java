package co.ceibauniversity.parkinglot.domain.impl;

import org.springframework.stereotype.Service;

import co.ceibauniversity.parkinglot.domain.ICalculator;
import co.ceibauniversity.parkinglot.model.Vehicle;

@Service
public class Calculator implements ICalculator {

	@Override
	public double getTotalPrice(String type, int cc, int totalHours, double priceDay, double priceHour) {
		double totalPrice;
		System.out.println("En getTotalPrice");
		if(totalHours >= Watchman.MIN_HOURS_TO_PAY_BY_DAY && totalHours <= Watchman.MAX_HOURS_TO_PAY_BY_DAY) {
			totalPrice = priceDay;
		} else if (totalHours > Watchman.MAX_HOURS_TO_PAY_BY_DAY){
			int extraHours = totalHours % Watchman.MAX_HOURS_TO_PAY_BY_DAY;
			if(extraHours > Watchman.MIN_HOURS_TO_PAY_BY_DAY) {
				totalPrice = (priceDay)*((totalHours/(Watchman.MAX_HOURS_TO_PAY_BY_DAY))+1);
			}else {
				totalPrice = (priceHour*extraHours + priceDay*(totalHours/Watchman.MAX_HOURS_TO_PAY_BY_DAY));
			}
		} else {
			totalPrice = totalHours * priceHour;
		}
		if(type.equals(Vehicle.BIKE)) {
			if(cc>Watchman.MAX_CC) {
				totalPrice += Watchman.EXTRA_CC_COST;
			}
		}
		return totalPrice;
	}
}
