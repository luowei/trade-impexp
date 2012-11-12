package com.oilchem.trade.service;

import com.oilchem.trade.domain.Log;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
public interface LogService {

    /**
     * 列出日志
     * @param pageable
     * @return
     */
    Page<Log> findAll(Pageable pageable);
}
