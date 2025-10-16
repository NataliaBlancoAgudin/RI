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
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(ContractRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<ContractRecord> findById(String id)
	throws PersistenceException {
	// TODO Auto-generated method stub
	return Optional.empty();
    }

    @Override
    public List<ContractRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<String> findByMechanicId(String id) {
	List<String> ids = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TCONTRACT_BY_MECHANIC_ID"))) {
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
    public List<String> findByProfessionalGroupName(String name) {
	List<String> plist = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(Queries
		.getSQLSentence("TCONTRACT_BY_PROFESSIONALGROUP_NAME"))) {
		pst.setString(1, name);
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
		Queries.getSQLSentence("TCONTRACT_IN_MONTH"))) {
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

}
