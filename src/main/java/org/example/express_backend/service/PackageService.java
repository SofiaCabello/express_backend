package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bouncycastle.util.Times;
import org.example.express_backend.dto.*;
import org.example.express_backend.entity.Batch;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.Shipment;
import org.example.express_backend.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PackageService extends ServiceImpl<PackageMapper, Package> implements IService<Package> {
    @Autowired
    private PackageMapper packageMapper;
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private CustomerService customerService;
    @Lazy
    @Autowired
    private BatchService batchService;
    @Autowired
    private LogisticService logisticService;

    /**
     * 根据包裹id获取包裹
     * @param id 包裹id
     * @return 查询到的包裹
     */
    public Package getPackageById(Long id) {
        return packageMapper.selectById(id);
    }

    /**
     * 添加包裹到运单
     * @param shipmentId 运单id
     * @param packageId 包裹id
     * @return 是否添加成功
     */
    private boolean addPackageToShipment(Long shipmentId, Long packageId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", packageId);
        Package P = packageMapper.selectOne(queryWrapper);
        // 如果包裹不存在或者运单不存在
        if(P == null){
            return false;
        }
        // 设定包裹外键
        try {
            P.setShipmentId(shipmentId);
            packageMapper.updateById(P);
        } catch (MybatisPlusException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据批次id获取包裹id
     * @param batchId 批次id
     * @return 查询到的包裹id
     */
    public List<Long> getPackageIdsByBatchId(Long batchId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("batch_id", batchId);
        List<Package> packages = packageMapper.selectList(queryWrapper);
        return packages.stream().map(Package::getId).collect(Collectors.toList());
    }

    /**
     * 根据运单id获取包裹
     * @param shipmentId 运单id
     * @return 查询到的包裹
     */
    public List<Package> getPackagesByShipmentId(Long shipmentId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shipment_id", shipmentId);
        return packageMapper.selectList(queryWrapper);
    }

    /**
     * 用来计算运单中单个包裹的运费，注意：不是整个运单的运费（需要计算所有包裹的运费）
     * @param calculatePriceDTO 计算运费的信息
     * @return 运费
     */
    public Double calculatePrice(CalculatePriceDTO calculatePriceDTO){
        // 重量
        Double weight = calculatePriceDTO.getWeight();
        // 尺寸
        String size = calculatePriceDTO.getSize();
        Integer type = calculatePriceDTO.getType();
        Double L = Double.parseDouble(size.split(",")[0]);
        Double W = Double.parseDouble(size.split(",")[1]);
        Double H = Double.parseDouble(size.split(",")[2]);
        Long origin = calculatePriceDTO.getOrigin();
        Long destination = calculatePriceDTO.getDestination();
        double volume = L * W * H;
        double VWeight = 0;

        switch (type){
            // 标快
            case 0:
                if(weight < 30){ // 30kg以下
                    VWeight = volume / 12000;
                } else { // 30kg以上
                    VWeight = volume / 6000;
                }
                break;
            // 特快
            case 1:
                if(shipmentService.isSameArea(origin, destination)){ // 同区域
                    VWeight = volume / 12000;
                } else { // 非同区域
                    VWeight = volume / 6000;
                }
        }
        double finalWeight = Math.max(weight, VWeight);
        // 10kg以下，续重以0.1kg为计重单位；10-100kg，续重以0.5kg为计重单位；100kg及以上，四舍五入取整数。
        if(finalWeight <= 10){ // 10kg以下，精确到0.1kg
            finalWeight = Math.ceil(finalWeight * 10) / 10;
        } else if (finalWeight <= 100){ // 10-100kg，精确到0.5kg
            finalWeight = Math.ceil(finalWeight * 2) / 2;
        } else { // 100kg以上，四舍五入取整数
            finalWeight = Math.round(finalWeight);
        }
        return finalWeight * 10; // 10元/kg
    }

    /**
     * 创建包裹
     * @param DTO 创建包裹的信息
     * @return 是否创建成功
     */
    @Transactional // 保证事务的一致性
    public Long createPackage(CreatePackageDTO DTO) {
        Package P = Package.builder()
                .id(generatePackageId(DTO.getShipmentId()))
                .shipmentId(DTO.getShipmentId())
                .receiverName(DTO.getReceiverName())
                .receiverAddress(DTO.getReceiverAddress())
                .receiverPhone(DTO.getReceiverPhone())
                .weight(DTO.getWeight())
                .size(DTO.getSize())
                .status(Package.statusEnum.PENDING.getStatus())
                .signDate(new Timestamp(System.currentTimeMillis()))
                .build();
        if(DTO.getReceiverId() !=null){
            P.setReceiverId(DTO.getReceiverId());
        }
        Shipment S = shipmentService.getShipmentById(DTO.getShipmentId());
        Double price = S.getPrice() + calculatePrice(new CalculatePriceDTO(S.getOrigin(), S.getDestination(), P.getWeight(), P.getSize(), S.getType()));
        shipmentService.updatePrice(S.getId(), price);
        packageMapper.insert(P);
        addPackageToShipment(DTO.getShipmentId(), P.getId());
        return P.getId();
    }

    /**
     * 更新包裹重量和尺寸信息
     */
    @Transactional
    public boolean updatePackageInfo(UpdatePackageDTO updatePackageDTO){
        Package P = packageMapper.selectById(updatePackageDTO.getPackageId());
        Double oldPrice = calculatePrice(new CalculatePriceDTO(shipmentService.getShipmentById(P.getShipmentId()).getOrigin(), shipmentService.getShipmentById(P.getShipmentId()).getDestination(), P.getWeight(), P.getSize(), shipmentService.getShipmentById(P.getShipmentId()).getType()));
        P.setSize(updatePackageDTO.getSize());
        P.setWeight(updatePackageDTO.getWeight());
        // 更新价格
        Shipment S = shipmentService.getShipmentById(P.getShipmentId());
        Double price = S.getPrice() - oldPrice + calculatePrice(new CalculatePriceDTO(S.getOrigin(), S.getDestination(), P.getWeight(), P.getSize(), S.getType()));
        shipmentService.updatePrice(S.getId(), price);
        return packageMapper.updateById(P) == 1;
    }

    /**
     * 生成包裹id
     * @param shipmentId 运单id
     * @return 包裹id
     */
    private Long generatePackageId(Long shipmentId) {
        // 取时间戳的后3位
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(10);
        return Long.parseLong(shipmentId.toString() + timestamp);
    }


    /**
     * 揽收包裹
     * @param packageId 包裹id
     * @return 是否揽收成功
     */
    public boolean pickUpPackage(Long packageId) {
        return updatePackageStatus(packageId, Package.statusEnum.PICKED_UP.getStatus());
    }

//    /**
//     * 运输包裹
//     * @param packageDTO
//     * @return
//     */
//    public boolean transPackage(PackageDTO packageDTO) {
//        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.IN_TRANSIT.getStatus());
//    }

    /**
     * 派送包裹
     * @param packageId 包裹id
     * @return 是否派送成功
     */
    public boolean deliverPackage(Long packageId) {
        return updatePackageStatus(packageId, Package.statusEnum.DELIVERING.getStatus());
    }

    /**
     * 签收包裹
     * @param id 包裹id
     * @return 是否签收成功
     */
    public boolean signedPackage(Long id) {
        return updatePackageStatus(id, Package.statusEnum.SIGNED.getStatus());
    }

/*    *//**
     * 取消包裹
     * @param packageDTO
     * @return
     *//*
    public boolean canceledPackage(PackageDTO packageDTO) {
        return updatePackageStatus(packageDTO, Package.statusEnum.PROCESSING.getStatus(), Package.statusEnum.IN_TRANSIT.getStatus());
    }*/

    /**
     * 更新特定batch_id的包裹状态为arrived
     *
     * @param batchId 批次id
     */
    public void updatePackageStatusByBatchId(Long batchId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("batch_id", batchId);
        List<Package> packages = packageMapper.selectList(queryWrapper);
        for (Package aPackage : packages) {
            aPackage.setStatus(Package.statusEnum.ARRIVED.getStatus());
            aPackage.setSignDate(new Timestamp(System.currentTimeMillis()));
            packageMapper.updateById(aPackage);
        }
    }

    /**
     * 更新包裹状态私有方法
     * @param packageId 包裹id
     * @param status 包裹状态
     * @return  是否更新成功
     */
    private boolean updatePackageStatus(Long packageId, String status) {
        Package aPackage = packageMapper.selectById(packageId);
        aPackage.setStatus(status);
        aPackage.setSignDate(new Timestamp(System.currentTimeMillis()));
        return packageMapper.updateById(aPackage) == 1;
    }

    /**
     * 添加包裹的转运批次ids
     * @param packageBatchDTO
     * @return
     */
    public void addPackageToBatch(PackageBatchDTO packageBatchDTO){
        for (Long id :
                packageBatchDTO.getPackageIds()) {
            Package aPackage = packageMapper.selectById(id);
            aPackage.setBatchId(packageBatchDTO.getBatchId());
            packageMapper.updateById(aPackage);
        }
        batchService.setPackages(packageBatchDTO.getBatchId(), packageBatchDTO.getPackageIds());
    }

    public List<Long> getPackageIdsByVehicleId(Long vehicleId) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vehicle_id", vehicleId);
        List<Package> packages = packageMapper.selectList(queryWrapper);
        return packages.stream().map(Package::getId).collect(Collectors.toList());
    }

    /**
     * 获取未揽收的包裹
     * @param logisticId 网点id
     * @return 未揽收的包裹
     */
    public List<Package> getUnpickedPackages(Long logisticId) {
        List<Long> shipmentIds = shipmentService.getShipmentIdsByOrigin(logisticId);
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        if(!shipmentIds.isEmpty()) {
            queryWrapper.in("shipment_id", shipmentIds);
            queryWrapper.eq("status", Package.statusEnum.PENDING.getStatus());
        } else {
            return null;
        }
        return packageMapper.selectList(queryWrapper);
    }

    /**
     * 获取未派送的包裹
     * @param logisticId 网点id
     * @return 未派送的包裹
     */
    public List<Package> getUndeliveredPackages(Long logisticId) {
        List<Long> shipmentIds = shipmentService.getShipmentIdsByDestination(logisticId);
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        if(!shipmentIds.isEmpty()) {
            queryWrapper.in("shipment_id", shipmentIds);
            queryWrapper.eq("status", Package.statusEnum.ARRIVED.getStatus());
        } else {
            return null;
        }
        return packageMapper.selectList(queryWrapper);
    }

    /**
     * 获取包裹历史
     * @param packageId 包裹id
     * @return 包裹历史
     */
    public List<Map<Timestamp, String>> getPackageHistory(Long packageId) {
        // 1. 先检查包裹状态
        String status = packageMapper.selectById(packageId).getStatus();
        Timestamp signDate = packageMapper.selectById(packageId).getSignDate();
        // 2. 选取包含该包裹的批次
        List<HistoryDTO> history = batchService.getBatchByPackageId(packageId);
        List<Map<Timestamp, String>> result = new ArrayList<>();
        // 3. 编写历史记录
        // 无论状态如何，首先添加包裹的创建记录
        Timestamp createDate = packageMapper.selectById(packageId).getCreateDate();
        Timestamp updateDate = packageMapper.selectById(packageId).getSignDate();
        result.add(Map.of(createDate, "包裹已创建，正在处理。"));

        // 处理在途及其它状态的历史记录
        for(HistoryDTO h : history){
            String originName = logisticService.getLogisticName(h.getOrigin());
            String destinationName = logisticService.getLogisticName(h.getDestination());
            if(h.getStatus().equals(Batch.statusEnum.IN_TRANS.getStatus())){
                result.add(Map.of(updateDate, "包裹正从" + originName + "发往" + destinationName + "。"));
            } else {
                result.add((Map.of(updateDate, "包裹已由" + originName + "抵达" + destinationName + "。")));
            }
        }

        // 根据最新状态添加最终记录
        switch (status) {
            case "pending":
                // 无需添加额外记录
                break;
            case "in_transit":
                // 在途状态的记录已在上面的循环中处理
                break;
            case "arrived":
                result.add(Map.of(updateDate, "包裹已抵达目的地，正在等待派送。"));
                break;
            case "signed":
                result.add(Map.of(updateDate, "包裹已被签收，签收时间为" + signDate + "。"));
                break;
            default:
                // 可以添加一条默认的记录，如果状态不明确
                result.add(Map.of(updateDate, "包裹状态更新中。"));
                break;
        }

        return result;
    }

    public int[] getDataBySeven() {
        int[] packageCounts = new int[7]; // 用于存储七天内每天的包裹数量
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(6); // 获取一周前的日期

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay()); // 将LocalDate转换为Timestamp

            QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
            queryWrapper.apply("DATE(create_date) = DATE('" + timestamp + "')"); // 过滤指定日期的数据
            int count = this.count(queryWrapper); // 查询符合条件的包裹数量
            packageCounts[6 - i] = count; // 注意，数组下标应该反过来，因为从当前日期往前数
        }

        return packageCounts;
    }

    public List<Map<String, Integer>> getCountsByDeparture() {
        return packageMapper.getCountsByDeparture();
    }

    public List<Map<String, Integer>> getCountsByDestination() {
        return packageMapper.getCountsByDestination();
    }

    public int getTotalPackageCount() {
        return packageMapper.selectCount(null);
    }
}
