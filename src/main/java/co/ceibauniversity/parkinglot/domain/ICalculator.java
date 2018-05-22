package co.ceibauniversity.parkinglot.domain;

public interface ICalculator {
	double getTotalPrice(int totalHours, double priceDay, double priceHour);
}
