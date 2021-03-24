package com.dj.iotlite.enums;

import lombok.Getter;

@Getter
public enum InterpreterTypeEnum {
    /**
     * 内嵌groovy脚本执行
     */
    GROOVY,
    /**
     * 外挂 grpc执行
     */
    CSHARP,
    CPLUSPLUS,
    DART,
    GO,
    JAVA,
    KOTLIN,
    NODE,
    OBJECTC,
    PHP,
    PYTHON,
    RUBY,
}