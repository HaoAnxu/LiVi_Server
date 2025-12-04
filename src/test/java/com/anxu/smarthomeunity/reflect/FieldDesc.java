package com.anxu.smarthomeunity.reflect;

// 自定义注解：字段描述
import java.lang.annotation.*;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDesc {
    String value(); // 字段中文描述
}
