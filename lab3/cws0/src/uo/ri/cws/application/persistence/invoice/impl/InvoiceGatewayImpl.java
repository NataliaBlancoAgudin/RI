package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class InvoiceGatewayImpl implements InvoiceGateway {

//    private static final String TWORKORDERS_FINDNOTINVOICED = "select a.id, a.description, a.date, a.state, a.amount"
//	+ " from TWorkOrders as a, TVehicles as v, TClients as c"
//	+ " where a.vehicle_id = v.id" + " and v.client_id = c.id"
//	+ " and state like 'FINISHED' and nif like ?";

    @Override
    public void add(InvoiceRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c
		.prepareStatement(Queries.getSQLSentence("TINVOICES_INSERT"))) {
		pst.setString(1, t.id);
		pst.setLong(2, t.number);
		pst.setDate(3, java.sql.Date.valueOf(t.date));
		pst.setDouble(4, t.vat);
		pst.setDouble(5, t.amount);
		pst.setString(6, t.state);
		pst.setLong(7, 1L);
		pst.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
		pst.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
		pst.setString(10, "ENABLED"); /// PREGUNTA: ¿Que entity pongo?
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(InvoiceRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public Optional<InvoiceRecord> findById(String id)
	throws PersistenceException {
	// TODO Auto-generated method stub
	return Optional.empty();
    }

    @Override
    public List<InvoiceRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public long findNextNumber() {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TINVOICES_SELECT_LAST_NUMBER"))) {
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			return rs.getLong(1) + 1;
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return 1L; // Si no hay facturas previas se empieza desde 1
    }

}
