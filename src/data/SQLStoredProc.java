package data;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLStoredProc
{
    private String storedProc;
    private final List<SQLParams> sqlParams;

    public SQLStoredProc(String storedProc)
    {
        this.storedProc = storedProc;
        sqlParams = new ArrayList<>();
    }

    CallableStatement getCallableStmnt(SQLConnection) throws Exception
    {
        CallableStatement statement = SQLConnection.getConn().prepareCall(getQuery());
        setStatementParameters(statement);
        return statement;
    }

    private void setStatementParameters(CallableStatement statement) throws SQLException
    {
        for (SQLParams param : sqlParams)

            statement.setObject(param.getName(), param.getValue(), param.getType());
    }

    private String getQuery()
    {
        if (sqlParams.size() > 0)
            return getParameterizedQuery();
        else
            return "{ CALL " + storedProc + " }";
    }

    private String getParameterizedQuery()
    {
        StringBuilder query = new StringBuilder("{ CALL " + storedProc + "(");
        for (int i = 0; i < sqlParams.size(); i++)
            // query.append((
            if (i == sqlParams.size() - 1)
            {
                query.append("?)}");
            } else query.append("?, ");
        return query.toString();
    }
    //public void addParam(SQLParameter)
}
