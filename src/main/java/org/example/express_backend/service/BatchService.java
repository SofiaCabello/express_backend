package org.example.express_backend.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.dto.CreateBatchDTO;
import org.example.express_backend.dto.HistoryDTO;
import org.example.express_backend.dto.UpdateBatchStatusDTO;
import org.example.express_backend.dto.VehicleLocationResultDTO;
import org.example.express_backend.entity.Batch;
import org.example.express_backend.entity.Package;
import org.example.express_backend.entity.VehicleLocation;
import org.example.express_backend.mapper.BatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class BatchService extends ServiceImpl<BatchMapper, Batch> implements IService<Batch> {
    @Autowired
    private BatchMapper batchMapper;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LogisticService logisticService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private VehicleService vehicleService;

    /**
     * 生成批次Id
     * @param origin 出发地
     * @param destination 目的地
     * @return 生成的批次Id
     */
    private String generateBatchId(Long origin, Long destination) {
        String time = String.valueOf(System.currentTimeMillis()).substring(7);
        return String.format("%d%d%s", origin, destination, time);
    }

    /**
     * 创建批次
     * @param DTO 创建批次的信息
     * @return 是否创建成功
     */
    public boolean createBatch(CreateBatchDTO DTO) {
        String batchId = generateBatchId(DTO.getOrigin(), DTO.getDestination());
        Batch batch = Batch.builder()
                .id(Long.parseLong(batchId))
                .origin(DTO.getOrigin())
                .destination(DTO.getDestination())
                .responsible(DTO.getResponsible())
                .status(Batch.statusEnum.IN_TRANS.getStatus())
                .vehicleId(DTO.getVehicleId())
                .build();
        try {
            batchMapper.insert(batch);
        } catch (MybatisPlusException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 更新批次状态
     * @param DTO 更新批次状态的信息
     * @return 是否更新成功
     */
    @Transactional
    public boolean updateBatchStatus(UpdateBatchStatusDTO DTO) {
        System.out.println("[DEBUG] 0");
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", DTO.getBatchId());
        Batch batch = batchMapper.selectOne(queryWrapper);
        String level = logisticService.getLogisticLevel(batch.getDestination());
        Timestamp createDate = batch.getCreateDate();
        List<VehicleLocationResultDTO> vehicleLocations = vehicleService.getVehicleLocationFromDateById(batch.getVehicleId(), createDate);
        locationService.insertPackageLocation(vehicleLocations, DTO.getBatchId());

        batch.setStatus(Batch.statusEnum.ARRIVE.getStatus());
        System.out.println("[DEBUG] 1");
        try {
            batchMapper.updateById(batch);
            System.out.println("[DEBUG] 2");
            // 如果批次目的地等级为district，同步更新包裹状态为arrived
            if(level.equals("district")) {
                System.out.println("[DEBUG] 3");
                packageService.updatePackageStatusByBatchId(DTO.getBatchId());
            }
        } catch (MybatisPlusException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Long> getPackages(Long batchId){
        Batch batch = batchMapper.selectById(batchId);
        return batch.getPackages().toJavaList(Long.class);
    }

    /**
     * 设定packages字段, 是JSONArray
     */
    public void setPackages(Long batchId, List<Long> packages){
        Batch batch = batchMapper.selectById(batchId);
        batch.setPackages(JSONArray.parseArray(packages.toString()));
        batchMapper.updateById(batch);
    }

    /**
     * 获取批次的历史记录
     */
    public List<HistoryDTO> getBatchByPackageId(Long packageId){
        return batchMapper.getBatchByPackageId(packageId);
    }


    /**
     * 批次统计7天的创建数据
     * @return 七天内每天的批次数量
     */
    public int[] getDataBySeven() {
        int[] Counts = new int[7]; // 用于存储七天内每天的批次数量
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(6); // 获取一周前的日期

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay()); // 将LocalDate转换为Timestamp

            QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
            queryWrapper.apply("DATE(create_date) = DATE('" + timestamp + "')"); // 过滤指定日期的数据
            int count = this.count(queryWrapper); // 查询符合条件的批次数量
            Counts[6 - i] = count; // 注意，数组下标应该反过来，因为从当前日期往前数
        }

        return Counts;
    }

    public Integer getCountsByEmployeeId(Long employeeId) {
        // 构造查询条件
        QueryWrapper<Batch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id", employeeId);

        // 查询与employeeId相关的所有batchId
        List<Batch> batchList = batchMapper.selectList(queryWrapper);

        // 逐个遍历batchList，获取每个batchId对应的包裹数量，并累加
        int totalPackageCount = 0;
        for (Batch batch : batchList) {
            totalPackageCount += packageService.getPackageCountByBatchId(batch.getId());
        }

        return totalPackageCount;
    }
}
