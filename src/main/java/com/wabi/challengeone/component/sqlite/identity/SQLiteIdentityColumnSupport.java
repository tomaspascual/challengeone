package com.wabi.challengeone.component.sqlite.identity;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.springframework.stereotype.Component;

@Component
public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getIdentitySelectString(String table, String column, int type)
            throws MappingException {
        return "select last_insert_rowid()";
    }

    @Override
    public String getIdentityColumnString(int type) throws MappingException {
        return "integer";
    }
}