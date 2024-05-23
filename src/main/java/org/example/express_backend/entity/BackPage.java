package org.example.express_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class BackPage<T> {

    private static final long serialVersionUID=1L;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 当前页数
     */
    private long currentPage;

    /**
     * 总数
     */
    private long totalNum;

    /**
     * 内容
     */
    private List<T> contentList;
}
