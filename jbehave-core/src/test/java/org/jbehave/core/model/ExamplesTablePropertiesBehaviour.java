package org.jbehave.core.model;

import org.jbehave.core.steps.ParameterConverters;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Valery Yatsynovich
 */
public class ExamplesTablePropertiesBehaviour {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String CONVERTED = "converted";

    @Test
    public void canGetCustomProperties() {
        ExamplesTableProperties properties = new ExamplesTableProperties("ignorableSeparator=!--,headerSeparator=!,valueSeparator=!,"
                + "commentSeparator=#,trim=false,metaByRow=true,transformer=CUSTOM_TRANSFORMER", "|", "|",
                "|--");
        assertThat(properties.getRowSeparator(), equalTo("\n"));
        assertThat(properties.getHeaderSeparator(), equalTo("!"));
        assertThat(properties.getValueSeparator(), equalTo("!"));
        assertThat(properties.getIgnorableSeparator(), equalTo("!--"));
        assertThat(properties.getCommentSeparator(), equalTo("#"));
        assertThat(properties.isTrim(), is(false));
        assertThat(properties.isMetaByRow(), is(true));
        assertThat(properties.getTransformer(), equalTo("CUSTOM_TRANSFORMER"));
    }

    @Test
    public void canSetPropertiesWithBackwardSlash() {
        ExamplesTableProperties properties = new ExamplesTableProperties("custom=\\", "|", "|", "|--");
        assertThat(properties.getProperties().getProperty("custom"), equalTo("\\"));
    }

    @Test
    public void canGetDefaultProperties() {
        ExamplesTableProperties properties = new ExamplesTableProperties(new Properties());
        assertThat(properties.getHeaderSeparator(), equalTo("|"));
        assertThat(properties.getValueSeparator(), equalTo("|"));
        assertThat(properties.getIgnorableSeparator(), equalTo("|--"));
        assertThat(properties.getCommentSeparator(), equalTo("#"));
        assertThat(properties.isTrim(), is(true));
        assertThat(properties.isMetaByRow(), is(false));
        assertThat(properties.getTransformer(), is(nullValue()));
    }

    @Test
    public void canGetAllProperties() {
        Properties properties = new Properties();
        properties.setProperty(KEY, VALUE);
        ExamplesTableProperties tableProperties = new ExamplesTableProperties(properties);
        assertThat(tableProperties.getProperties().containsKey(KEY), is(true));
    }

    @Test
    public void canGetPropertiesWithNestedTransformersWithoutEscaping() {
        ExamplesTableProperties properties = new ExamplesTableProperties("transformer=CUSTOM_TRANSFORMER, " +
                "tables={transformer=CUSTOM_TRANSFORMER\\, parameter1=value1}", "|", "|",
                "|--");
        assertThat(properties.getRowSeparator(), equalTo("\n"));
        assertThat(properties.getHeaderSeparator(), equalTo("|"));
        assertThat(properties.getValueSeparator(), equalTo("|"));
        assertThat(properties.getIgnorableSeparator(), equalTo("|--"));
        assertThat(properties.isTrim(), is(true));
        assertThat(properties.isMetaByRow(), is(false));
        assertThat(properties.getTransformer(), equalTo("CUSTOM_TRANSFORMER"));
        assertThat(properties.getProperties().getProperty("tables"),
                equalTo("{transformer=CUSTOM_TRANSFORMER, parameter1=value1}"));
    }
    
    @Test
    public void shouldApplyConvertersToValues() {
        ParameterConverters converters = mock(ParameterConverters.class);
        Mockito.when(converters.convert(VALUE, String.class)).thenReturn(CONVERTED);
        ExamplesTableProperties properties = new ExamplesTableProperties(KEY + "=" + VALUE, "|", "|", "|--",
                converters);
        assertThat(properties.getProperties().getProperty(KEY), equalTo(CONVERTED));
    }
}
