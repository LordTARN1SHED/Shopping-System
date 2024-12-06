package com.wms.mapper;

import com.wms.entity.Books;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@Mapper
public interface BooksMapper extends BaseMapper<Books> {
    //findByName用SQL语句书写在映射文件中
    @Select("SELECT * FROM books WHERE name = #{name}")
    List<Books> findByName(String name);

    @Delete("delete from books where id=#{id}")
    boolean delById(Integer id);

    @Update("update books set number=number+#{number} where id=#{id}")
    boolean addNum(Integer id, Integer number);

    // @Select("SELECT * FROM books WHERE author like #{author}")
    @Select("SELECT * FROM books WHERE author = #{author}")
    List<Books> findByAuthor(String author);

}
