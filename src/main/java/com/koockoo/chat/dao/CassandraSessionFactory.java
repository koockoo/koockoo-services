package com.koockoo.chat.dao;

import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingSession;

/**
 * This class initializes connection to Cassandra Cluster.
 * All DAO implementations reuse this component.
 */
@Repository
public class CassandraSessionFactory {

    protected String node = "127.0.0.1";
    protected String keyspace = "koockoo";
    protected Cluster cluster;
	protected Session session;
	protected MappingSession mappingSession;
	
    /** only 1 thread is permitted to open connection */
    protected synchronized void connect() {
        if (session == null) {
            cluster = Cluster.builder().addContactPoint(node).build();
            session = cluster.connect();
            session.execute("CREATE KEYSPACE IF NOT EXISTS "+ getKeyspace() +
                " WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 }");

            mappingSession = new MappingSession(getKeyspace(), getSession());
        }   
    }
	
    public Session getSession() {
        if (session == null) {
            connect();
        }
        return session;
    }

    public MappingSession getMappingSession() {
        if (session == null) {
            connect();
        }
        return mappingSession;
    }

	public void setMappingSession(MappingSession mappingSession) {
		this.mappingSession = mappingSession;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
}
