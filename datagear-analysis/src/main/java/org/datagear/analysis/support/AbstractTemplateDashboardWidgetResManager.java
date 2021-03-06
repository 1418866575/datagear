package org.datagear.analysis.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.datagear.analysis.TemplateDashboardWidget;
import org.datagear.analysis.TemplateDashboardWidgetResManager;
import org.datagear.util.IOUtil;
import org.datagear.util.StringUtil;

/**
 * 抽象{@linkplain TemplateDashboardWidgetResManager}。
 * 
 * @author datagear@163.com
 *
 */
public abstract class AbstractTemplateDashboardWidgetResManager implements TemplateDashboardWidgetResManager
{
	public AbstractTemplateDashboardWidgetResManager()
	{
		super();
	}

	@Override
	public String getDefaultEncoding()
	{
		return Charset.defaultCharset().name();
	}

	@Override
	public Reader getResourceReader(String id, String name, String encoding) throws IOException
	{
		encoding = getResourceEncodingWithDefault(encoding);

		InputStream in = getResourceInputStream(id, name);
		return IOUtil.getReader(in, encoding);
	}

	@Override
	public Writer getResourceWriter(String id, String name, String encoding) throws IOException
	{
		encoding = getResourceEncodingWithDefault(encoding);

		OutputStream out = getResourceOutputStream(id, name);
		return IOUtil.getWriter(out, encoding);
	}

	protected String getTemplateEncodingWithDefault(TemplateDashboardWidget<?> widget)
	{
		String encoding = widget.getTemplateEncoding();

		if (StringUtil.isEmpty(encoding))
			encoding = getDefaultEncoding();

		return encoding;
	}

	protected String getResourceEncodingWithDefault(String encoding)
	{
		if (StringUtil.isEmpty(encoding))
			encoding = getDefaultEncoding();

		return encoding;
	}
}
