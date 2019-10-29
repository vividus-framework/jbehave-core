package org.jbehave.core.model;

import static java.lang.Boolean.parseBoolean;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.steps.ParameterConverters;

public final class ExamplesTableProperties {

    private static final String HEADER_SEPARATOR = "|";
    private static final String VALUE_SEPARATOR = "|";
    private static final String IGNORABLE_SEPARATOR = "|--";
    private static final String COMMENT_SEPARATOR = "#";

    private static final String HEADER_SEPARATOR_KEY = "headerSeparator";
    private static final String VALUE_SEPARATOR_KEY = "valueSeparator";
    private static final String IGNORABLE_SEPARATOR_KEY = "ignorableSeparator";
    private static final String COMMENT_SEPARATOR_KEY = "commentSeparator";

    private static final String ROW_SEPARATOR = "\n";

    private final Properties properties = new Properties();
    private final String propertiesAsString;

    public ExamplesTableProperties(Properties properties){
        this.properties.putAll(properties);
        if ( !this.properties.containsKey(HEADER_SEPARATOR_KEY) ){
            this.properties.setProperty(HEADER_SEPARATOR_KEY, HEADER_SEPARATOR);
        }
        if ( !this.properties.containsKey(VALUE_SEPARATOR_KEY) ){
            this.properties.setProperty(VALUE_SEPARATOR_KEY, VALUE_SEPARATOR);
        }
        if ( !this.properties.containsKey(IGNORABLE_SEPARATOR_KEY) ){
            this.properties.setProperty(IGNORABLE_SEPARATOR_KEY, IGNORABLE_SEPARATOR);
        }
        if ( !this.properties.containsKey(COMMENT_SEPARATOR_KEY) ){
            this.properties.setProperty(COMMENT_SEPARATOR_KEY, COMMENT_SEPARATOR);
        }
        StringBuilder propertiesAsStringBuilder = new StringBuilder();
        for (Map.Entry<Object, Object> property : this.properties.entrySet()) {
            propertiesAsStringBuilder.append(property.getKey()).append('=').append(property.getValue()).append(',');
        }
        propertiesAsString = propertiesAsStringBuilder.substring(0, propertiesAsStringBuilder.length() - 1);
    }

    public ExamplesTableProperties(String propertiesAsString, String defaultHeaderSeparator, String defaultValueSeparator,
            String defaultIgnorableSeparator) {
        this(propertiesAsString, defaultHeaderSeparator, defaultValueSeparator, defaultIgnorableSeparator, null);
    }

    public ExamplesTableProperties(String propertiesAsString, String defaultHeaderSeparator, String defaultValueSeparator,
            String defaultIgnorableSeparator, ParameterConverters parameterConverters) {
        properties.setProperty(HEADER_SEPARATOR_KEY, defaultHeaderSeparator);
        properties.setProperty(VALUE_SEPARATOR_KEY, defaultValueSeparator);
        properties.setProperty(IGNORABLE_SEPARATOR_KEY, defaultIgnorableSeparator);
        properties.putAll(parseProperties(propertiesAsString, parameterConverters));
        this.propertiesAsString = propertiesAsString;
    }

    private Map<String, String> parseProperties(String propertiesAsString, ParameterConverters parameterConverters) {
        Map<String, String> result = new LinkedHashMap<>();
        if (!isEmpty(propertiesAsString)) {
            for (String propertyAsString : propertiesAsString.split("(?<!\\\\),")) {
                String[] property = StringUtils.split(propertyAsString, "=", 2);
                String convertedValue = StringUtils.replace(property[1], "\\,", ",").trim();
                if (parameterConverters != null) {
                	convertedValue = (String) parameterConverters.convert(convertedValue, String.class);
                }
                result.put(property[0].trim(), convertedValue);
             }
        }
        return result;
    }

    public String getRowSeparator() {
        return ROW_SEPARATOR;
    }

    public String getHeaderSeparator() {
        return properties.getProperty(HEADER_SEPARATOR_KEY);
    }

    public String getValueSeparator() {
        return properties.getProperty(VALUE_SEPARATOR_KEY);
    }

    public String getIgnorableSeparator() {
        return properties.getProperty(IGNORABLE_SEPARATOR_KEY);
    }

    public String getCommentSeparator() {
        return properties.getProperty(COMMENT_SEPARATOR_KEY);
    }

    public boolean isTrim() {
        return parseBoolean(properties.getProperty("trim", "true"));
    }

    public boolean isMetaByRow(){
        return parseBoolean(properties.getProperty("metaByRow", "false"));
    }

    public String getTransformer() {
        return properties.getProperty("transformer");
    }

    public Properties getProperties() {
        return properties;
    }

    public String getPropertiesAsString() {
        return propertiesAsString;
    }
}
