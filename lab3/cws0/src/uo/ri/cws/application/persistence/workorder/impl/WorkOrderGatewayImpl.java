package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoicingWorkOrderRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.util.jdbc.Jdbc;
import uo.ri.util.jdbc.Queries;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

    @Override
    public void add(WorkorderRecord t) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void remove(String id) throws PersistenceException {
	// TODO Auto-generated method stub

    }

    @Override
    public void update(WorkorderRecord t) throws PersistenceException {
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_UPDATE"))) {
		pst.setString(1, t.invoice_id);
		pst.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		pst.setString(3, t.id);
		pst.executeUpdate();
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}

    }

    @Override
    public Optional<WorkorderRecord> findById(String id)
	throws PersistenceException {
	Optional<WorkorderRecord> ow = Optional.empty();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_ID_BY_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    if (rs.next()) {
			WorkorderRecord wr = new WorkorderRecord();
			wr.id = rs.getString("id");
			wr.amount = rs.getDouble("amount");
			wr.state = rs.getString("state");

			ow = Optional.of(wr);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new PersistenceException(e);
	}
	return ow;
    }

    @Override
    public List<WorkorderRecord> findAll() throws PersistenceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<String> findByMechanicId(String id) {
	List<String> ids = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDER_BY_MECHANIC_ID"))) {
		pst.setString(1, id);
		try (ResultSet rs = pst.executeQuery()) {
		    while (rs.next()) {
			ids.add(rs.getString("id"));
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
	return ids;
    }

    @Override
    public List<InvoicingWorkOrderRecord> findNotInvoicedWorkOrdeByClient(
	String nif) {
	List<InvoicingWorkOrderRecord> listIn = new ArrayList<>();
	try {
	    Connection c = Jdbc.getCurrentConnection();
	    try (PreparedStatement pst = c.prepareStatement(
		Queries.getSQLSentence("TWORKORDERS_FIND_NOT_INVOICED"))) {
		pst.setString(1, nif);
		try (ResultSet rs = pst.executeQuery();) {
		    while (rs.next()) {
			InvoicingWorkOrderRecord dto = new InvoicingWorkOrderRecord();
			dto.id = rs.getString("id");
			dto.description = rs.getString("description");
			dto.date = rs.getTimestamp("date").toLocalDateTime();
			dto.state = rs.getString("state");
			dto.amount = rs.getDouble("amount");
			listIn.add(dto);
		    }
		}
	    }
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
	return listIn;
    }

}
