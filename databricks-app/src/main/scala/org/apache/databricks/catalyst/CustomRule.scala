package org.apache.databricks.catalyst

import com.typesafe.scalalogging.Logger
import org.apache.databricks.InvertedIndex
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.plans.logical.{Aggregate, Distinct, LogicalPlan}
import org.apache.spark.sql.catalyst.rules.Rule

/**
 * @author Sam Ma
 * @date 2021/09/08
 * 自定义Spark SQL优化Rule，实现自定义优化逻辑
 */
case class CustomRule(spark: SparkSession) extends Rule[LogicalPlan] {

  private[this] val logger = Logger(InvertedIndex.getClass)

  def apply(plan: LogicalPlan): LogicalPlan = plan transform {
    case Distinct(child) => {
      logger.info("apply CustomReplaceDistinctRule to optimize spark sql")
      Aggregate(child.output, child.output, child)
    }
  }

}