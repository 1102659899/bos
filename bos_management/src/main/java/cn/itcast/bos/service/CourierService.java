package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {

    public void save(Courier  courier);

    public Page<Courier> findPage(Specification<Courier> specification,Pageable pageable);

    void delete(int[] id);

    void reduction(int[] id);
}
