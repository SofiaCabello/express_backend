package org.example.express_backend.service;

import org.example.express_backend.entity.Package;
import org.example.express_backend.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    private PackageMapper packageMapper;

    public Package getPackageById(Integer id) {
        return packageMapper.selectById(id);
    }
}
