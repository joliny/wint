package wint.help.biz.result;

import java.util.Locale;
import java.util.ResourceBundle;

import wint.mvc.holder.WintContext;
import wint.mvc.i18n.ResourceBundleService;

public class ResourceBundleUtil {

	public static String getMessage(Locale locale, ResultCode resultCode) {
		return getProperty(locale, resultCode);
	}

	public static String getMessage(ResultCode resultCode) {
		return getMessage(WintContext.getLocale(), resultCode);
	}

	public static String getProperty(Locale locale, ResultCode resultCode) {
		try {
			ResourceBundle resourceBundle = getResourceBundle(locale, resultCode);
			String name = resultCode.getName();
			if (resourceBundle.containsKey(name)) {
				return resourceBundle.getString(name);
			}
			return name;
		} catch (Exception e) {
			return resultCode.getName();
		}
	}

	public static String getProperty(ResultCode resultCode) {
		return getProperty(WintContext.getLocale(), resultCode);
	}

	private static ResourceBundle getResourceBundle(Locale locale, ResultCode resultCode) {
		locale = (locale == null ? Locale.getDefault() : locale);
		String baseName = resultCode.getClass().getName();
		ResourceBundleService resourceBundleService = WintContext.getServiceContext().getService(ResourceBundleService.class);
		return resourceBundleService.getResourceBundle(baseName, locale);
	}

}
