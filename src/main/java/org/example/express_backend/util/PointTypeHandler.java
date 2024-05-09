package org.example.express_backend.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.example.express_backend.entity.Point;

import java.sql.*;

public class PointTypeHandler implements TypeHandler<Point> {
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
//        if (parameter == null) {
//            ps.setNull(i, Types.BINARY);
//        } else {
//            // 转换 Point 为 WKB
//            byte[] wkb = toWKB(parameter); // 实现这个方法来转换为 WKB
//            ps.setBytes(i, wkb);
//        }
//    }

    @Override
    public void setParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getLongitude() + "," + parameter.getLatitude());
    }

    @Override
    public Point getResult(ResultSet rs, String columnName) throws SQLException {
        String[] split = rs.getString(columnName).split(",");
        return new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    @Override
    public Point getResult(ResultSet rs, int columnIndex) throws SQLException {
        String[] split = rs.getString(columnIndex).split(",");
        return new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    @Override
    public Point getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String[] split = cs.getString(columnIndex).split(",");
        return new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }


}
