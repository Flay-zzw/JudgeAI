package com.zzw.judgeai.common;

import lombok.Data;

@Data
public class PageRequest {

    private int pageNum = 1;
    private int pageSize = 10;
    private String sortField;
    private String sortOrder = "descend";
}
