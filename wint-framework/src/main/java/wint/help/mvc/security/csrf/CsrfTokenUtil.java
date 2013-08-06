package wint.help.mvc.security.csrf;

import wint.core.config.Constants;
import wint.lang.utils.StringUtil;
import wint.mvc.flow.FlowData;
import wint.mvc.holder.WintContext;

/**
 * @author pister 2012-3-11 下午02:22:41
 */
public class CsrfTokenUtil {

    //	private static CsrfTokenGenerator csrfTokenGenerator = new RandomCsrfTokenGenerator();
    private static CsrfTokenGenerator csrfTokenGenerator = new FixedBySessionCsrfTokenGenerator();

    public static final String DEFAULT_GROUP_NAME = "wint_group";

    public static final String DEFAULT_NAME = "wint_csrf";

    public static String getCurrentToken(FlowData flowData, String groupName, String tokenName) {
        return csrfTokenGenerator.currentToken(flowData, groupName, tokenName);
    }

    public static String getNextToken(FlowData flowData, String groupName, String tokenName) {
        return csrfTokenGenerator.nextToken(flowData, groupName, tokenName);
    }

    public static String token() {
        return getCurrentToken(WintContext.getFlowData(), DEFAULT_GROUP_NAME, DEFAULT_NAME);
    }

    public static String nextToken() {
        return getNextToken(WintContext.getFlowData(), DEFAULT_GROUP_NAME, DEFAULT_NAME);
    }

    public static void clearToken() {
        clearToken(WintContext.getFlowData(), DEFAULT_GROUP_NAME, DEFAULT_NAME);
    }

    public static boolean check(FlowData flowData) {
        String tokenName = flowData.getServiceContext().getConfiguration().getProperties().getString(Constants.PropertyKeys.CSRF_TOKEN_NAME, Constants.Defaults.CSRF_TOKEN_NAME);
        return checkCsrfByParameter(flowData, tokenName);
    }

    public static boolean checkCsrfByParameter(FlowData flowData, String paramName) {
        String value = flowData.getParameters().getString(paramName);
        if (StringUtil.isEmpty(value)) {
            return false;
        }
        return checkCsrfToken(flowData, DEFAULT_GROUP_NAME, DEFAULT_NAME, value);
    }

    public static boolean checkCsrfToken(FlowData flowData, String groupName, String tokenName, String inputValue) {
        String tokenFromSession = CsrfTokenUtil.getCurrentToken(flowData, groupName, tokenName);
        CsrfTokenUtil.getNextToken(flowData, groupName, tokenName);
        if (!StringUtil.equals(tokenFromSession, inputValue)) {
            return false;
        }
        return true;
    }

    public static void clearToken(FlowData flowData, String groupName, String tokenName) {
        csrfTokenGenerator.clearToken(flowData, groupName, tokenName);
    }


}
