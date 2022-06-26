package com.kakaopay.server.member;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberIdGenerator implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) as Id from Member");
            if(rs.next()) {
                int id = rs.getInt(1) + 1;
                String generateId = String.format("%09d", id);
                return generateId;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
