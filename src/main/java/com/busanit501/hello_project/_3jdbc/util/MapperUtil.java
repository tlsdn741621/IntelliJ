package com.busanit501.hello_project._3jdbc.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

public enum MapperUtil {
    INSTANCE;

    // 변환도구, 불러오기,
    private ModelMapper modelMapper;

    MapperUtil() {
        // DTO <-> VO , 2개의 모델 클래스의 변환 하기 위한 설정
        this.modelMapper = new ModelMapper();
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public ModelMapper get() {
        return modelMapper;
    }
}
