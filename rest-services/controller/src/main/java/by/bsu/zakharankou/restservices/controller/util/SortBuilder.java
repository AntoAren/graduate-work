package by.bsu.zakharankou.restservices.controller.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.Column;
import javax.persistence.Entity;

import by.bsu.zakharankou.restservices.controller.WebException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.ReflectionUtils;
import static org.springframework.util.StringUtils.hasText;

/**
 * Utility class to describe different sort options.
 * It defines sorting direction and field to sort by.
 */
public final class SortBuilder {

    private static final ConcurrentMap<Class, Set> CLASS_FIELDS_CACHE = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Class, Map> ANNOTATION_TO_FIELD_MAPPING_CACHE = new ConcurrentHashMap<>();

    /* Contains non constant class fields.*/
    private Set<String> classFields;

    /* Contains annotation to field mapping. */
    private Map<String, String> annotationToFieldMapping;

    /* Contains sort properties. Maintains insertion order. */
    private Map<String, Direction> sortProperties = new LinkedHashMap<>();

    /* Default sort property. Is used if added property is not valid. */
    private String defaultSortProperty;

    /* Default sort direction. */
    private Direction direction = Direction.ASC;

    /**
     * Creates sorting builder. Specified model is used to create valid sort object.
     * If specified sort property is not exist in the model then default sort property is used.
     *
     * @param model        model which is used for sorting
     * @param sortProperty default sort property
     */
    //NOTE: don't use String.CASE_INSENSATIVE_ORDER. For now we decided that value of sort parameter is case sensitive.
    public SortBuilder(final Class model, final String sortProperty) {
        classFields = CLASS_FIELDS_CACHE.get(model);
        annotationToFieldMapping = ANNOTATION_TO_FIELD_MAPPING_CACHE.get(model);

        if (classFields == null || annotationToFieldMapping == null) {
            classFields = new LinkedHashSet<String>();//Maintains insertion order.
            annotationToFieldMapping = new HashMap<String, String>();

            ReflectionUtils.doWithFields(model, new FieldCallback());

            CLASS_FIELDS_CACHE.put(model, classFields);
            ANNOTATION_TO_FIELD_MAPPING_CACHE.put(model, annotationToFieldMapping);
        }

        if (!classFields.contains(sortProperty) && !annotationToFieldMapping.containsKey(sortProperty)) {
            throw new WebException("Default sorting property must be present in the model.");
        }

        this.defaultSortProperty = map(sortProperty);
    }

    /**
     * Change default direction from <code>asc</code> to <code>desc</code>. It is used when specified direction is invalid.
     */
    public SortBuilder desc() {
        direction = Direction.DESC;
        return this;
    }

    public SortBuilder sort(final String property, final String direction) {
        try {
            return add(property, Direction.fromString(direction));
        } catch (IllegalArgumentException e) {
            //if direction is not valid sort using default direction
            return add(property, this.direction);
        }
    }

    public SortBuilder sortAsc(final String property) {
        return add(property, Direction.ASC);
    }

    public SortBuilder sortDesc(final String property) {
        return add(property, Direction.DESC);
    }

    public Sort build() {
        if (sortProperties.isEmpty()) {
            //if properties are not defined sort by default property and direction
            sort(defaultSortProperty, direction.name());
        }
        Sort sortChain = null;
        for (Map.Entry<String, Direction> entry : sortProperties.entrySet()) {
            final Order order = new Order(entry.getValue(), entry.getKey());
            final Sort sort = new Sort(order.ignoreCase());
            sortChain = (sortChain == null) ? sort : sortChain.and(sort);
        }
        return sortChain;
    }

    private SortBuilder add(final String property, final Direction direction) {
        sortProperties.put(map(property), direction);
        return this;
    }

    private String map(final String property) {
        if (classFields.contains(property)) {
            //model contains specified property
            return property;
        } else if (annotationToFieldMapping.containsKey(property)) {
            //model doesn't contain specified property, but the property is mapped to an alias (field in the table)
            return annotationToFieldMapping.get(property);
        } else {
            //specified property is invalid, default property is used
            return this.defaultSortProperty;
        }
    }

    private class FieldCallback implements ReflectionUtils.FieldCallback {

        private static final String FIELD_NAME_SEPARATOR = ".";

        private String parentFieldName;

        public FieldCallback() {
        }

        public FieldCallback(String parentFieldName) {
            this.parentFieldName = parentFieldName;
        }

        private String prependWithParentFieldName(String fieldName) {
            if (hasText(this.parentFieldName)) {
                return this.parentFieldName + FIELD_NAME_SEPARATOR + fieldName;
            } else {
                return fieldName;
            }
        }

        private boolean parentFieldNameContainsField(String fieldName) {
            return hasText(this.parentFieldName) &&
                    (this.parentFieldName.endsWith(FIELD_NAME_SEPARATOR + fieldName) ||
                            this.parentFieldName.startsWith(fieldName + FIELD_NAME_SEPARATOR) ||
                            this.parentFieldName.contains(FIELD_NAME_SEPARATOR + fieldName + FIELD_NAME_SEPARATOR));
        }

        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            int modifiers = field.getModifiers();

            if (!Modifier.isStatic(modifiers)) {
                Class fieldType = field.getType();
                String fieldName = field.getName();

                if (fieldType.getAnnotation(Entity.class) != null) {
                    if (!this.parentFieldNameContainsField(fieldName)) {
                        fieldName = this.prependWithParentFieldName(fieldName);
                        ReflectionUtils.doWithFields(fieldType, new FieldCallback(fieldName));
                    }
                } else {
                    fieldName = this.prependWithParentFieldName(fieldName);
                    classFields.add(fieldName);

                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation != null) {
                        String columnName = this.prependWithParentFieldName(columnAnnotation.name());
                        annotationToFieldMapping.put(columnName, fieldName);
                    }
                }
            }
        }
    }
}