package org.example.express_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.express_backend.dto.CreateBatchDTO;
import org.example.express_backend.dto.UpdateBatchStatusDTO;
import org.example.express_backend.entity.Batch;
import org.example.express_backend.entity.Package;
import org.example.express_backend.mapper.BatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class BatchService extends ServiceImpl<BatchMapper, Batch> implements IService<Batch> {
    @Autowired
    private BatchMapper batchMapper;
    @Autowired
    private LocationService locationService;

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
    // TODO: 更新批次状态时，要将批次所在车辆的位置信息复制到Location表中，以作备份
    public boolean updateBatchStatus(UpdateBatchStatusDTO DTO) {
        Batch batch = batchMapper.selectById(DTO.getBatchId());
        if (batch == null) {
            return false;
        }
        batch.setStatus(DTO.getStatus());
        try {
            batchMapper.updateById(batch);
        } catch (MybatisPlusException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
}
