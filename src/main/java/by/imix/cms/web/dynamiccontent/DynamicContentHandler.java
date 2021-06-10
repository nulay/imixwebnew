package by.imix.cms.web.dynamiccontent;

import by.imix.cms.model.IDynamicContentService;
import by.imix.cms.redirect.RedirectView;
import by.imix.cms.web.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by miha on 17.10.2014.
 */
@Transactional(readOnly = true)
@Scope( proxyMode = ScopedProxyMode.TARGET_CLASS )
@Slf4j
public class DynamicContentHandler implements HandlerInterceptor {

    private final IDynamicContentService dynamicContentService;

    public DynamicContentHandler(IDynamicContentService dynamicContentService) {
        this.dynamicContentService = dynamicContentService;

    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.getViewName().startsWith("redirect:")) {
            RedirectView rw = dynamicContentService.getRedirectViewService().checkRedirect(httpServletRequest.getRequestURI());
            if (rw != null) {
                dynamicContentService.load(rw.getId_nodeView());
                DynamicContent dynContent = dynamicContentService.load(rw.getId_nodeView());

                Container contC = new Container();
                contC.setName("centerPage");
                if (modelAndView.getViewName().contains("/")) {
                    contC.setType("jsp");
                } else {
                    contC.setType("src/main/webapp/WEB-INF/tiles");
                }
                contC.setContent(modelAndView.getViewName());
                modelAndView.addObject(Const.NAME_OBJECT_FOR_CENTER_PAGE_FOR_DYNAMIC_CONTENT, contC);

                modelAndView.addObject(Const.NAME_MAIN_OBJECT_FOR_DYNAMIC_CONTENT, dynContent);
                modelAndView.setViewName(dynamicContentService.getViewNameTemplate(dynContent.getNode()));

                log.debug("{} and will be redirect to {} for path - {}", rw, modelAndView.getViewName(), httpServletRequest.getServletPath());
            }
        }
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    public IDynamicContentService getDynamicContentService() {
        return dynamicContentService;
    }

}

