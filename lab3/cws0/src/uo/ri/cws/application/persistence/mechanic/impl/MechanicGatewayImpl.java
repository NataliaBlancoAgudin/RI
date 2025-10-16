package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class MechanicGatewayImpl implements MechanicGateway {

//    private static final String TMECHANICS_UPDATE = "update TMechanics set name = ?, surname = ?, "
//	+ "version = version + 1, updatedat = ?"
//	+ "where id = ? and version = ?";
//    private static final String TMECHANICS_FINDBYID = "select * from TMechanics where id = ?";

//    private static final String TMECHANICS_FINDALL = "SELECT ID, NAME, "
//	+ "SURNAME, NIF, VERSION FROM TMECHANICS";

//    private static final String TMECHANICS_FINDBYNIF = "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS "
//	+ "WHERE NIF = ?";

//    private static final String TMECHANICS_ADD = "insert into TMechanics"
//	+ "(id, nif, name, surname, version, "
//	+ "createdAt, updatedAt, entityState) "
//	+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

//    private static final String TMECHANICS_DELETE = "DELETE FROM TMECHANICS "
//	+ "WHERE ID = ?";

    @Override
    public void add(MechanicRecord t) throws PersistenceException {
	// Process
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c
		.prepareStatement(Queries.getSQLSentence("TMECHANICS_ADD"))) {
		pst.setString(1, t.id);
		pst.setString(2, t.nif);
		pst.setString(3, t.name);
		pst.setString(4, t.surname);
		pst.setLong(5, t.version);
		/// Estos no estan en el DTO
		pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		pst.setString(8, "ENABLED");

		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void remove(String id) throws PersistenceException {
	// Process
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TMECHANICS_DELETE"))) {
		pst.setString(1, id);
		pst.executeUpdate();
	    }

	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

    }

    @Override
    public void update(MechanicRecord t) throws PersistenceException {
	// Process
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TMECHANICS_UPDATE"))) {
		pst.setString(1, t.name);
		pst.setString(2, t.surname);
		pst.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
		pst.setString(4, t.id);
		pst.executeUpdate();

	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<MechanicRecord> findById(String id)
	throws PersistenceException {
	Optional<MechanicRecord> om = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TMECHANICS_FINDBYID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			/// Como lo tengo que repetir 3 veces minimo lo puedo
			/// poner en una clase fuera con un unico metodo
			/// estatico
			/// Clase "MechanicRecordAssembler.toRecord(rs)"
			MechanicRecord m = new MechanicRecord();
			m.id = rs.getString("id");
			m.nif = rs.getString("nif");
			m.name = rs.getString("name");
			m.surname = rs.getString("surname");
			m.version = rs.getLong("version");
			m.entityState = rs.getString("entitystate");
			m.createdAt = rs.getTimestamp("createdAt")
			    .toLocalDateTime();
			m.updatedAt = rs.getTimestamp("updatedAt")
			    .toLocalDateTime();

			om = Optional.of(m);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return om;
    }

    @Override
    public List<MechanicRecord> findAll() throws PersistenceException {
	List<MechanicRecord> lm = new ArrayList<>();
	// Process
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TMECHANICS_FINDALL"))) {
		try (ResultSet rs = pst.executeQuery();) {
		    while (rs.next()) {
			MechanicRecord m = new MechanicRecord();
			m.id = rs.getString("id");
			m.nif = rs.getString("nif");
			m.name = rs.getString("name");
			m.surname = rs.getString("surname");
			m.version = rs.getLong("version");
			lm.add(m);

		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	return lm;
    }

    @Override
    public Optional<MechanicRecord> findByNif(String nif) {
	MechanicRecord m = new MechanicRecord();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TMECHANICS_FINDBYNIF"))) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			m.id = rs.getString("ID");
			m.name = rs.getString("NAME");
			m.surname = rs.getString("SURNAME");
			m.nif = rs.getString("nif");
			m.version = rs.getLong("VERSION");

			return Optional.of(m);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
	return Optional.empty();
    }

}
