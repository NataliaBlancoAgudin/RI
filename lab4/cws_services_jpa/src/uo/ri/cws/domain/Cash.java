package uo.ri.cws.domain;

public class Cash extends PaymentMean {

    public Cash(Client client) {
	/// Tengo que asociarlo con client
	Associations.Holds.link(this, client);
    }

    /**
     * A cash can always pay
     */
    @Override
    public boolean canPay(Double amount) {
	return true;
    }

}
