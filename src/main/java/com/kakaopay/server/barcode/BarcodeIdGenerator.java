package com.kakaopay.server.barcode;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class BarcodeIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

        Connection connection = session.connection();
        try{
            Statement statement = connection.createStatement();
            Random random = new Random();


            ResultSet rs = statement.executeQuery("select count(*) as Id from Member");
            if(rs.next()) {
                int id = rs.getInt(1);
                StringBuilder generateId;


                while(true) {
                    generateId = new StringBuilder();

                    for (int i = 0; i < 10; i++) {
                        generateId.append(random.nextInt(9));
                    }
                    generateId = new StringBuilder(generateId.insert(0, id).substring(0, 10));
                    ResultSet sameBarcodeCnt = statement.executeQuery("select count(*) as Id from Barcode where barcode_id=" + "\'"+ generateId +"\'");
                    if(sameBarcodeCnt.next()){
                        if(sameBarcodeCnt.getInt(1) == 0)
                            break;
                    }
                }

                return generateId.toString();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
