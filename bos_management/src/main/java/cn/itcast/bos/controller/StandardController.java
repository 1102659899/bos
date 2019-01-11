package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("standard")
public class StandardController {
    @Autowired
    StandardService standardService;

    @RequestMapping("/save")
    public ResponseResult save(Standard standard) {

        try {
            standardService.save(standard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }

    @RequestMapping("findPage")
    public Map findPage(int page, int rows) {
        if (page == 0) {
            page = 1;

        }

        if (rows == 0) {
            rows = 1;
        }

        Pageable pageable = PageRequest.of(page - 1, rows);
        Page<Standard> standardPage = standardService.findPage(pageable);
        long totalElements = standardPage.getTotalElements(); //总记录数
        List<Standard> standards = standardPage.getContent(); //每页显示的数据
        Map map = new HashMap();
        map.put("total", totalElements);
        map.put("rows", standards);
        return map;
    }


    @RequestMapping("/delete")
    public Map delete(int[] id) {

        Map map = new HashMap();

        try {
            standardService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message", "删除失败");
            return map;
        }
        map.put("message", "删除成功");
        return map;
    }

    @RequestMapping("findCourierBox")
    public List findCourierBox() {
        List list1 = new ArrayList();
        List<Standard> list = standardService.findAll();

        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map.put("id", list.get(i).getId());
            map.put("name", list.get(i).getName());
            list1.add(map);
        }
        return list1;

    }

}
