package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class AreaServiceImpl implements AreaService{
    @Autowired
    AreaDao areaDao;

    @Override
    public void saveBatch(List<Area> areas) {
        for (Area area : areas) {
            areaDao.save(area);
        }
    }

    @Override
    public Page<Area> findPage(Specification<Area> specification, Pageable pageable) {
        return areaDao.findAll(specification,pageable);
    }

    @Override
    public void delete(String [] id) {
        for (String i : id) {
            areaDao.deleteById(i);
        }
    }

    @Override
    public void save(Area area) {
        areaDao.save(area);
    }
}
