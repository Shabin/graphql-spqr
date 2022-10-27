package io.leangen.graphql.execution;

import java.util.Map;

import graphql.GraphQLContext;
import graphql.execution.MergedField;
import graphql.execution.TypeResolutionParameters;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import io.leangen.graphql.generator.TypeRegistry;
import io.leangen.graphql.metadata.strategy.type.TypeInfoGenerator;

public class TypeResolutionEnvironment {

    private final TypeRegistry typeRegistry;
    private final TypeInfoGenerator typeInfoGenerator;
    
    private final graphql.TypeResolutionEnvironment environment;

    public TypeResolutionEnvironment(graphql.TypeResolutionEnvironment environment,
                                     TypeRegistry typeRegistry,
                                     TypeInfoGenerator typeInfoGenerator) {
    	this.environment = TypeResolutionParameters.newParameters().value(environment.getObject())
				.field(environment.getField()).fieldType(environment.getFieldType()).schema(environment.getSchema())
				.context(environment.getContext()).argumentValues(() -> environment.getArguments()).build();
        this.typeRegistry = typeRegistry;
        this.typeInfoGenerator = typeInfoGenerator;
    }

    public TypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    public TypeInfoGenerator getTypeInfoGenerator() {
        return typeInfoGenerator;
    }
    
	/**
	 * You will be passed the specific source object that needs to be resolved into a concrete graphql object type
	 *
	 * @param <T> you decide what type it is
	 *
	 * @return the object that needs to be resolved into a specific graphql object type
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T) environment.getObject();
	}

	/**
	 * @return the runtime arguments to this the graphql field
	 */
	public Map<String, Object> getArguments() {
		return environment.getArguments();
	}

	/**
	 * @return the graphql field in question
	 */
	public MergedField getField() {
		return environment.getField();
	}

	/**
	 * @return the type of the graphql field, which still be either a {@link graphql.schema.GraphQLUnionType} or a
	 *         {@link graphql.schema.GraphQLInterfaceType}
	 */
	public GraphQLType getFieldType() {
		return environment.getFieldType();
	}

	/**
	 * @return the graphql schema in question
	 */
	public GraphQLSchema getSchema() {
		return environment.getSchema();
	}

	/**
	 * Returns the context object set in via {@link ExecutionInput#getContext()}
	 *
	 * @param <T> to two
	 *
	 * @return the context object
	 *
	 * @deprecated use {@link #getGraphQLContext()} instead
	 */
	@Deprecated
	public <T> T getContext() {
		// noinspection unchecked
		return (T) environment.getContext();
	}

	/**
	 * @return the {@link GraphQLContext} object set in via {@link ExecutionInput#getGraphQLContext()}
	 */
	public GraphQLContext getGraphQLContext() {
		return environment.getGraphQLContext();
	}

	/**
	 * Returns the local context object set in via {@link DataFetcherResult#getLocalContext()}
	 *
	 * @param <T> to two
	 *
	 * @return the local context object
	 */
	<T> T getLocalContext() {
		// noinspection unchecked
		return (T) null;
	}

	/**
	 * @return the {@link DataFetchingFieldSelectionSet} for the current field fetch that needs type resolution
	 */
	public DataFetchingFieldSelectionSet getSelectionSet() {
		return environment.getSelectionSet();
	}
}
