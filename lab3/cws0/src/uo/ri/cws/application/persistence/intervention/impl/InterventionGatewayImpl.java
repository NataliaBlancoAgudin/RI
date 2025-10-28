package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.intervention.InterventionGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InterventionGatewayImpl implements InterventionGateway {

    @Override
    public void add(InterventionRecord t) throws PersistenceException {
	// no implementado

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// no implementado

    }

    @Override
    public void update(InterventionRecord t) throws PersistenceException {
	// no implementado

    }

    @Override
    public Optional<InterventionRecord> findById(String id)
	throws PersistenceException {
	// no implementado
	return Optional.empty();
    }

    @Override
    public List<InterventionRecord> findAll() throws PersistenceException {
	// no implementado
	return null;
    }

    @Override
    public List<String> findByMechanicId(String id) {
	List<String> ids = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TINTERFVENTION_BY_MECHANIC_ID"))) {
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

}
