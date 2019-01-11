package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.AreaService;
import cn.itcast.bos.util.PinYin4jUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    AreaService areaService;

    @RequestMapping("batchImport")
    public void batchImport(MultipartFile file) throws IOException {
        Workbook hssfWorkbook = null;
        String filename = file.getOriginalFilename();
        ArrayList<Area> areas = new ArrayList<>();
        //编写解析代码逻辑
        //基于.xls格式解析HSSF
        //1.加载Excle文件对象
        if (filename.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(file.getInputStream());
        }

        if (filename.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(file.getInputStream());
        }

        Sheet sheetAt = hssfWorkbook.getSheetAt(0);

        for (Row row : sheetAt) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).getStringCellValue())) {
                continue;
            }
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            String[] headArray = PinYin4jUtils.getHeadByString(province + city + district);
            StringBuffer buffer = new StringBuffer();
            for (String headStr : headArray) {
                buffer.append(headStr);
            }
            String shortcode = buffer.toString();
            area.setShortcode(shortcode);
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(citycode);

            areas.add(area);
        }

        areaService.saveBatch(areas);

    }


    @RequestMapping("/findPage")
    public Map findPage(int page, int rows, Area area) {
        if (page == 0) {
            page = 1;
        }
        if (rows == 0) {
            rows= 1;
        }

        Pageable pageable = PageRequest.of(page - 1, rows);
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                ArrayList<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(area.getProvince())) {
                    Predicate p1 = cb.like(root.get("province").as(String.class), "%"+area.getProvince()+"%");
                    list.add(p1);
                }

                if (!StringUtils.isEmpty(area.getCity())) {
                    Predicate p2 = cb.like(root.get("city").as(String.class),"%"+ area.getCity()+"%");
                    list.add(p2);
                }

                if (!StringUtils.isEmpty(area.getDistrict())) {
                    Predicate p3 = cb.like(root.get("district").as(String.class),"%"+ area.getDistrict()+"%");
                    list.add(p3);
                }


                return cb.and(list.toArray(new Predicate[0]));
            }

        };
        Page<Area> areaPage = areaService.findPage(specification, pageable);
        Map map = new HashMap<>();
        map.put("total", areaPage.getTotalElements());
        map.put("rows", areaPage.getContent());
        return map;
    }

    @RequestMapping("/delete")
    public ResponseResult delete(String [] id){
        try {
            areaService.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return  ResponseResult.SUCCESS();
    }

    @RequestMapping("save")
    public ResponseResult save(Area area){

        try {
            areaService.save(area);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }
        return ResponseResult.SUCCESS();
    }


}
