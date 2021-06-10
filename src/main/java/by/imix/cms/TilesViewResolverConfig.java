package by.imix.cms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class TilesViewResolverConfig {
    @Bean(name = "viewResolver")
    public ViewResolver getViewResolver() {
        TilesViewResolver viewResolver = new TilesViewResolver();
        viewResolver.setViewClass(TilesView.class);
        viewResolver.setOrder(1);
        return viewResolver;
    }

    @Bean(name = "jspViewResolver")
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver iVR = new InternalResourceViewResolver(){{
            setPrefix("/WEB-INF/view/");
            setSuffix(".jsp");
            setViewClass(JstlView.class);
        }};
        iVR.setOrder(3);
        return iVR;
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[]{"WEB-INF/tiles/tiles-cms-main-defs.xml",
                "WEB-INF/tiles/tiles-defs-admincontent.xml"});
        tilesConfigurer.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }


}
