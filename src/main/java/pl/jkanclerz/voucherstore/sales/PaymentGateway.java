package pl.jkanclerz.voucherstore.sales;

public interface PaymentGateway {
    ReservationPaymentDetails register(Reservation reservation);
}