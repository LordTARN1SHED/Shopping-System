package com.wms.service.impl;

import com.wms.entity.Books;
import com.wms.mapper.BooksMapper;
import com.wms.service.BooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

    @Autowired
    private BooksMapper booksMapper;
    @Override
    public boolean addNum(Integer id, Integer number) {
        return booksMapper.addNum(id,number);
    }
    @Override
    public List<Books> findByName(String name) {
        return booksMapper.findByName(name);
    }

    @Override
    public boolean delById(Integer id) {
        return booksMapper.deleteById(id)>0;
    }
    @Override
    public List<Books> findByAuthor(String author){
        return booksMapper.findByAuthor(author);
    }
}
