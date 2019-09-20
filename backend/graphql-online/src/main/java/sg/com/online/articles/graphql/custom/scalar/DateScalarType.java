package sg.com.online.articles.graphql.custom.scalar;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

/**
 * @author benghwee pc
 * Custom scalar type from Date to String
 */
@Component
public class DateScalarType extends GraphQLScalarType{

	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
	
	DateScalarType() {
		    super("Date", "Date value", new Coercing<Date,String>() {

				@Override
				public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
					Date date;
					if (dataFetcherResult instanceof Date) {
	                    date = (Date) dataFetcherResult;
	                } else if (dataFetcherResult instanceof String) {
	                    date = parseDate(dataFetcherResult.toString(), CoercingSerializeException::new);
	                } else {
	                    throw new CoercingSerializeException(
	                            "Expected a 'String' or 'java.util.Date' but was '" + (dataFetcherResult).getClass() + "'."
	                    );
	                }
	                try {
	                    return dateFormatter.format(date);
	                } catch (DateTimeException e) {
	                    throw new CoercingSerializeException(
	                            "Unable to turn Date into full date because of : '" + e.getMessage() + "'."
	                    );
	                }
				}

				@Override
				public Date parseValue(Object input) throws CoercingParseValueException {
					Date date;
	                if (input instanceof Date) {
	                    date = (Date) input;
	                } else if (input instanceof String) {
	                    date = parseDate(input.toString(), CoercingParseValueException::new);
	                } else {
	                    throw new CoercingParseValueException(
	                            "Expected a 'String' or 'java.util.Date' but was '" + (input).getClass() + "'."
	                    );
	                }
	                try {
	                    return date;
	                } catch (DateTimeException ex) {
	                    throw new CoercingParseValueException(
	                            "Unable to turn Date into full date because of : '" + ex.getMessage() + "'."
	                    );
	                }
				}

				@Override
				public Date parseLiteral(Object input) throws CoercingParseLiteralException {
					if (!(input instanceof StringValue)) {
	                    throw new CoercingParseLiteralException(
	                            "Expected AST type 'StringValue' but was '" + (input).getClass() + "'."
	                    );
	                }
	                return parseDate(((StringValue) input).getValue(), CoercingParseLiteralException::new);
				}
		      
		    });
		  }
	
	private static Date parseDate(String str, Function<String, RuntimeException> exceptionMaker) {
        try {
        	//modified this part to allows empty dates
        	if(str.isEmpty()) {
        		return new Date();
        	}
        	
            Date temporalAccessor = dateFormatter.parse(str);
            return temporalAccessor;
        } catch ( ParseException ex) {
            throw exceptionMaker.apply("Invalid RFC3339 full date value : '" + str + "'. because of : '" + ex.getMessage() + "'");
        }
    }
}
