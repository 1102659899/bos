package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {

    public void saveBatch(List<Area> areas);


   public Page<Area> findPage(Specification<Area> specification, Pageable pageable);

    void delete(String[] id);

    void save(Area area);
}
