package com.hitwh.face.controller;

import com.hitwh.face.bean.EquipmentBean;
import com.hitwh.face.entity.Equipment;
import com.hitwh.face.model.BasePageResult;
import com.hitwh.face.model.BaseResult;
import com.hitwh.face.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangrong
 * @date 2022/8/8 10:08
 */
@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping("/list")
    public BasePageResult<Equipment> list(@RequestParam String name,
                                                   @RequestParam Integer pageNum,
                                                   @RequestParam Integer pageSize) {
        var result = new BasePageResult<Equipment>();
        equipmentService.list(name, pageNum, pageSize, result);
        return result;
    }

    @PostMapping("/add")
    public BaseResult<Equipment> add(@RequestBody EquipmentBean equipmentBean) {
        var result = new BaseResult<Equipment>();
        equipmentService.add(equipmentBean, result);
        return result;
    }

    @PostMapping("/update")
    public BaseResult<Equipment> update(@RequestBody EquipmentBean equipmentBean) {
        var result = new BaseResult<Equipment>();
        equipmentService.update(equipmentBean, result);
        return result;
    }

    @PostMapping("/delete")
    public BaseResult<Equipment> delete(@RequestParam String id) {
        var result = new BaseResult<Equipment>();
        equipmentService.delete(id, result);
        return result;
    }

    @GetMapping("/testConnect")
    public BaseResult<Void> testConnect(@RequestParam String ip,
                                        @RequestParam Integer port,
                                        @RequestParam String privateKey) {
        var result = new BaseResult<Void>();
        equipmentService.testConnect(ip, port, privateKey, result);
        return result;
    }

    @GetMapping("/online")
    public BaseResult<List<Equipment>> online() {
        var result = new BaseResult<List<Equipment>>();
        equipmentService.online(result);
        return result;
    }
}
