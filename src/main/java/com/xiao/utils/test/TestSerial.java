package com.xiao.utils.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
public class TestSerial implements Serializable {
    private static final long serialVersionUID = -5334634542217664105L;
    int id;
}
