package org.modelcatalogue.crf.model.validation

import org.modelcatalogue.crf.model.Item
import org.modelcatalogue.crf.model.ResponseType
import org.springframework.beans.MutablePropertyValues
import org.springframework.validation.DataBinder
import org.springframework.validation.Errors
import org.springframework.validation.beanvalidation.SpringValidatorAdapter

class Validation {

    private Validation(){}

    static Errors validate(Map<String, Object> properties = [:], Class<?> type) {

        def target = type == Item ? type.newInstance(properties.responseType ?: ResponseType.TEXT) : type.newInstance()

        DataBinder binder = new DataBinder(target)
        binder.addValidators(new SpringValidatorAdapter(javax.validation.Validation.buildDefaultValidatorFactory().validator))
        binder.bind(new MutablePropertyValues(properties))
        binder.validate()

        binder.getBindingResult()
    }

}