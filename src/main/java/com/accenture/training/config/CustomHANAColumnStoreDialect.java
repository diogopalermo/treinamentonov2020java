package com.accenture.training.config;

import org.hibernate.dialect.AbstractHANADialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class CustomHANAColumnStoreDialect extends AbstractHANADialect {

    public CustomHANAColumnStoreDialect() {
        super();

        // full-text search functions
        registerFunction("score", new StandardSQLFunction("score", StandardBasicTypes.DOUBLE));
        registerFunction("snippets", new StandardSQLFunction("snippets"));
        registerFunction("highlighted", new StandardSQLFunction("highlighted"));
        registerFunction("contains", new VarArgsSQLFunction(StandardBasicTypes.BOOLEAN, "contains(", ",", ") /*"));
        registerFunction("contains_rhs", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "*/"));
        registerFunction("fuzzy", new VarArgsSQLFunction(StandardBasicTypes.STRING, "FUZZY(", ",", ")"));
        registerFunction("multiarguments", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", ",", ")"));
        registerFunction("not_contains", new VarArgsSQLFunction(StandardBasicTypes.BOOLEAN, "not contains(", ",", ") /*"));
        registerFunction("months_between", new StandardSQLFunction("months_between", StandardBasicTypes.INTEGER));
        registerFunction("fuzzySimilarCalculationModeSearchCompare", new SQLFunctionTemplate(StandardBasicTypes.STRING, "'similarCalculationMode=searchCompare'"));
      
    }

    @Override
	protected boolean supportsAsciiStringTypes() {
		return false;
	}

	@Override
	protected Boolean useUnicodeStringTypesDefault() {
		return Boolean.TRUE;
	}
}