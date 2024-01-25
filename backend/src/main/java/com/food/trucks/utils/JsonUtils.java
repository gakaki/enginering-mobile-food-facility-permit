package com.food.trucks.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String toJSON(Object target) {
        try {
            if (Objects.isNull(target)) {
                return null;
            }
            return objectMapper.writeValueAsString(target);
        } catch (Exception e) {
            log.error("JsonUtils toJavaList error", e);
            return null;
        }
    }

    public static <T> T toJavaType(String json, TypeReference<T> valueTypeRef) {
        try {
            if (Objects.isNull(json)) {
                return null;
            }
            return objectMapper.readValue(json, valueTypeRef);
        } catch (JsonProcessingException e) {
            log.error("JsonUtils toJavaType error", e);
            return null;
        }
    }
}
