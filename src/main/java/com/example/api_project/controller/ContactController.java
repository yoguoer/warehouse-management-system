package com.example.api_project.controller;

import com.example.api_project.model.ResponseData;
import com.example.api_project.model.Result;
import com.example.api_project.pojo.Contact;
import com.example.api_project.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * 联系人(Contact)表控制层
 *
 */
@RestController
@RequestMapping("api/contact")
public class ContactController {
    /**
     * 服务对象
     */
    @Autowired
    private ContactService contactService;

    /**
     * 分页查询
     *
     * @return 不查询结果
     */
    @GetMapping("/list")
    public Result querylist() {
        return ResponseData.success(this.contactService.querylist());
    }

    /**
     * 分页查询
     *
     * @param contact 筛选条件    分页对象
     * @return 查询结果
     */
    @GetMapping("/list-page")
    public Result queryByPage(Contact contact, Integer page, Integer size) {
        int pageNow = page == null ? 1 : page;
        int pageSize = size== null ? 5 : size;
        int startRows = pageSize * (pageNow - 1);
        return ResponseData.success(this.contactService.queryByPage(contact,startRows ,pageSize));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getById")
    public Result queryById(@PathVariable("id") String id) {
        return ResponseData.success(this.contactService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param contact 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Contact contact) {
        Random random = new Random();
        Integer number = random.nextInt(9000) + 1000;
        contact.setContactKey(System.currentTimeMillis() + String.valueOf(number));
        return ResponseData.success(this.contactService.insert(contact));
    }

    /**
     * 编辑数据
     *
     * @param contact 实体
     * @return 编辑结果
     */
    @PostMapping("/update")
    public Result edit(@RequestBody Contact contact) {
        return ResponseData.success(this.contactService.update(contact));
    }

    /**
     * 删除数据
     *
     * @param contact 主键
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Result deleteById(@RequestBody Contact contact) {
        if(this.contactService.deleteById(contact)==true){
            return ResponseData.success();
        }else{
            return ResponseData.error();
        }
//        return ResponseData.success(this.contactService.deleteById(contact));
    }

    @PostMapping("/delete-list")
    public Result deleteByList(@RequestBody List<Contact> contactKeys) {
        for(Contact contact: contactKeys){
            this.contactService.deleteById(contact);
        }
        return ResponseData.success();
    }
}

