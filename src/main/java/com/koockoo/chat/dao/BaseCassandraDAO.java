package com.koockoo.chat.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.EntityTypeParser;
import com.datastax.driver.mapping.meta.EntityFieldMetaData;
import com.datastax.driver.mapping.meta.EntityTypeMetadata;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 * Wrapper over MappingSession to reduce amount of code for other DAO
 * @author Eugene Valchkou.
 *
 */
@Repository
public class BaseCassandraDAO {
	
	@Autowired 
	protected CassandraSessionFactory sf;
	
	public <T> T get(Class<T> clazz, Object key) {
		return sf.getMappingSession().get(clazz, key);
	}
	
	public <T> T save(T obj) {
		return sf.getMappingSession().save(obj);
	}

	public <T> void delete(T obj) {
		sf.getMappingSession().delete(obj);
	}

	public <T> void delete(Class<T> clazz, Object id) {
		sf.getMappingSession().delete(clazz, id);
	}
	
    /** Building Select Statement to select by a single column equals criteria*/
    protected <T> Statement buildQueryForColumn(Class<T> clazz, String propName, Object propVal) {
        EntityTypeMetadata emeta = EntityTypeParser.getEntityMetadata(clazz);
        EntityFieldMetaData fmeta = emeta.getFieldMetadata(propName);
        if (fmeta != null) {
            return QueryBuilder.select().all()
                    .from(sf.getKeyspace(), emeta.getTableName())
                    .where(eq(fmeta.getColumnName(), propVal));
        }
        return null;
    }	
	
	public CassandraSessionFactory getSessionFactory() {
		return sf;
	}

	public void setSessionFactory(CassandraSessionFactory sf) {
		this.sf = sf;
	}

	
}
