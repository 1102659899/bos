package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.ResponseResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;


public interface StandardService {

    public void save(Standard standard);

    public Page<Standard> findPage(Pageable pageable);

    void delete(int [] id);

    List<Standard> findAll();
}
