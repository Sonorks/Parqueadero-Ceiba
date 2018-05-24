package co.ceibauniversity.parkinglot.domain;

public interface ICalculator {
	double getTotalPrice(String type, int cc, int totalHours, double priceDay, double priceHour);
}
