package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourierDao extends JpaRepository<Courier,Integer> ,JpaSpecificationExecutor<Courier> {

    @Query("update Courier set deltag='1' where id=?1")
    @Modifying
     public void deleteDeltag(int id);

    @Query("update Courier set deltag=null where id=?1")
    @Modifying
    void reduction(int i);
}
