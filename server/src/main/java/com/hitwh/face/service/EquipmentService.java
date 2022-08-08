package com.hitwh.face.service;

import com.hitwh.face.bean.EquipmentBean;
import com.hitwh.face.dao.EquipmentDao;
import com.hitwh.face.entity.Equipment;
import com.hitwh.face.model.BasePageResult;
import com.hitwh.face.model.BaseResult;
import com.hitwh.face.socket.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author wangrong
 * @date 2022/8/8 10:09
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentService implements InitializingBean {
    private final EquipmentDao equipmentDao;
    private Map<String, Client> equipmentMap;

    @Override
    public void afterPropertiesSet() {
        log.debug("初始化设备列表...");
        var num = equipmentDao.count();
        equipmentMap = new ConcurrentHashMap<>((int) num);
        var equipmentList = equipmentDao.findAll();
        for (var equipment : equipmentList) {
            Map<String, String> headers = Map.of("privateKey", equipment.getPrivateKey());
            var client = new Client(URI.create(equipment.getIp() + ":" + equipment.getPort()), headers);
            try {
                client.connectBlocking();
            } catch (Exception e) {
                log.warn("连接设备失败：{}", equipment.getName());
            }
            equipmentMap.put(equipment.getId(), client);
        }
        log.debug("初始化设备列表完成，共{}个设备", equipmentMap.size());
    }

    public void list(String name, Integer pageNum, Integer pageSize, BasePageResult<Equipment> result) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        var res = equipmentDao.findByNameLikePageable(name, pageable);
        result.construct(true, "查询成功", res);
    }


    public void testConnect(String ip, Integer port, String privateKey, BaseResult<Void> result) {
        Map<String, String> headers = Map.of("privateKey", privateKey);
        var client = new Client(URI.create("ws://" + ip + ":" + port), headers);
        try {
            client.connectBlocking(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            result.construct(false, "连接失败", null);
            return;
        }
        result.construct(true, "连接成功", null);
    }

    public void add(EquipmentBean equipmentBean, BaseResult<Equipment> result) {

    }

    public void update(EquipmentBean equipmentBean, BaseResult<Equipment> result) {

    }

    public void delete(String id, BaseResult<Equipment> result) {

    }

    public void online(BaseResult<List<Equipment>> result) {
        var res = equipmentDao.findAllById(equipmentMap.keySet());
        result.construct(true, "查询成功", res);
    }
}
