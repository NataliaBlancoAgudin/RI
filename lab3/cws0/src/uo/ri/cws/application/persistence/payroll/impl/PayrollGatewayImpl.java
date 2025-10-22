package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c
		.prepareStatement(Queries.getSQLSentence("TPAYROLLS_ADD"))) {
		pst.setString(1, t.id);
		pst.setDouble(2, t.baseSalary);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pst.setDate(4, Date.valueOf(t.date));
		pst.setString(5, "ENABLED");
		pst.setDouble(6, t.extraSalary);
		pst.setDouble(7, t.nicDeduction);
		pst.setDouble(8, t.productivityEarning);
		pst.setDouble(9, t.taxDeduction);
		pst.setDouble(10, t.trienniumEarning);
		pst.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
		pst.setLong(12, t.version);
		pst.setString(13, t.contract_id);

		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void remove(String id) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c
		.prepareStatement(Queries.getSQLSentence("TPAYROLLS_DELETE"))) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void update(PayrollRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<PayrollRecord> findById(String id)
	throws PersistenceException {
	Optional<PayrollRecord> op = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDBYID"))) {
		pst.setString(1, id);

		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
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

			op = Optional.of(pr);

		    }

		}
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return op;
    }

    @Override
    public List<PayrollRecord> findAll() throws PersistenceException {
	List<PayrollRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FINDALL"))) {

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
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
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

    @Override
    public List<PayrollRecord> findPayrollsByDate(LocalDate date) {
	List<PayrollRecord> lp = new ArrayList<>();

	LocalDate firstDay = date.withDayOfMonth(1);
	LocalDate lastDay = date.withDayOfMonth(date.lengthOfMonth());

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_BY_DATE"))) {

		pst.setDate(1, Date.valueOf(firstDay));
		pst.setDate(2, Date.valueOf(lastDay));

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
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
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

    @Override
    public List<PayrollRecord> findPayrollsOfMechanicByDate(String mechanicId,
	LocalDate date) {
	List<PayrollRecord> lp = new ArrayList<>();

	LocalDate firstDay = date.withDayOfMonth(1);
	LocalDate lastDay = date.withDayOfMonth(date.lengthOfMonth());

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_OF_MECHANIC_BY_DATE"))) {

		pst.setString(1, mechanicId);
		pst.setDate(2, Date.valueOf(firstDay));
		pst.setDate(3, Date.valueOf(lastDay));

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
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
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

    @Override
    public List<PayrollRecord> findPayrollsByMechanicId(String mechanicId) {
	List<PayrollRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPAYROLLS_FIND_BY_MECHANIC"))) {

		pst.setString(1, mechanicId);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
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
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

    @Override
    public List<PayrollRecord> findPayrollsByProfessionalGroupName(
	String groupName) {
	List<PayrollRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries
		.getSQLSentence("TPAYROLLS_FIND_BY_PROFESSIONAL_GROUP"))) {

		pst.setString(1, groupName);

		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
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
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return lp;
    }

}
