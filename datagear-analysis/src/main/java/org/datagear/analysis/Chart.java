/*
 * Copyright (c) 2018 datagear.tech. All Rights Reserved.
 */

/**
 * 
 */
package org.datagear.analysis;

/**
 * 图表。
 * 
 * @author datagear@163.com
 *
 */
public interface Chart
{
	/**
	 * 获取{@linkplain ChartPropertyValues}。
	 * 
	 * @return
	 */
	ChartPropertyValues getChartOptions();

	/**
	 * 获取{@linkplain DataSetFactory}。
	 * 
	 * @return
	 */
	DataSetFactory[] getDataSetFactories();
}
