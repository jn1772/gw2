package com.codebytes.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

class Result{
    
    ResultSet result;
    int error=0;
}

class err{
    static final int TABLE_NOT_EXIST = 1146;
}

@Component
public class DB {
    
    Connection con;
    Statement st;
    ResultSet result;
    err err;
    
    public void sayHello() {
    	System.out.println("Hello!");
    }
    public DB() {
    	
    }
    long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    
    SessionFactory factory;
    
    public void initFactoryInstance() {
    	try {
    		factory = new Configuration().configure("/com/codebytes/core/hibernate.cfg.xml").buildSessionFactory();
    	} catch (Throwable ex) {
    		System.err.println("Failed to create sessionFactory object." + ex);
    		throw new ExceptionInInitializerError(ex);	
    	}
    }
    
    public Session getSession() {
    	if(factory == null)initFactoryInstance();
    	return factory.openSession();	
    }
        
    public Result executeCommand(String s) {
        Result res = new Result();
        try{
            st = con.createStatement();
            st.execute(s);
            result = st.getResultSet();
            res.result = result;
        }catch (SQLException e){
            System.err.println("SQLException : "+e.getMessage() +" "+e.getErrorCode());
            res.error = e.getErrorCode();
        }
        return res;
    }
}