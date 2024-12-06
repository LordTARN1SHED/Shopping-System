package com.wms.service;

import com.wms.entity.Books;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
public interface BooksService extends IService<Books> {
    List<Books> findByName(String name);
    List<Books> findByAuthor(String author);

    boolean addNum(Integer id, Integer number);

    boolean delById(Integer id);
}
