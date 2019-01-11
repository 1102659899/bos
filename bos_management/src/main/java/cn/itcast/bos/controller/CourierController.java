package cn.itcast.bos.controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courier")
public class CourierController {
    @Autowired
    CourierService courierService;

   @RequestMapping("/save")
    public ResponseResult save(Courier courier){
       try {
           courierService.save(courier);
       } catch (Exception e) {
           e.printStackTrace();
           return ResponseResult.FAIL();
       }
       return ResponseResult.SUCCESS();

   }

   @RequestMapping("/findPage")
   public Map findPage(int page,int rows,Courier courier){

       if(page==0){
           page=1;
       }
       if(rows==0){
           rows=1;
       }
       Pageable pageable= PageRequest.of(page-1,rows);
      Specification<Courier> specification=new Specification<Courier>() {
          @Override
          public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
              ArrayList<Predicate> list = new ArrayList<>();
              if (!StringUtils.isEmpty(courier.getCourierNum())) {
                  Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
                  list.add(p1);
              }
              if (!StringUtils.isEmpty(courier.getCompany())) {
                  Predicate p2 = cb.like(root.get("company").as(String.class), "%" + courier.getCompany() + "%");
                  list.add(p2);
              }
              if (!StringUtils.isEmpty(courier.getType())) {
                  Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
                  list.add(p3);
              }
              Join<Courier, Standard> standard = root.join("standard", JoinType.INNER);
              if (courier.getStandard() != null && !StringUtils.isEmpty(courier.getStandard().getId())) {
                  Predicate p4 = cb.equal(standard.get("id").as(String.class), courier.getStandard().getId());
                  list.add(p4);
              }
              return cb.and(list.toArray(new Predicate[0]));
          }
      };
       Page<Courier> courierPage = courierService.findPage(specification,pageable);
       long totalElements = courierPage.getTotalElements();
       List<Courier> couriers = courierPage.getContent();
       Map map=new HashMap();
       map.put("total",totalElements);
       map.put("rows",couriers);
     return map;
   }

@RequestMapping("delete")
   public Map delete(int [] id){
       Map map = new HashMap();
       try {
           courierService.delete(id);
       } catch (Exception e) {
           e.printStackTrace();
          map.put("message","作废失败");
          return map;
       }
       map.put("message","作废成功");
       return map;
   }

   @RequestMapping("reduction")
   public Map reduction(int [] id){
       Map map=new HashMap();

       try {
           courierService.reduction(id);

       } catch (Exception e) {
           e.printStackTrace();
           map.put("message","还原失败");
           return map;
       }
       map.put("message","还原成功");
       return map;
   }


}
