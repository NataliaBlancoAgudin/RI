package uo.ri.cws.application.persistence.professionalGroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalGroup.ProfessionalGroupGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

    @Override
    public void add(ProfessionalGroupRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_ADD"))) {
		pst.setString(1, t.id);
		pst.setString(2, t.name);
		pst.setDouble(3, t.productivityRate);
		pst.setDouble(4, t.trienniumPayment);
		pst.setLong(5, t.version);
		/// estos son valores que no nos da el dto
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

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
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_DELETE"))) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void update(ProfessionalGroupRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_UPDATE"))) {
		pst.setDouble(1, t.productivityRate);
		pst.setDouble(2, t.trienniumPayment);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

		pst.setString(4, t.name);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<ProfessionalGroupRecord> findById(String id)
	throws PersistenceException {
	Optional<ProfessionalGroupRecord> op = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDBYID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			ProfessionalGroupRecord p = new ProfessionalGroupRecord();
			p.id = rs.getString("id");
			p.createdAt = rs.getTimestamp("createdAt")
			    .toLocalDateTime();
			p.entityState = rs.getString("entityState");
			p.name = rs.getString("name");
			p.productivityRate = rs.getDouble("productivityRate");
			p.trienniumPayment = rs.getDouble("trienniumPayment");
			p.version = rs.getLong("version");

			op = Optional.of(p);
		    }

		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return op;
    }

    @Override
    public List<ProfessionalGroupRecord> findAll() throws PersistenceException {
	List<ProfessionalGroupRecord> lp = new ArrayList<>();

	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_FINDALL"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ProfessionalGroupRecord p = new ProfessionalGroupRecord();
			p.id = rs.getString("id");
			p.name = rs.getString("name");
			p.productivityRate = rs.getDouble("productivityRate");
			p.trienniumPayment = rs.getDouble("trienniumPayment");
			p.version = rs.getLong("version");

			lp.add(p);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return lp;
    }

    @Override
    public Optional<ProfessionalGroupRecord> findByName(String name) {
	Optional<ProfessionalGroupRecord> op = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();

	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TPROFESSIONALGROUPS_FIND_BY_NAME"))) {
		pst.setString(1, name);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			ProfessionalGroupRecord p = new ProfessionalGroupRecord();
			p.id = rs.getString("id");
			p.name = rs.getString("name");
			p.productivityRate = rs.getDouble("productivityRate");
			p.trienniumPayment = rs.getDouble("trienniumPayment");

			p.version = rs.getLong("version");
			op = Optional.of(p);
		    }
		}
	    }

	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return op;
    }

}
