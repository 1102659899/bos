package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    CourierDao courierDao;

    @Override
    public void save(Courier courier) {
        courierDao.save(courier);
    }

    @Override
    public Page<Courier> findPage(Specification<Courier> specification,Pageable pageable) {
        return courierDao.findAll(specification,pageable);
    }

    @Override
    public void delete(int[] id) {
        for (int i : id) {
            courierDao.deleteDeltag(i);
        }
    }

    @Override
    public void reduction(int[] id) {

        for (int i : id) {
            courierDao.reduction(i);
        }


    }
}
