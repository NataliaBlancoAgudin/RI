package uo.ri.cws.application.persistence.contract.impl;

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
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ContractGatewayImpl implements ContractGateway {

    @Override
    public void add(ContractRecord t) throws PersistenceException {
	// no implementado

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// no implementado

    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {
	// no implementado

    }

    @Override
    public Optional<ContractRecord> findById(String id)
	throws PersistenceException {
	Optional<ContractRecord> oc = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_FINDBYID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			ContractRecord cr = new ContractRecord();
			cr.id = rs.getString("id");
			cr.annualbasesalary = rs.getDouble("annualBaseSalary");
			cr.createdAt = rs.getTimestamp("createdAt")
			    .toLocalDateTime();
			cr.enddate = rs.getDate("endDate");
			cr.entityState = rs.getString("entityState");
			cr.settlement = rs.getDouble("settlement");
			cr.startdate = rs.getDate("startDate");
			cr.state = rs.getString("state");
			cr.taxrate = rs.getDouble("taxRate");
			cr.updatedAt = rs.getTimestamp("updatedAt")
			    .toLocalDateTime();
			cr.version = rs.getLong("version");
			cr.contracttype_id = rs.getString("contracttype_id");
			cr.mechanic_id = rs.getString("mechanic_id");
			cr.professionalgroup_id = rs
			    .getString("professionalgroup_id");

			oc = Optional.of(cr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return oc;
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
	// no implementado
	return null;
    }

    @Override
    public List<String> findByMechanicId(String id) {
	List<String> ids = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ids.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return ids;
    }

    @Override
    public List<String> findByProfessionalGroupId(String id) {
	List<String> plist = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries
		.getSQLSentence("TCONTRACTS_BY_PROFESSIONALGROUP_NAME"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			plist.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return plist;
    }

    @Override
    public List<String> findContractsInMonth(LocalDate date) {
	List<String> plist = new ArrayList<>();

	// Calculamos el primer y ultimo dia del mes
	LocalDate firstDay = date.withDayOfMonth(1);
	LocalDate lastDay = date.withDayOfMonth(date.lengthOfMonth());

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_IN_MONTH"))) {
		pst.setDate(1, Date.valueOf(lastDay));
		pst.setDate(2, Date.valueOf(firstDay));
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			plist.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return plist;
    }

    @Override
    public List<ContractRecord> findInForceContracts() {
	List<ContractRecord> plist = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACTS_IN_FORCE"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ContractRecord cr = new ContractRecord();
			cr.id = rs.getString("id");
			cr.annualbasesalary = rs.getDouble("annualBaseSalary");
			cr.createdAt = rs.getTimestamp("createdAt")
			    .toLocalDateTime();
			cr.entityState = rs.getString("entityState");
			cr.settlement = rs.getDouble("settlement");
			cr.startdate = rs.getDate("startDate");
			cr.state = rs.getString("state");
			cr.taxrate = rs.getDouble("taxRate");
			cr.updatedAt = rs.getTimestamp("updatedAt")
			    .toLocalDateTime();
			cr.version = rs.getLong("version");
			cr.contracttype_id = rs.getString("contracttype_id");
			cr.mechanic_id = rs.getString("mechanic_id");
			cr.professionalgroup_id = rs
			    .getString("professionalgroup_id");

			plist.add(cr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

	return plist;
    }

}
