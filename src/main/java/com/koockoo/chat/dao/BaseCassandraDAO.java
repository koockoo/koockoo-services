package com.koockoo.chat.dao;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.MappingSession;
import com.datastax.driver.mapping.meta.EntityFieldMetaData;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;
import com.google.common.collect.ImmutableMap;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 * Spring Bean wrapper for MappingSession.
 * This class inherits all the MappingSession behavior 
 * In addition it may add extra features for you specific needs.
 * As an example <pre>buildQueryForColumn</pre> method.
 */
@Repository
public abstract class BaseCassandraDAO extends MappingSession implements InitializingBean {

    @Autowired 
    protected CassandraSessionFactory sf;
	
    /** Building Select Statement to select by a single column equals criteria*/
    protected <T> Statement buildQueryForColumn(Class<T> clazz, String propName, Object propVal) {
        return buildQueryForColumns(clazz, ImmutableMap.of(propName, propVal));
    }	
	
    /** Building Select Statement to select by a single column equals criteria*/
    protected <T> Statement buildQueryForColumns(Class<T> clazz, Map<String, Object> props) {
        EntityTypeMetadata emeta = EntityTypeParser.getEntityMetadata(clazz);
        Select s = QueryBuilder.select().all().from(sf.getKeyspace(), emeta.getTableName());
        for (String propName: props.keySet()) {
            EntityFieldMetaData fmeta = emeta.getFieldMetadata(propName);
            if (fmeta != null) {
                s.where(eq(fmeta.getColumnName(), props.get(propName)));
            }
        }
        s.allowFiltering();
        return s;
    }   
    
	public CassandraSessionFactory getSessionFactory() {
		return sf;
	}

	public void setSessionFactory(CassandraSessionFactory sf) {
		this.sf = sf;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        keyspace = sf.getKeyspace();
        session = sf.getSession();
    }
	
}
