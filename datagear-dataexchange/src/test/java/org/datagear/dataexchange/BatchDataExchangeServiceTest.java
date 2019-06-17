/*
 * Copyright (c) 2018 datagear.org. All Rights Reserved.
 */

package org.datagear.dataexchange;

import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.datagear.connection.IOUtil;
import org.datagear.connection.JdbcUtil;
import org.datagear.dataexchange.support.CsvBatchDataImport;
import org.datagear.dataexchange.support.CsvDataImport;
import org.datagear.dataexchange.support.CsvDataImportService;
import org.junit.Assert;
import org.junit.Test;

/**
 * {@linkplain CsvBatchDataImportService}单元测试类。
 * 
 * @author datagear@163.com
 *
 */
public class BatchDataExchangeServiceTest extends DataexchangeTestSupport
{
	public static final String TABLE_NAME = "T_DATA_IMPORT";

	private BatchDataExchangeService<CsvDataImport, CsvBatchDataImport> batchDataExchangeService;

	public BatchDataExchangeServiceTest()
	{
		super();

		CsvDataImportService csvDataImportService = new CsvDataImportService(databaseInfoResolver);
		ExecutorService executorService = Executors.newCachedThreadPool();

		this.batchDataExchangeService = new BatchDataExchangeService<CsvDataImport, CsvBatchDataImport>(
				csvDataImportService, executorService);
	}

	@Test
	public void exchangeTest() throws Exception
	{
		ConnectionFactory connectionFactory = new DataSourceConnectionFactory(buildTestDataSource());
		DataFormat dataFormat = new DataFormat();
		TextDataImportOption importOption = new TextDataImportOption(true, ExceptionResolve.ABORT, true);
		List<Reader> csvReaders = new ArrayList<Reader>();
		List<String> importTables = new ArrayList<String>();

		csvReaders.add(IOUtil.getReader(getTestResourceInputStream("BatchDataExchangeServiceTest_1.csv"), "UTF-8"));
		csvReaders.add(IOUtil.getReader(getTestResourceInputStream("BatchDataExchangeServiceTest_2.csv"), "UTF-8"));

		importTables.add(TABLE_NAME);
		importTables.add(TABLE_NAME);

		CsvBatchDataImport csvBatchDataImport = new CsvBatchDataImport(connectionFactory, dataFormat, importOption,
				csvReaders, importTables);

		Connection cn = connectionFactory.getConnection();

		try
		{
			clearTable(cn, TABLE_NAME);

			this.batchDataExchangeService.exchange(csvBatchDataImport);

			List<CsvDataImport> csvDataImports = csvBatchDataImport.getForResult();

			int count = getCount(cn, TABLE_NAME);

			Assert.assertEquals(2, csvDataImports.size());
			Assert.assertEquals(6, count);
		}
		finally
		{
			JdbcUtil.closeConnection(cn);
		}
	}
}
