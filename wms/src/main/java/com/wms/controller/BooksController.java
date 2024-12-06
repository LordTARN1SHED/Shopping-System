package com.wms.controller;


import com.wms.common.QueryObj;
import com.wms.common.Result;
import com.wms.entity.Books;
import com.wms.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jrd
 * @since 2023-12-20
 */
@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BooksService booksService;

    @PostMapping("/subNum")
    public Result subNum(@RequestBody QueryObj queryObj){
        return booksService.addNum(queryObj.id,-queryObj.number)?Result.suc():Result.fail();
    }

    @PostMapping("/addNum")
    public Result addNum(@RequestBody QueryObj queryObj){
        return booksService.addNum(queryObj.id,queryObj.number)?Result.suc():Result.fail();
    }

    @PostMapping("/delete")
    public Result deleteBook(@RequestBody QueryObj queryObj) {
        return booksService.delById(queryObj.id)?Result.suc():Result.fail();
    }
    @GetMapping("/list")
    public Result getAllBooks() {
        return Result.suc(booksService.list());
    }

    @GetMapping("/{id}")
    public Result getBookById(@PathVariable Integer id) {
        Books books=booksService.getById(id);
        return books==null?Result.fail():Result.suc(books);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Books books){
        return booksService.updateById(books)?Result.suc():Result.fail();
    }

    //lessen by id
    @PostMapping("/lessen/{id}/{number}")
    public Result lessenById(@PathVariable Integer id,@PathVariable Integer number){
        return booksService.addNum(id,-number)?Result.suc():Result.fail();
    }
    @PostMapping("/save")
    public Result addBook(@RequestBody Books book) {
        return booksService.save(book)?Result.suc():Result.fail();
    }

    @RequestMapping("/findByName")
    public Result searchBooksByName(@RequestParam String name) {
        List<Books> list=booksService.findByName(name);
        if (list.isEmpty()) return Result.fail();
        // 注意这里需要用isEmpty来判断是否为空，而不能用 ==null
        else return Result.suc(list);
    }

    @RequestMapping("/blurryFindByName")    //模糊查询
    public Result blurrySearchByName(@RequestParam String name) {
        List<Books> list = booksService.lambdaQuery()
                .like(Books::getName,name)
                .list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam String id) {
        Books books=booksService.getById(id);
        return books==null?Result.fail():Result.suc(books);
    }

    @RequestMapping("/findByAuthor")
    public Result findByAuthor(@RequestParam String author) {
        List<Books> list=booksService.findByAuthor(author);
        return list.isEmpty()?Result.fail():Result.suc(list);
    }

    @RequestMapping("/blurryFindByAuthor")  //模糊查询
    public Result blurryFindByAuthor(@RequestParam String author) {
        List<Books> list=booksService.lambdaQuery()
                .like(Books::getAuthor,author)
                .list();
        return list.isEmpty()?Result.fail():Result.suc(list);
    }

    @RequestMapping("/getBookPicture/{bookid}")
    public ResponseEntity<Resource> getBookPicture(@PathVariable Integer bookid) throws MalformedURLException {
        Books book=booksService.getById(bookid);
        String pictureurl = book.getPictureurl();
        // 构建图片文件路径
        Path filePath = Paths.get(backurl + pictureurl);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE); // 设置响应类型为图片

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // 图片文件不存在或不可读
            return ResponseEntity.notFound().build();
        }
    }

    @Value("${file.upload-path}")
    private String backurl;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File saveFile = new File(backurl);
        if (!saveFile.exists()) {
            //若不存在该目录，则创建目录
            saveFile.mkdir();
        }
        //拼接url，采用随机数，保证每个图片的url不同
        UUID uuid = UUID.randomUUID();
        //拼接后的url
        String url = uuid + file.getOriginalFilename();
        try {
            //将文件保存指定目录
            file.transferTo(new File(backurl + url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.suc(url);
    }
}
