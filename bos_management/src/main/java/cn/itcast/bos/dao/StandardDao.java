package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardDao extends JpaRepository<Standard,Integer>  {
}
