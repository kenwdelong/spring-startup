package startup

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

class Main {

	public static void main(String[] args) {
		(1..3).each {
		long start = System.currentTimeMillis()
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:startup/applicationContext.xml")
		long stop = System.currentTimeMillis()
		println "Xml: ${stop-start} ms"
		println ctx.getBeanDefinitionCount() + " beans\n"
		
		start = System.currentTimeMillis()
		AnnotationConfigApplicationContext actx = new AnnotationConfigApplicationContext("beans")
		stop = System.currentTimeMillis()
		println "Annotation: ${stop-start} ms"
		println actx.getBeanDefinitionCount() + " beans\n"
		
		start = System.currentTimeMillis()
		actx = new AnnotationConfigApplicationContext(BeanConfig.class)
		stop = System.currentTimeMillis()
		println "Java Config: ${stop-start} ms"
		println actx.getBeanDefinitionCount() + " beans\n"
		}
	} 

}
