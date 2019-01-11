package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Rollback(value = false)
public class StandardServieImpl implements StandardService{
    @Autowired
    StandardDao standardDao;

    @Override
    public void save(Standard standard) {
          standardDao.save(standard);
    }

    @Override
    public Page<Standard> findPage(Pageable pageable) {
        return standardDao.findAll(pageable);
    }

    @Override
    public void delete(int [] id) {
        for (int i : id) {
            standardDao.deleteById(i);
        }


    }

    @Override
    public List<Standard> findAll() {
     return standardDao.findAll();
    }
}
