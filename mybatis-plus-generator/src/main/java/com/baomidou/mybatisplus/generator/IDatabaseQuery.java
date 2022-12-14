/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.DbQueryDecorator;
import com.baomidou.mybatisplus.generator.config.querys.H2Query;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.jdbc.DatabaseMetaDataWrapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author nieqiurong 2021/1/6.
 * @since 3.5.0
 */
public abstract class IDatabaseQuery {

    protected final ConfigBuilder configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    public IDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
    }

    @NotNull
    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    @NotNull
    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    /**
     * ???????????????
     *
     * @return ?????????
     */
    @NotNull
    public abstract List<TableInfo> queryTables();

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @author nieqiurong 2021/1/6.
     * @since 3.5.0
     */
    public static class DefaultDatabaseQuery extends IDatabaseQuery {

        private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDatabaseQuery.class);

        private final StrategyConfig strategyConfig;

        private final GlobalConfig globalConfig;

        private final DbQueryDecorator dbQuery;

        public DefaultDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
            super(configBuilder);
            this.strategyConfig = configBuilder.getStrategyConfig();
            this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
            this.globalConfig = configBuilder.getGlobalConfig();
        }

        @NotNull
        @Override
        public List<TableInfo> queryTables() {
            boolean isInclude = strategyConfig.getInclude().size() > 0;
            boolean isExclude = strategyConfig.getExclude().size() > 0;
            //??????????????????
            List<TableInfo> tableList = new ArrayList<>();

            //???????????????????????????????????????
            List<TableInfo> includeTableList = new ArrayList<>();
            List<TableInfo> excludeTableList = new ArrayList<>();
            try {
                dbQuery.execute(dbQuery.tablesSql(), result -> {
                    String tableName = result.getStringResult(dbQuery.tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
                        String tableComment = result.getTableComment();
                        // ????????????
                        if (!(strategyConfig.isSkipView() && "VIEW".equals(tableComment))) {
                            tableInfo.setComment(tableComment);
                            if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                                includeTableList.add(tableInfo);
                            } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                                excludeTableList.add(tableInfo);
                            }
                            tableList.add(tableInfo);
                        }
                    }
                });
                if (isExclude || isInclude) {
                    Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                        .stream()
                        .filter(s -> !ConfigBuilder.matcherRegTable(s))
                        .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
                    // ?????????????????????????????????????????????????????????????????????
                    for (TableInfo tabInfo : tableList) {
                        if (notExistTables.isEmpty()) {
                            break;
                        }
                        //????????????????????????????????????????????????????????????
                        notExistTables.remove(tabInfo.getName().toLowerCase());
                    }
                    if (notExistTables.size() > 0) {
                        LOGGER.warn("???[{}]?????????????????????????????????", String.join(StringPool.COMMA, notExistTables.values()));
                    }
                    // ??????????????????????????????
                    if (isExclude) {
                        tableList.removeAll(excludeTableList);
                    } else {
                        tableList.clear();
                        tableList.addAll(includeTableList);
                    }
                }
                // ?????????????????????????????????????????? github issues/219
                tableList.forEach(this::convertTableFields);
                return tableList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                // ?????????????????????,??????????????????
                dbQuery.closeConnection();
            }
        }

        private void convertTableFields(@NotNull TableInfo tableInfo) {
            DbType dbType = this.dataSourceConfig.getDbType();
            String tableName = tableInfo.getName();
            try {
                final Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsMetaInfoMap = new HashMap<>();
                //TODO ???????????????????????????,????????????????????????????????????.
                Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsInfo =
                    new DatabaseMetaDataWrapper(dbQuery.getConnection()).getColumnsInfo(null, dataSourceConfig.getSchemaName(), tableName);
                if (columnsInfo != null && !columnsInfo.isEmpty()) {
                    columnsMetaInfoMap.putAll(columnsInfo);
                }
                String tableFieldsSql = dbQuery.tableFieldsSql(tableName);
                Set<String> h2PkColumns = new HashSet<>();
                if (DbType.H2 == dbType) {
                    dbQuery.execute(String.format(H2Query.PK_QUERY_SQL, tableName), result -> {
                        String primaryKey = result.getStringResult(dbQuery.fieldKey());
                        if (Boolean.parseBoolean(primaryKey)) {
                            h2PkColumns.add(result.getStringResult(dbQuery.fieldName()));
                        }
                    });
                }
                Entity entity = strategyConfig.entity();
                dbQuery.execute(tableFieldsSql, result -> {
                    String columnName = result.getStringResult(dbQuery.fieldName());
                    TableField field = new TableField(this.configBuilder, columnName);
                    // ??????????????????????????????????????????????????????ID????????????list???????????????0?????????
                    boolean isId = DbType.H2 == dbType ? h2PkColumns.contains(columnName) : result.isPrimaryKey();
                    // ??????ID
                    if (isId) {
                        field.primaryKey(dbQuery.isKeyIdentity(result.getResultSet()));
                        tableInfo.setHavePrimaryKey(true);
                        if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
                            LOGGER.warn("?????????[{}]???????????????????????????????????????????????????ID??????????????????!", tableName);
                        }
                    }
                    field.setColumnName(columnName)
                        .setType(result.getStringResult(dbQuery.fieldType()))
                        .setComment(result.getFiledComment())
                        .setCustomMap(dbQuery.getCustomFields(result.getResultSet()));
                    String propertyName = entity.getNameConvert().propertyNameConvert(field);
                    IColumnType columnType = dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field);
                    field.setPropertyName(propertyName, columnType);
                    field.setMetaInfo(new TableField.MetaInfo(columnsMetaInfoMap.get(columnName.toLowerCase())));
                    tableInfo.addField(field);
                });
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tableInfo.processTable();
        }
    }

}
