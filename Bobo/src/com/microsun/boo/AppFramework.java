package com.microsun.boo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;

import com.letv.mobile.android.app.AndroidFramework;
import com.letv.mobile.android.app.IAndroidAppContext;
import com.letv.mobile.android.app.IAndroidFramework;
import com.letv.mobile.android.http.HttpRpcServiceModule;
import com.letv.mobile.android.preference.PreferenceManagerModule;
import com.letv.mobile.android.security.DummySiteSecurityModule;
import com.letv.mobile.core.command.impl.CommandExecutorModule;
import com.letv.mobile.core.microkernel.api.AbstractModule;
import com.letv.mobile.core.rest.provider.GSONProvider;
import com.letv.mobile.core.rpc.rest.RestEasyClientModule;
import com.letv.mobile.core.util.StringUtils;
import com.microsun.boo.service.impl.LocationServiceImpl;

public class AppFramework extends AndroidFramework<IBoboAppContext, AbstractModule<IBoboAppContext>> {
	private final BoboApplication app;
	IAndroidAppContext a;
	
	private class BoboAppContextImpl extends AbstractContext implements IBoboAppContext {
		private ExecutorService executor = Executors.newSingleThreadExecutor();

		@SuppressWarnings("rawtypes")
		@Override
		public IAndroidFramework getApplication() {
			return AppFramework.this;
		}

		@Override
		public void invokeLater(Runnable arg0) {
			runOnUIThread(arg0, 0, null);
		}

	};

	private BoboAppContextImpl context;

	public AppFramework(BoboApplication demoApplication) {
		this.app = demoApplication;
	}

	@Override
	public Application getAndroidApplication() {
		return app;
	}

	@Override
	public String getApplicationName() {
		return "letv demo";
	}

	protected IBoboAppContext getContext() {
		if (context == null)
			context = new BoboAppContextImpl();
		return context;
	}

	@Override
	protected void extractChannelInfo() {
		super.extractChannelInfo();
		String ch = (String) getContext().getAttribute(SampleConstants.KEY_PUBLISH_CHANNEL);
		if (!StringUtils.isBlank(ch)) {
			getContext().setAttribute(SampleConstants.KEY_UMENG_CHANNEL, ch);
			try {
				InputStream in = getAndroidApplication().getAssets().open("channel-maps.properties");
				Properties p = new Properties();
				p.load(in);
				String value = p.getProperty(ch);
				if (StringUtils.isNotBlank(value)) {
					if (value.contains("#")) {
						String[] chs = value.split("#");
						if (chs != null && chs.length == 2) {
							getContext().setAttribute(SampleConstants.KEY_LETV_APPKEY, chs[0]);
							getContext().setAttribute(SampleConstants.KEY_LETV_PCODE, chs[1]);
						}
					} else {
						getContext().setAttribute(SampleConstants.KEY_UMENG_CHANNEL, value);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public String getPChannel() {
		return super.getPChannel();
	}

	@Override
	public String getPChannel(String channelType) {
		return super.getPChannel(channelType);
	}

	@Override
	protected void initModules() {
		registerKernelModule(new PreferenceManagerModule<IBoboAppContext>());
		registerKernelModule(new DummySiteSecurityModule<IBoboAppContext>());
		RestEasyClientModule<IBoboAppContext> rest = new RestEasyClientModule<IBoboAppContext>();
		rest.getClient().register(GSONProvider.class);
		registerKernelModule(rest);

		CommandExecutorModule<IBoboAppContext> cmdExecutor = new CommandExecutorModule<IBoboAppContext>();
		registerKernelModule(cmdExecutor);

		HttpRpcServiceModule<IBoboAppContext> m = new HttpRpcServiceModule<IBoboAppContext>();
		m.setEnablegzip(false);
		m.setConnectionPoolSize(10);
		registerKernelModule(m);

		registerKernelModule(new LocationServiceImpl());
	}

}
