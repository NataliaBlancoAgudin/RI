package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class PayrollGatewayImpl implements PayrollGateway {

    @Override
    public void add(PayrollRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
	throws PersistenceException {
	// TODO Auto-generated method stub
	return Optional.empty();
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<PayrollRecord> findPayrollsByDate(LocalDate date) {
	List<PayrollRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_BY_DATE"))) {

		pst.setDate(1, Date.valueOf(date));

		try (ResultSet rs = pst.executeQuery()) {
		    PayrollRecord pr = new PayrollRecord();
		    pr.id = rs.getString("id");
		    pr.baseSalary = rs.getDouble("baseSalary");
		    pr.createdAt = rs.getDate("createdAt").toLocalDate();
		    pr.date = rs.getDate("date").toLocalDate();
		    pr.entityState = rs.getString("entityState");
		    pr.extraSalary = rs.getDouble("extraSalary");
		    pr.nicDeduction = rs.getDouble("nicDeduction");
		    pr.productivityEarning = rs
			.getDouble("productivityEarning");
		    pr.taxDeduction = rs.getDouble("taxDeduction");
		    pr.trienniumEarning = rs.getDouble("trienniumEarning");
		    pr.updatedAt = rs.getDate("updatedAt").toLocalDate();
		    pr.version = rs.getLong("version");
		    pr.contract_id = rs.getString("contract_id");

		    lp.add(pr);
		}
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

}
