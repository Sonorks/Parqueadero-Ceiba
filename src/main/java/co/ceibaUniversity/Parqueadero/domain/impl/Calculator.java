package co.ceibaUniversity.Parqueadero.domain.impl;

import org.springframework.stereotype.Service;

import co.ceibaUniversity.Parqueadero.domain.ICalculator;

@Service
public class Calculator implements ICalculator {

	@Override
	public double getTotalPrice(int totalHours, double priceDay, double priceHour) {
		double totalPrice;
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
		return totalPrice;
	}
}
