// This file is licensed under the Elastic License 2.0. Copyright 2021 StarRocks Limited.

package com.starrocks.sql.optimizer.operator.physical;

import com.starrocks.catalog.Column;
import com.starrocks.catalog.Table;
import com.starrocks.external.elasticsearch.EsShardPartitions;
import com.starrocks.sql.optimizer.OptExpression;
import com.starrocks.sql.optimizer.OptExpressionVisitor;
import com.starrocks.sql.optimizer.operator.OperatorType;
import com.starrocks.sql.optimizer.operator.OperatorVisitor;
import com.starrocks.sql.optimizer.operator.scalar.ColumnRefOperator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PhysicalEsScanOperator extends PhysicalScanOperator {
    private final List<EsShardPartitions> selectedIndex;

    public PhysicalEsScanOperator(Table table,
                                  List<ColumnRefOperator> outputColumns,
                                  Map<ColumnRefOperator, Column> colRefToColumnMetaMap,
                                  List<EsShardPartitions> selectedIndex) {
        super(OperatorType.PHYSICAL_ES_SCAN, table, outputColumns, colRefToColumnMetaMap);
        this.selectedIndex = selectedIndex;
    }

    public List<EsShardPartitions> getSelectedIndex() {
        return this.selectedIndex;
    }

    @Override
    public <R, C> R accept(OperatorVisitor<R, C> visitor, C context) {
        return visitor.visitPhysicalEsScan(this, context);
    }

    @Override
    public <R, C> R accept(OptExpressionVisitor<R, C> visitor, OptExpression optExpression, C context) {
        return visitor.visitPhysicalEsScan(optExpression, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PhysicalEsScanOperator that = (PhysicalEsScanOperator) o;
        return Objects.equals(selectedIndex, that.selectedIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), selectedIndex);
    }
}
