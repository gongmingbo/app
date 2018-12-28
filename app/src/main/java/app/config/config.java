package app.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class config extends WebMvcConfigurerAdapter{
	@Value("${uploadpath}")
	private String uploadpath;
	@Autowired
	private ApiInterceptor apiInterceptor;
	@Autowired
	private WebInterceptor webInterceptor;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		   registry.addResourceHandler("/resources/**").addResourceLocations("file:"+uploadpath);
		super.addResourceHandlers(registry);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webInterceptor).addPathPatterns("/web/**");
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
		super.addInterceptors(registry);
	}
	//session监听器;
	@Bean
	public ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean() {
		ServletListenerRegistrationBean<SessionListener> slrBean = new ServletListenerRegistrationBean<SessionListener>();
		slrBean.setListener(new SessionListener());
		return slrBean;
	}

}
